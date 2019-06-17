package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface MealService {

    Meal create(Meal meal);

    void delete(int userId, int mealId) throws NotFoundException;

    Meal get(int userId, int mealId) throws NotFoundException;

    void update(Meal meal);

    List<Meal> getAll(int userId);
}