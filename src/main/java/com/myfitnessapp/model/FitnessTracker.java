package com.myfitnessapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FitnessTracker {

    private List<Workout> workouts;

    public FitnessTracker() {
        this.workouts = new ArrayList<>();
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        if (workouts == null) {
            this.workouts = new ArrayList<>();
        } else {
            this.workouts = workouts;
        }
    }

    public void addWorkout(Workout workout) {
        if (workout != null) {
            workouts.add(workout);
        }
    }

    public void removeWorkout(Workout workout) {
        workouts.remove(workout);
    }

    public Workout findWorkoutByDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        for (Workout w : workouts) {
            if (w.getDate().equals(date)) {
                return w;
            }
        }
        return null;
    }

    public Set<String> getAllExerciseNames() {
        Set<String> names = new HashSet<>();
        for (Workout w : workouts) {
            for (Exercise e : w.getExercises()) {
                if (e.getName() != null && !e.getName().trim().isEmpty()) {
                    names.add(e.getName().trim());
                }
            }
        }
        return names;
    }

    public ExerciseStats getExerciseStatistics(String exerciseName) {
        if (exerciseName == null || exerciseName.trim().isEmpty()) {
            return null;
        }
        String target = exerciseName.trim().toLowerCase();

        double maxWeight = 0.0;
        LocalDate lastWorkoutDate = null;
        WorkoutSet lastSet = null;

        for (Workout w : workouts) {
            for (Exercise e : w.getExercises()) {
                if (e.getName() != null && e.getName().trim().toLowerCase().equals(target)) {
                    double exerciseMax = e.getMaxWeight();
                    if (exerciseMax > maxWeight) {
                        maxWeight = exerciseMax;
                    }
                    if (e.getLastSet() != null) {
                        if (lastWorkoutDate == null || w.getDate().isAfter(lastWorkoutDate)) {
                            lastWorkoutDate = w.getDate();
                            lastSet = e.getLastSet();
                        }
                    }
                }
            }
        }

        if (lastWorkoutDate == null && maxWeight == 0.0) {
            return null;
        }

        return new ExerciseStats(exerciseName.trim(), maxWeight, lastWorkoutDate, lastSet);
    }
}
