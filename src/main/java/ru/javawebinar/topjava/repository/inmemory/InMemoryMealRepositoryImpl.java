package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    //  первая Map - группировка по userId, вторая по mealId
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal.getUserId(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {}", meal);
        if (meal.getUserId() != userId) {
            return null;
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        else {
            Meal savedMeal = get(userId, meal.getId());
            if (savedMeal == null) {
                return null;
            }
        }
        if (!repository.containsKey(userId)) {
            repository.put(userId, new HashMap<>());
        }
        repository.get(userId).put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int mealId) {
        log.info("delete {}", mealId);
        return repository.getOrDefault(userId, Collections.emptyMap()).remove(mealId) != null;
    }

    @Override
    public Meal get(int userId, int mealId) {
        log.info("get {}", mealId);
        return repository.getOrDefault(userId, Collections.emptyMap()).get(mealId);
    }

    @Override
    public List<Meal> getByUserId(int userId) {
        log.info("getAll for user = {}", userId);
        return repository.getOrDefault(userId, Collections.emptyMap())
                .values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

