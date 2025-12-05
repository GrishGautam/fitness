package com.myfitnessapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Workout {

    private LocalDate date;
    private List<Exercise> exercises;

    public Workout(LocalDate date) {
        setDate(date);
        this.exercises = new ArrayList<>();
    }

    public Workout(LocalDate date, List<Exercise> exercises) {
        this(date);
        if (exercises != null) {
            this.exercises.addAll(exercises);
        }
    }

    public Workout() {
        this(LocalDate.now());
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Workout date must not be null.");
        }
        this.date = date;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        if (exercises == null) {
            this.exercises = new ArrayList<>();
        } else {
            this.exercises = exercises;
        }
    }

    public void addExercise(Exercise exercise) {
        if (exercise != null) {
            exercises.add(exercise);
        }
    }

    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
    }

    public Exercise findExerciseByName(String name) {
        if (name == null) {
            return null;
        }
        for (Exercise e : exercises) {
            if (e.getName().equalsIgnoreCase(name.trim())) {
                return e;
            }
        }
        return null;
    }

    public List<Exercise> getUnmodifiableExercises() {
        return Collections.unmodifiableList(exercises);
    }

    @Override
    public String toString() {
        return "Workout on " + date + " (" + exercises.size() + " exercises)";
    }
}
