package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static int userId = 1;

    private static int caloriesPerDay = DEFAULT_CALORIES_PER_DAY;

    public static int authUserId() {
        return userId;
    }

    public static void setAuthUserId(int id) {
        userId = id;
    }

    public static int authUserCaloriesPerDay() {
        return caloriesPerDay;
    }

    public static void setCaloriesPerDay(int calories) {
        caloriesPerDay = calories;
    }
}