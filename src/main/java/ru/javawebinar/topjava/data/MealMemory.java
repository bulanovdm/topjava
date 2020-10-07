package ru.javawebinar.topjava.data;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class MealMemory implements MealRepo {
    private final AtomicInteger idMeal = new AtomicInteger(0);
    private final Map<Long, Meal> mapMeal = new ConcurrentHashMap<>();

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mapMeal.values());
    }

    @Override
    public Meal add(Meal meal) {
        long id = idMeal.incrementAndGet();
        meal.setId(id);
        mapMeal.put(id, meal);
        return meal;
    }

    @Override
    public Meal edit(Meal meal) {
        mapMeal.computeIfPresent(meal.getId(), (key, value) -> value = meal);
        return meal;
    }

    @Override
    public void removeById(Long id) {
        mapMeal.remove(id);
    }

    @Override
    public Meal getById(Long id) {
        return mapMeal.get(id);
    }
}
