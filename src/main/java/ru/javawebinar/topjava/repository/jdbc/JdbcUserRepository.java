package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserRole;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;
    private final SimpleJdbcInsert insertRole;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.insertRole = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_roles");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        List<Role> userRoleList;
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            userRoleList = Collections.emptyList();
        } else {
            if (namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
                return null;
            }
            userRoleList = jdbcTemplate.queryForList("SELECT role FROM user_roles WHERE user_id=?", Role.class, user.getId());
        }

        for (Role role : user.getRoles()) {
            if (!userRoleList.contains(role)) {
                insertRole.execute(new BeanPropertySqlParameterSource(new UserRole(user.getId(), role)));
            }
        }

        for (Role role : userRoleList) {
            if (!user.getRoles().contains(role)) {
                jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=? AND role=?", user.getId(), role.name());
            }
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        User user = DataAccessUtils.singleResult(users);
        fullUserRoles(user);
        return user;
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        User user = DataAccessUtils.singleResult(users);
        fullUserRoles(user);
        return user;
    }

    private void fullUserRoles(User user) {
        if (user != null) {
            user.setRoles(jdbcTemplate.queryForList("SELECT ROLE FROM user_roles WHERE user_id=?", Role.class, user.getId()));
        }
    }

    @Override
    public List<User> getAll() {
        List<User> userList = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        List<UserRole> userRoleList = jdbcTemplate.query("SELECT * FROM user_roles", new BeanPropertyRowMapper<>(UserRole.class));
        for (UserRole userRole : userRoleList) {
            userList.stream()
                    .filter(u -> userRole.getUser_id().equals(u.getId()))
                    .findFirst()
                    .ifPresent(u -> u.addRole(userRole.getRole()));
        }
        return userList;
    }
}
