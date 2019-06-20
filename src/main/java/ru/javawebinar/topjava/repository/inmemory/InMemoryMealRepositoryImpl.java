package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
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
        MealsUtil.IVAN_MEALS.forEach(meal -> save(1, meal));
        MealsUtil.ALEX_MEALS.forEach(meal -> save(2, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else {
            Meal savedMeal = get(userId, meal.getId());
            if (savedMeal == null) {
                return null;
            }
        }
        repository.putIfAbsent(userId, new HashMap<>());
        repository.get(userId).put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int mealId) {
        log.info("delete {}", mealId);
        Map<Integer, Meal> userMeal = repository.get(userId);
        return userMeal != null && userMeal.remove(mealId) != null;
    }

    @Override
    public Meal get(int userId, int mealId) {
        log.info("get {}", mealId);
        Map<Integer, Meal> userMeal = repository.get(userId);
        return userMeal == null ? null : userMeal.get(mealId);
    }

    @Override
    public List<Meal> getByUserId(int userId) {
        log.info("getAll for user = {}", userId);
        Map<Integer, Meal> userMeal = repository.get(userId);
        if (userMeal == null) {
            return new ArrayList<>();
        }
        return userMeal.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllWithFilter(int userId, LocalDate start, LocalDate end) {
        log.info("getAll for user = {} from {} to {}", userId, start, end);
        Map<Integer, Meal> userMeal = repository.get(userId);
        if (userMeal == null) {
            return new ArrayList<>();
        }
        return userMeal.values().stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), start, end))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

