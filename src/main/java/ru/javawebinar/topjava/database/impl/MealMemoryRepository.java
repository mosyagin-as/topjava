package ru.javawebinar.topjava.database.impl;

import ru.javawebinar.topjava.database.interfaces.MealRepository;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMemoryRepository implements MealRepository {

    private AtomicInteger counter = new AtomicInteger(0);
    private Map<Integer, Meal> mealMap = new HashMap<>();

    {
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));

    }



//    public MealMemoryRepository() {
//        counter = new AtomicInteger(0);
//    }

//    private int

    @Override
    public void create(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(counter.incrementAndGet());
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
