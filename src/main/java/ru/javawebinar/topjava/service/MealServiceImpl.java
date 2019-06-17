package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int userId, int mealId) throws NotFoundException {
        if (!repository.delete(userId, mealId)) {
            throw new NotFoundException("Not found meal with id:" + mealId);    
        }
    }

    @Override
    public Meal get(int userId, int mealId) throws NotFoundException {
        Meal meal = repository.get(userId, mealId);
        if (meal == null) {
            throw new NotFoundException("Not found meal with id:" + mealId);
        }
        return meal;
    }


    @Override
    public void update(Meal meal) {
        repository.save(meal);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getByUserId(userId);
    }
}