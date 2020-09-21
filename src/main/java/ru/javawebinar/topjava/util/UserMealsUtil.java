package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.*;

import java.nio.file.Files;
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

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapWithDates = new LinkedHashMap<>();

        //fill the map
        for (UserMeal meal : meals) {
            mapWithDates.put(meal.getDateTime().toLocalDate(), 0);
        }

        // merge calories in one day
        for (UserMeal meal : meals) {
            if (isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                mapWithDates.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
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

/*
        Map<LocalDate, Integer> mapWithDates =
                meals.stream().collect(Collectors.groupingBy(UserMeal::getLocalDate, Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(userMeal -> {
                    LocalTime currentTime = userMeal.getDateTime().toLocalTime();
                    return currentTime.isAfter(startTime) && currentTime.isBefore(endTime);
                })
                .map(userMeal -> {
                    Integer calories = mapWithDates.get(userMeal.getDateTime().toLocalDate());
                    return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), calories > caloriesPerDay);
                })
                .collect(Collectors.toList());
*/

        // TODO Implement by streams
        return null;
    }
}
