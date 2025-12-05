# Design Document – Fitness & Workout Tracker

(Shortened version for the zip; the full design can match your assignment needs.)

## Project Description

The Fitness & Workout Tracker is a Java Swing desktop application for logging workouts (by date), exercises (name and muscle group), and sets (weight and reps), with simple progress statistics (max weight and last set per exercise). Data is persisted to a text file (`data/workouts.txt`).

## CRC Cards (Summary)

- **Workout**
  - Responsibilities: hold date and list of exercises; add/remove exercises; search by name.
  - Collaborators: Exercise, FitnessTracker.

- **Exercise**
  - Responsibilities: hold name, muscle group, and list of sets; add/remove sets; compute max weight and last set.
  - Collaborators: Workout, WorkoutSet, FitnessTracker.

- **WorkoutSet**
  - Responsibilities: store weight and reps; validate values.
  - Collaborators: Exercise.

- **FitnessTracker**
  - Responsibilities: hold all workouts; add/remove workouts; find workouts by date; compute exercise statistics.
  - Collaborators: Workout, Exercise, WorkoutSet, ExerciseStats, FileStorage.

- **ExerciseStats**
  - Responsibilities: represent max weight and last set for a given exercise name.
  - Collaborators: FitnessTracker, WorkoutSet.

- **FileStorage**
  - Responsibilities: load/save FitnessTracker data to text file.
  - Collaborators: FitnessTracker, Workout, Exercise, WorkoutSet.

## UML Class Diagram (Text)

- `Workout`
  - date : LocalDate
  - exercises : List<Exercise>
  - +addExercise(e: Exercise)
  - +removeExercise(e: Exercise)
  - +findExerciseByName(name: String) : Exercise

- `Exercise`
  - name : String
  - muscleGroup : String
  - sets : List<WorkoutSet>
  - +addSet(s: WorkoutSet)
  - +removeSet(s: WorkoutSet)
  - +getMaxWeight() : double
  - +getLastSet() : WorkoutSet

- `WorkoutSet`
  - weight : double
  - reps : int

- `FitnessTracker`
  - workouts : List<Workout>
  - +addWorkout(w: Workout)
  - +removeWorkout(w: Workout)
  - +findWorkoutByDate(date: LocalDate) : Workout
  - +getAllExerciseNames() : Set<String>
  - +getExerciseStatistics(name: String) : ExerciseStats

- `ExerciseStats`
  - exerciseName : String
  - maxWeight : double
  - lastWorkoutDate : LocalDate
  - lastSet : WorkoutSet

- `FileStorage`
  - DEFAULT_FILE_PATH : String
  - +load(path: String) : FitnessTracker
  - +save(tracker: FitnessTracker, path: String) : void

## Class Relationships (Text)

- FitnessTracker 1..* Workout  
- Workout 1..* Exercise  
- Exercise 1..* WorkoutSet  
- FitnessTracker -> ExerciseStats (computed statistics)  
- FileStorage -> FitnessTracker (load/save)

## Business Rules (Summary)

- Workout date must not be null.
- Exercise name must be non-empty.
- Weight must be >= 0.
- Reps must be > 0.
- Progress is computed across all workouts with matching exercise names (case-insensitive).
- Data file format:
  - `WORKOUT|<date>`
  - `EXERCISE|<name>|<muscleGroup>`
  - `SET|<weight>|<reps>`
