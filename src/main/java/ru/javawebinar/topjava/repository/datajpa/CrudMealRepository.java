package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    List<Meal> getByUserIdOrderByDateTimeDesc(Integer userId);

    List<Meal> getByUserIdAndDateTimeBetweenOrderByDateTimeDesc(Integer userId, LocalDateTime startDate, LocalDateTime endDate);

    Meal getByIdAndUserId(Integer id, Integer userId);



    @Transactional
    @Modifying
    @Query(name = Meal.UPDATE)
    Integer update(@Param("datetime") LocalDateTime dateTime,
                @Param("calories") int calories,
                @Param("description") String description,
                @Param("id") int id,
                @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query(name = Meal.DELETE)
    int delete(@Param("id") int id, @Param("userId") int userId);

}
