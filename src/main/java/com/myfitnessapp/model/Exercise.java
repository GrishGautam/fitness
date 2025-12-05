package com.myfitnessapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercise {

    private String name;
    private String muscleGroup;
    private List<WorkoutSet> sets;

    public Exercise(String name, String muscleGroup) {
        setName(name);
        setMuscleGroup(muscleGroup);
        this.sets = new ArrayList<>();
    }

    public Exercise(String name, String muscleGroup, List<WorkoutSet> sets) {
        this(name, muscleGroup);
        if (sets != null) {
            this.sets.addAll(sets);
        }
    }

    public Exercise() {
        this("Unnamed Exercise", "Unknown");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Exercise name must not be empty.");
        }
        this.name = name.trim();
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        if (muscleGroup == null || muscleGroup.trim().isEmpty()) {
            muscleGroup = "Unknown";
        }
        this.muscleGroup = muscleGroup.trim();
    }

    public List<WorkoutSet> getSets() {
        return sets;
    }

    public void setSets(List<WorkoutSet> sets) {
        if (sets == null) {
            this.sets = new ArrayList<>();
        } else {
            this.sets = sets;
        }
    }

    public void addSet(WorkoutSet set) {
        if (set != null) {
            sets.add(set);
        }
    }

    public void removeSet(WorkoutSet set) {
        sets.remove(set);
    }

    public double getMaxWeight() {
        double max = 0.0;
        for (WorkoutSet s : sets) {
            if (s.getWeight() > max) {
                max = s.getWeight();
            }
        }
        return max;
    }

    public WorkoutSet getLastSet() {
        if (sets.isEmpty()) {
            return null;
        }
        return sets.get(sets.size() - 1);
    }

    public List<WorkoutSet> getUnmodifiableSets() {
        return Collections.unmodifiableList(sets);
    }

    @Override
    public String toString() {
        return name + " (" + muscleGroup + ")";
    }
}
