package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        assertMatch(service.get(USER_BREAKFAST_ID, USER_ID), USER_BREAKFAST);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getMealAnotherUser() {
        service.get(USER_BREAKFAST_ID, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(ADMIN_BREAKFAST_ID, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), ADMIN_DINNER, ADMIN_LUNCH);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteMealAnotherUser() {
        service.delete(USER_BREAKFAST_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        LocalDate startDate = LocalDate.of(2019, Month.JUNE, 20);
        LocalDate endDate = LocalDate.of(2019, Month.JUNE, 23);
        assertMatch(service.getBetweenDates(startDate, endDate, USER_ID),
                USER_DINNER_23, USER_LUNCH_23, USER_BREAKFAST_23, USER_DINNER, USER_LUNCH, USER_BREAKFAST);
    }

    @Test
    public void getBetweenDateTimes() {
        LocalDateTime startDateTime = LocalDateTime.of(2019, Month.JUNE, 20, 9, 20);
        LocalDateTime endDateTime = LocalDateTime.of(2019, Month.JUNE, 22, 14, 40);
        assertMatch(service.getBetweenDateTimes(startDateTime, endDateTime, USER_ID),
                USER_LUNCH, USER_BREAKFAST);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(ADMIN_ID),
                ADMIN_DINNER, ADMIN_LUNCH, ADMIN_BREAKFAST);
    }

    @Test
    public void update() {
        Meal updated = new Meal(USER_BREAKFAST);
        updated.setDescription("Updated Description");
        updated.setCalories(450);
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_BREAKFAST_ID, USER_ID), updated);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2019, Month.JUNE, 21, 10, 0), "Admin another Завтрак", 500);
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(ADMIN_ID),
                ADMIN_DINNER, ADMIN_LUNCH, ADMIN_BREAKFAST, created);
    }
}