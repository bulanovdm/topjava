package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL_ID, USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(
                LocalDate.of(2020, Month.OCTOBER, 19),
                LocalDate.of(2020, Month.OCTOBER, 19), USER_ID);
        assertMatch(meals, userMeal1);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void update() {
        Meal updatedMeal = getUpdatedMeal();
        service.update(new Meal(updatedMeal), USER_ID);
        assertMatch(service.get(updatedMeal.getId(), USER_ID), updatedMeal);
    }

    @Test
    public void create() {
        Meal newMeal = getNewMeal();
        Meal created = service.create(new Meal(newMeal), USER_ID);
        newMeal.setId(created.getId());
        assertMatch(created, newMeal);
        assertMatch(service.get(newMeal.getId(), USER_ID), newMeal);
    }

    @Test
    public void unauthorizedGet() {
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_MEAL_ID, USER_ID));
    }

    @Test
    public void unauthorizedDelete() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void unauthorizedUpdate() {
        Meal updatedMeal = getUpdatedMeal();
        assertThrows(NotFoundException.class, () -> service.update(updatedMeal, ADMIN_ID));
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_MEAL, USER_ID));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_MEAL, USER_ID));
    }

    @Test
    public void updateNotFound() {
        Meal updatedMeal = getUpdatedMeal();
        updatedMeal.setId(NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.update(updatedMeal, USER_ID));
    }
}