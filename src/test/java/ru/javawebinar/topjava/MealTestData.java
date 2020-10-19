package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int USER_MEAL_ID = 1;
    public static final int ADMIN_MEAL_ID = 4;
    public static final int NOT_FOUND_MEAL = 0;

    // (100000, 500, 'userMeal1', '2020-10-19 14:00')
    public static final Meal userMeal1 = new Meal(USER_MEAL_ID,
            LocalDateTime.of(2020, Month.OCTOBER, 19, 14, 0), "userMeal1", 500);

    // (100000, 1000, 'userMeal2', '2020-10-20 15:00')
    public static final Meal userMeal2 = new Meal(USER_MEAL_ID + 1,
            LocalDateTime.of(2020, Month.OCTOBER, 20, 15, 0), "userMeal2", 1000);

    // (100000, 600, 'userMeal3', '2020-10-21 16:00')
    public static final Meal userMeal3 = new Meal(USER_MEAL_ID + 2,
            LocalDateTime.of(2020, Month.OCTOBER, 21, 16, 0), "userMeal3", 600);

    // (100001, 500, 'adminMeal1', '2020-10-19 18:00')
    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL_ID,
            LocalDateTime.of(2020, Month.OCTOBER, 19, 18, 0), "adminMeal1", 500);

    public static Meal getNewMeal() {
        return new Meal(null, LocalDateTime.of(2020, Month.OCTOBER, 20, 12, 0), "Meal", 1000);
    }

    public static Meal getUpdatedMeal() {
        Meal updated = new Meal(userMeal1);
        updated.setDateTime(LocalDateTime.of(2020, Month.DECEMBER, 20, 20, 0));
        updated.setDescription("anotherUserMeal1");
        updated.setCalories(1500);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
