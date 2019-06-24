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
        assertThat(service.get(USER_BREAKFAST_ID, USER_ID))
                .isEqualTo(USER_BREAKFAST);
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
        service.delete(USER_BREAKFAST_ID, USER_ID);
        assertThat(service.getAll(USER_ID)).isEqualTo(Arrays.asList(USER_LUNCH, USER_DINNER));
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
        service.create(new Meal(LocalDateTime.of(2019, Month.JUNE, 19, 10, 0), "User AnotherЗавтрак", 500), USER_ID);
        LocalDate startDate = LocalDate.of(2019, Month.JUNE, 20);
        LocalDate endDate = LocalDate.of(2019, Month.JUNE, 23);
        assertThat(service.getBetweenDates(startDate, endDate, USER_ID))
                .isEqualTo(Arrays.asList(USER_BREAKFAST, USER_LUNCH, USER_DINNER));
    }

    @Test
    public void getBetweenDateTimes() {
        LocalDateTime startDateTime = LocalDateTime.of(2019, Month.JUNE, 20, 9, 20);
        LocalDateTime endDateTime = LocalDateTime.of(2019, Month.JUNE, 22, 14, 40);
        assertThat(service.getBetweenDateTimes(startDateTime, endDateTime, USER_ID))
                .isEqualTo(Arrays.asList(USER_BREAKFAST, USER_LUNCH));
    }

    @Test
    public void getAll() {
        assertThat(service.getAll(ADMIN_ID))
                .isEqualTo(Arrays.asList(ADMIN_BREAKFAST, ADMIN_LUNCH, ADMIN_DINNER));
    }

    @Test
    public void update() {
        Meal updated = new Meal(USER_BREAKFAST);
        updated.setDescription("Updated Description");
        updated.setCalories(450);
        service.update(updated, USER_ID);
        assertThat(service.get(USER_BREAKFAST_ID, USER_ID))
                .isEqualToComparingFieldByField(updated);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2019, Month.JUNE, 21, 10, 0), "User another Завтрак", 500);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertThat(service.getAll(USER_ID))
                .isEqualTo(Arrays.asList(created, USER_BREAKFAST, USER_LUNCH, USER_DINNER));
    }
}