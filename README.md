# Fitness & Workout Tracker (CS-3354)

A Java Swing desktop application for tracking workouts, exercises, and sets, with simple progress tracking and file-based persistence.

## Features

- Create workouts for specific dates
- Add exercises (name + muscle group) to each workout
- Add sets (weight + reps) to each exercise
- View and edit workouts, exercises, and sets
- Simple progress view:
  - Max weight ever lifted for a given exercise (by name)
  - Last recorded set (date, weight, reps)
- Data persisted to a text file: `data/workouts.txt`

## Requirements

- Java 17 (or compatible JDK)
- Gradle (or use the Gradle wrapper if added later)

## Build & Run

From the project root:

### On macOS / Linux

```bash
./gradlew run
```

### On Windows

```bat
gradlew.bat run
```

This will launch the Swing application (main window) with a list of workouts and control buttons.

## Data Storage

- The application reads and writes workout data to:

```text
data/workouts.txt
```

- The file uses a simple line-based format:

```text
WORKOUT|2024-11-02
EXERCISE|Bench Press|Chest
SET|135.0|8
SET|155.0|5
EXERCISE|Squat|Legs
SET|185.0|5
```

You can edit or clear this file if you want to reset data.

## Packages

- `com.myfitnessapp`
  - `App` – Application entry point
- `com.myfitnessapp.model`
  - `Workout`, `Exercise`, `WorkoutSet`, `FitnessTracker`, `ExerciseStats`, `FileStorage`
- `com.myfitnessapp.ui`
  - `MainWindow`, `WorkoutWindow`, `ExerciseWindow`, `ProgressWindow`

## Notes

- GUI uses standard Java Swing components.
- Collections are implemented using Java Collection Framework (`List`, `ArrayList`, `Set`, etc.).
- All persistence is handled via simple text files; no database is required.
