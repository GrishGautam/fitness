package com.myfitnessapp.ui;

import com.myfitnessapp.model.FileStorage;
import com.myfitnessapp.model.FitnessTracker;
import com.myfitnessapp.model.Workout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class MainWindow extends JFrame {

    private final FitnessTracker tracker;
    private final DefaultListModel<Workout> workoutListModel;
    private final JList<Workout> workoutList;

    public MainWindow(FitnessTracker tracker) {
        this.tracker = tracker;

        setTitle("Fitness & Workout Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);

        workoutListModel = new DefaultListModel<>();
        for (Workout w : tracker.getWorkouts()) {
            workoutListModel.addElement(w);
        }
        workoutList = new JList<>(workoutListModel);
        workoutList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(workoutList);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Workout");
        JButton editButton = new JButton("View/Edit Workout");
        JButton deleteButton = new JButton("Delete Workout");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton progressButton = new JButton("Progress");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(progressButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> onAddWorkout());
        editButton.addActionListener(e -> onEditWorkout());
        deleteButton.addActionListener(e -> onDeleteWorkout());
        saveButton.addActionListener(e -> onSave());
        loadButton.addActionListener(e -> onLoad());
        progressButton.addActionListener(e -> onProgress());
    }

    private void onAddWorkout() {
        String dateStr = JOptionPane.showInputDialog(this,
                "Enter workout date (YYYY-MM-DD):",
                LocalDate.now().toString());
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return;
        }
        try {
            LocalDate date = LocalDate.parse(dateStr.trim());
            Workout workout = new Workout(date);
            tracker.addWorkout(workout);
            workoutListModel.addElement(workout);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid date format. Please use YYYY-MM-DD.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEditWorkout() {
        Workout selected = workoutList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a workout first.",
                    "No Selection", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        WorkoutWindow ww = new WorkoutWindow(this, tracker, selected);
        ww.setVisible(true);
        workoutList.repaint();
    }

    private void onDeleteWorkout() {
        Workout selected = workoutList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a workout to delete.",
                    "No Selection", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete selected workout?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tracker.removeWorkout(selected);
            workoutListModel.removeElement(selected);
        }
    }

    private void onSave() {
        try {
            FileStorage.save(tracker, FileStorage.DEFAULT_FILE_PATH);
            JOptionPane.showMessageDialog(this,
                    "Workouts saved to " + FileStorage.DEFAULT_FILE_PATH,
                    "Save Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error saving file: " + ex.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLoad() {
        try {
            FitnessTracker loaded = FileStorage.load(FileStorage.DEFAULT_FILE_PATH);
            tracker.setWorkouts(loaded.getWorkouts());
            workoutListModel.clear();
            for (Workout w : tracker.getWorkouts()) {
                workoutListModel.addElement(w);
            }
            JOptionPane.showMessageDialog(this,
                    "Workouts loaded from " + FileStorage.DEFAULT_FILE_PATH,
                    "Load Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error loading file: " + ex.getMessage(),
                    "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onProgress() {
        ProgressWindow pw = new ProgressWindow(this, tracker);
        pw.setVisible(true);
    }
}
