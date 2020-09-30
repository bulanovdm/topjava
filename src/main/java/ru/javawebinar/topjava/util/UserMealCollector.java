package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.UNORDERED;
import static java.util.stream.Collectors.toList;
import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class UserMealCollector implements Collector<UserMeal, Map<LocalDate, List<UserMeal>>, List<UserMealWithExcess>> {
    private final int caloriesPerDay;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public UserMealCollector(LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.caloriesPerDay = caloriesPerDay;
    }

    @Override
    public Supplier<Map<LocalDate, List<UserMeal>>> supplier() {
        return TreeMap::new;
    }

    @Override
    public BiConsumer<Map<LocalDate, List<UserMeal>>, UserMeal> accumulator() {
        return (map, meal) -> {
            map.putIfAbsent(meal.getLocalDate(), new ArrayList<>());
            map.computeIfPresent(meal.getLocalDate(), (key, value) -> {
                value.add(meal);
                return value;
            });
        };
    }

    @Override
    public BinaryOperator<Map<LocalDate, List<UserMeal>>> combiner() {
        return (left, right) -> {
            left.putAll(right);
            return left;
        };
    }

    @Override
    public Function<Map<LocalDate, List<UserMeal>>, List<UserMealWithExcess>> finisher() {
        List<UserMealWithExcess> finisher = new ArrayList<>();

        return map -> {
            map.keySet().stream()
                    .map(key -> map.get(key).stream()
                            .filter(userMeal -> isBetweenHalfOpen(userMeal.getLocalTime(), startTime, endTime))
                            .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                                    map.get(key).stream().mapToInt(UserMeal::getCalories).sum() > caloriesPerDay))
                            .collect(toList())).forEach(finisher::addAll);
            return finisher;
        };
    }
/*        return map -> {
            map.values().stream()
                    .flatMap(list -> list.stream()
                            .filter(userMeal -> isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                            .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                                    list.stream().mapToInt(UserMeal::getCalories).sum() > caloriesPerDay)))
                    .collect(toList()).forEach(finisher::addAll);
            return finisher;
        };
            dunno why addAll not working
 */

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(UNORDERED);
    }
}
