package com.myfitnessapp.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class FileStorage {

    public static final String DEFAULT_FILE_PATH = "data/workouts.txt";

    private FileStorage() {
    }

    public static FitnessTracker load(String filePath) throws IOException {
        FitnessTracker tracker = new FitnessTracker();
        Path path = Path.of(filePath);
        if (!Files.exists(path)) {
            return tracker;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            Workout currentWorkout = null;
            Exercise currentExercise = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                String type = parts[0];
                switch (type) {
                    case "WORKOUT":
                        if (parts.length >= 2) {
                            LocalDate date = LocalDate.parse(parts[1]);
                            currentWorkout = new Workout(date);
                            tracker.addWorkout(currentWorkout);
                            currentExercise = null;
                        }
                        break;
                    case "EXERCISE":
                        if (currentWorkout != null && parts.length >= 3) {
                            String name = parts[1];
                            String muscleGroup = parts[2];
                            currentExercise = new Exercise(name, muscleGroup);
                            currentWorkout.addExercise(currentExercise);
                        }
                        break;
                    case "SET":
                        if (currentExercise != null && parts.length >= 3) {
                            double weight = Double.parseDouble(parts[1]);
                            int reps = Integer.parseInt(parts[2]);
                            WorkoutSet set = new WorkoutSet(weight, reps);
                            currentExercise.addSet(set);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return tracker;
    }

    public static void save(FitnessTracker tracker, String filePath) throws IOException {
        if (tracker == null) {
            tracker = new FitnessTracker();
        }

        Path path = Path.of(filePath);
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            List<Workout> workouts = tracker.getWorkouts();
            for (int i = 0; i < workouts.size(); i++) {
                Workout w = workouts.get(i);
                writer.write("WORKOUT|" + w.getDate());
                writer.newLine();
                for (Exercise e : w.getExercises()) {
                    writer.write("EXERCISE|" + e.getName() + "|" + e.getMuscleGroup());
                    writer.newLine();
                    for (WorkoutSet set : e.getSets()) {
                        writer.write("SET|" + set.getWeight() + "|" + set.getReps());
                        writer.newLine();
                    }
                }
                if (i < workouts.size() - 1) {
                    writer.newLine();
                }
            }
        }
    }

    public static FitnessTracker loadDefault() throws IOException {
        return load(DEFAULT_FILE_PATH);
    }

    public static void saveDefault(FitnessTracker tracker) throws IOException {
        save(tracker, DEFAULT_FILE_PATH);
    }
}
