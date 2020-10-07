package ru.javawebinar.topjava.model;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public @Getter
class Meal {
    public static final int EXCESS = 2000;

    private long id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(long id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(0, dateTime, description, calories);
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setId(long id) {
        this.id = id;
    }
}