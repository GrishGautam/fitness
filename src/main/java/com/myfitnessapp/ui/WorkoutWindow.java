package com.myfitnessapp.ui;

import com.myfitnessapp.model.Exercise;
import com.myfitnessapp.model.FitnessTracker;
import com.myfitnessapp.model.Workout;

import javax.swing.*;
import java.awt.*;

public class WorkoutWindow extends JDialog {

    private final FitnessTracker tracker;
    private final Workout workout;

    private final DefaultListModel<Exercise> exerciseListModel;
    private final JList<Exercise> exerciseList;
    private final JLabel dateLabel;

    public WorkoutWindow(Frame owner, FitnessTracker tracker, Workout workout) {
        super(owner, "Workout Details", true);
        this.tracker = tracker;
        this.workout = workout;

        setSize(500, 350);
        setLocationRelativeTo(owner);

        dateLabel = new JLabel("Workout Date: " + workout.getDate());
        exerciseListModel = new DefaultListModel<>();
        for (Exercise e : workout.getExercises()) {
            exerciseListModel.addElement(e);
        }
        exerciseList = new JList<>(exerciseListModel);
        exerciseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(exerciseList);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(dateLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Exercise");
        JButton editButton = new JButton("Edit Exercise");
        JButton deleteButton = new JButton("Delete Exercise");
        JButton closeButton = new JButton("Close");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(closeButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> onAddExercise());
        editButton.addActionListener(e -> onEditExercise());
        deleteButton.addActionListener(e -> onDeleteExercise());
        closeButton.addActionListener(e -> dispose());
    }

    private void onAddExercise() {
        JTextField nameField = new JTextField();
        JTextField muscleField = new JTextField();

        Object[] message = {
                "Exercise Name:", nameField,
                "Muscle Group:", muscleField
        };

        int option = JOptionPane.showConfirmDialog(this, message,
                "Add Exercise", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String muscle = muscleField.getText().trim();
                Exercise exercise = new Exercise(name, muscle);
                workout.addExercise(exercise);
                exerciseListModel.addElement(exercise);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onEditExercise() {
        Exercise selected = exerciseList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select an exercise first.",
                    "No Selection", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        ExerciseWindow ew = new ExerciseWindow(this, selected);
        ew.setVisible(true);
        exerciseList.repaint();
    }

    private void onDeleteExercise() {
        Exercise selected = exerciseList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select an exercise to delete.",
                    "No Selection", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete selected exercise?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            workout.removeExercise(selected);
            exerciseListModel.removeElement(selected);
        }
    }
}
