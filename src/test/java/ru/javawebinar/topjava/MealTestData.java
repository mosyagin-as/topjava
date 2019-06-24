package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

public class MealTestData {

    public static Integer USER_BREAKFAST_ID = 100002;
    public static Integer USER_LUNCH_ID = 100003;
    public static Integer USER_DINNER_ID = 100004;
    public static Integer ADMIN_BREAKFAST_ID = 100005;
    public static Integer ADMIN_LUNCH_ID = 100006;
    public static Integer ADMIN_DINNER_ID = 100007;

    public static Meal USER_BREAKFAST = new Meal(USER_BREAKFAST_ID, LocalDateTime.of(2019, Month.JUNE, 22, 10, 0), "User Завтрак", 500);
    public static Meal USER_LUNCH = new Meal(USER_LUNCH_ID, LocalDateTime.of(2019, Month.JUNE, 22, 13, 0), "User Обед", 1000);
    public static Meal USER_DINNER = new Meal(USER_DINNER_ID, LocalDateTime.of(2019, Month.JUNE, 22, 20, 0), "User Ужин", 500);
    public static Meal ADMIN_BREAKFAST = new Meal(ADMIN_BREAKFAST_ID, LocalDateTime.of(2019, Month.JUNE, 22, 10, 0), "Admin Завтрак", 1000);
    public static Meal ADMIN_LUNCH = new Meal(ADMIN_LUNCH_ID, LocalDateTime.of(2019, Month.JUNE, 22, 13, 0), "Admin Обед", 500);
    public static Meal ADMIN_DINNER = new Meal(ADMIN_DINNER_ID, LocalDateTime.of(2019, Month.JUNE, 22, 20, 0), "Admin Ужин", 510);

}
