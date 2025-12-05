package com.myfitnessapp.model;

import java.time.LocalDate;

public class ExerciseStats {

    private final String exerciseName;
    private final double maxWeight;
    private final LocalDate lastWorkoutDate;
    private final WorkoutSet lastSet;

    public ExerciseStats(String exerciseName, double maxWeight, LocalDate lastWorkoutDate, WorkoutSet lastSet) {
        this.exerciseName = exerciseName;
        this.maxWeight = maxWeight;
        this.lastWorkoutDate = lastWorkoutDate;
        this.lastSet = lastSet;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public LocalDate getLastWorkoutDate() {
        return lastWorkoutDate;
    }

    public WorkoutSet getLastSet() {
        return lastSet;
    }
}
