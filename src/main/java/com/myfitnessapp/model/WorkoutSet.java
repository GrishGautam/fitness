package com.myfitnessapp.model;

public class WorkoutSet {

    private double weight;
    private int reps;

    public WorkoutSet(double weight, int reps) {
        setWeight(weight);
        setReps(reps);
    }

    public WorkoutSet() {
        this(0.0, 0);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative.");
        }
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        if (reps <= 0) {
            throw new IllegalArgumentException("Reps must be positive.");
        }
        this.reps = reps;
    }

    @Override
    public String toString() {
        return reps + " reps @ " + weight + " lbs";
    }
}
