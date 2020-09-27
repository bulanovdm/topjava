package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

//in progress

public class UserMealsCollector implements Collector<UserMeal, LinkedHashMap<UserMealWithExcess, Integer>, List<UserMealWithExcess>> {

    @Override
    public Supplier<LinkedHashMap<UserMealWithExcess, Integer>> supplier() {
        return null;
    }

    @Override
    public BiConsumer<LinkedHashMap<UserMealWithExcess, Integer>, UserMeal> accumulator() {
        return null;
    }

    @Override
    public BinaryOperator<LinkedHashMap<UserMealWithExcess, Integer>> combiner() {
        return null;
    }

    @Override
    public Function<LinkedHashMap<UserMealWithExcess, Integer>, List<UserMealWithExcess>> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
