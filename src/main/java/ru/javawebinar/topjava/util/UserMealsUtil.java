package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println();
        List<UserMealWithExcess> mealsToStream = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToStream.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapWithDates = new LinkedHashMap<>();

        //fill the map
        for (UserMeal meal : meals) {
            mapWithDates.put(meal.getDateTime().toLocalDate(), 0);
        }

        // merge calories in one day
        for (UserMeal meal : meals) {
            if (isBetweenHalfOpen(meal.getLocalTime(), startTime, endTime)) {
                mapWithDates.merge(meal.getLocalDate(), meal.getCalories(), Integer::sum);
            }
        }

        // left non excess days in map
        mapWithDates.values().removeIf(value -> value <= caloriesPerDay);

        // fill our list to return it
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();

        for (UserMeal meal : meals) {
            if (mapWithDates.containsKey(meal.getDateTime().toLocalDate())) userMealWithExcessList
                    .add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), true));
        }

        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapWithDatesStream = meals.stream()
                        .filter(userMeal -> isBetweenHalfOpen(userMeal.getLocalTime(), startTime, endTime))
                        .collect(Collectors.groupingBy(UserMeal::getLocalDate, Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(userMeal -> mapWithDatesStream.get(userMeal.getLocalDate()) > caloriesPerDay)
                .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true))
                .collect(Collectors.toList());
    }
}
