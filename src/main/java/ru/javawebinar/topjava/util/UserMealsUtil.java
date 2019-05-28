package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    private static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        // считаем каллории по дням
        Map<LocalDate, Integer> dayCaloriesMap = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            LocalDate mealDay = userMeal.getDate();
            Integer prevCalories = dayCaloriesMap.getOrDefault(mealDay, 0);
            dayCaloriesMap.put(mealDay, prevCalories + userMeal.getCalories());
        }

        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            if (TimeUtil.isBetween(userMeal.getTime(), startTime, endTime)) {
                userMealWithExceedList.add(
                        createUserMealWithExceed(userMeal,
                                dayCaloriesMap.get(userMeal.getDate()) > caloriesPerDay));
            }
        }
        return userMealWithExceedList;
    }

    private static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // группируем каллории по дням
        Map<LocalDate, Integer> dayCaloriesMap = mealList
                .stream()
                .collect(Collectors.groupingBy(
                        UserMeal::getDate,
                        Collectors.mapping(
                                UserMeal::getCalories,
                                Collectors.summingInt(Integer::valueOf)
                        )
                ));

        return mealList
                .stream()
                .filter(um -> TimeUtil.isBetween(um.getTime(), startTime, endTime))
                .map(userMeal -> createUserMealWithExceed(
                        userMeal,
                        dayCaloriesMap.get(userMeal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static UserMealWithExceed createUserMealWithExceed(UserMeal userMeal, boolean isExceeded) {
        return new UserMealWithExceed(
                userMeal.getDateTime(),
                userMeal.getDescription(),
                userMeal.getCalories(),
                isExceeded);
    }
}
