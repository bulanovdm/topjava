package ru.javawebinar.topjava.data;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepo {
    List<Meal> query();

    Meal add(Meal meal);

    Meal edit(Meal meal);

    void removeById(Long id);

    Meal getById(Long id);
}