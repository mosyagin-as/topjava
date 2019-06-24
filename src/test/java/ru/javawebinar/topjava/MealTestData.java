package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static Integer USER_BREAKFAST_ID = 100002;
    public static Integer USER_LUNCH_ID = 100003;
    public static Integer USER_DINNER_ID = 100004;
    public static Integer ADMIN_BREAKFAST_ID = 100005;
    public static Integer ADMIN_LUNCH_ID = 100006;
    public static Integer ADMIN_DINNER_ID = 100007;

    public static Meal USER_BREAKFAST = new Meal(USER_BREAKFAST_ID, LocalDateTime.of(2019, Month.JUNE, 22, 10, 0), "User Завтрак 22", 500);
    public static Meal USER_LUNCH = new Meal(USER_LUNCH_ID, LocalDateTime.of(2019, Month.JUNE, 22, 13, 0), "User Обед 22", 1000);
    public static Meal USER_DINNER = new Meal(USER_DINNER_ID, LocalDateTime.of(2019, Month.JUNE, 22, 20, 0), "User Ужин 22", 500);
    public static Meal ADMIN_BREAKFAST = new Meal(ADMIN_BREAKFAST_ID, LocalDateTime.of(2019, Month.JUNE, 22, 10, 0), "Admin Завтрак", 1000);
    public static Meal ADMIN_LUNCH = new Meal(ADMIN_LUNCH_ID, LocalDateTime.of(2019, Month.JUNE, 22, 13, 0), "Admin Обед", 500);
    public static Meal ADMIN_DINNER = new Meal(ADMIN_DINNER_ID, LocalDateTime.of(2019, Month.JUNE, 22, 20, 0), "Admin Ужин", 510);
    public static Meal USER_BREAKFAST_23 = new Meal(ADMIN_DINNER_ID + 1, LocalDateTime.of(2019, Month.JUNE, 23, 10, 0), "User Завтрак 23", 450);
    public static Meal USER_LUNCH_23 = new Meal(ADMIN_DINNER_ID + 2, LocalDateTime.of(2019, Month.JUNE, 23, 13, 0), "User Обед 23", 900);
    public static Meal USER_DINNER_23 = new Meal(ADMIN_DINNER_ID + 3, LocalDateTime.of(2019, Month.JUNE, 23, 20, 0), "User Ужин 23", 650);
    public static Meal USER_BREAKFAST_24 = new Meal(ADMIN_DINNER_ID + 4, LocalDateTime.of(2019, Month.JUNE, 24, 10, 0), "User Завтрак 24", 550);
    public static Meal USER_LUNCH_24 = new Meal(ADMIN_DINNER_ID + 5, LocalDateTime.of(2019, Month.JUNE, 24, 13, 0), "User Обед 24", 950);
    public static Meal USER_DINNER_24 = new Meal(ADMIN_DINNER_ID + 6, LocalDateTime.of(2019, Month.JUNE, 24, 20, 0), "User Ужин 24", 600);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

}
