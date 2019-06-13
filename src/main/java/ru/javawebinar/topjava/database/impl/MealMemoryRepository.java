package ru.javawebinar.topjava.database.impl;

import ru.javawebinar.topjava.database.interfaces.MealRepository;
import ru.javawebinar.topjava.model.Meal;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMemoryRepository implements MealRepository {

    private Map<Integer, Meal> mealMap = new HashMap<>();

    private AtomicInteger counter;

    public MealMemoryRepository() {
        counter = new AtomicInteger(0);
    }

    @Override
    public void create(Meal meal) {
        if (meal.getId() == null) {
            Integer id = counter.incrementAndGet();
            meal.setId(id);
        }
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public List<Meal> readAll() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public Meal read(Integer id) {
        return mealMap.get(id);
    }

    @Override
    public void update(Integer id, Meal meal) {
        if (mealMap.containsKey(id)) {
            mealMap.put(id, meal);
        }
    }

    @Override
    public void delete(Integer id) {
        mealMap.remove(id);
    }
}
