package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface MealRepository {
    Meal save(Meal meal);

    // false if not found
    boolean delete(int userId, int mealId);

    // null if not found
    Meal get(int userId, int mealId);

    Collection<Meal> getAll();

    List<Meal> getByUserId(int userId);
}
