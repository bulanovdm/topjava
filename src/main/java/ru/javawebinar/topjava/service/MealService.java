package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.util.ValidationUtil.permissionCheck;

@Service
public class MealService {
    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        meal.setUserId(userId);
        return repository.save(meal);
    }

    public boolean delete(Integer mealId, int userId) {
        Meal meal = repository.get(mealId);
        checkNotFoundWithId(meal, mealId);
        permissionCheck(meal, userId);
        return repository.delete(mealId);
    }

    public Meal get(Integer mealId, int userId) {
        Meal meal = repository.get(mealId);
        checkNotFoundWithId(meal, mealId);
        permissionCheck(meal, userId);
        return meal;
    }

    public Collection<Meal> getAll(int authUserId) {
        return repository.getAll()
                .stream()
                .filter(meal -> meal.getUserId() == authUserId)
                .distinct()
                .collect(Collectors.toList());
    }
}