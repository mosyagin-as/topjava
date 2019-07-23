package ru.javawebinar.topjava.model;

public class UserRole {
    private Integer user_id;
    private Role role;

    public UserRole () {}

    public UserRole(Integer user_id, Role role) {
        this.user_id = user_id;
        this.role = role;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
