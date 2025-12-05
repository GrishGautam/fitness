package com.myfitnessapp.ui;

import com.myfitnessapp.model.ExerciseStats;
import com.myfitnessapp.model.FitnessTracker;
import com.myfitnessapp.model.WorkoutSet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProgressWindow extends JDialog {

    private final FitnessTracker tracker;
    private final JComboBox<String> exerciseCombo;
    private final JLabel maxWeightLabel;
    private final JLabel lastSetLabel;

    public ProgressWindow(Frame owner, FitnessTracker tracker) {
        super(owner, "Exercise Progress", true);
        this.tracker = tracker;

        setSize(450, 250);
        setLocationRelativeTo(owner);

        Set<String> nameSet = tracker.getAllExerciseNames();
        List<String> names = new ArrayList<>(nameSet);
        names.sort(String::compareToIgnoreCase);

        exerciseCombo = new JComboBox<>(names.toArray(new String[0]));
        maxWeightLabel = new JLabel("Max Weight: N/A");
        lastSetLabel = new JLabel("Last Set: N/A");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Select Exercise:"), BorderLayout.WEST);
        topPanel.add(exerciseCombo, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        infoPanel.add(maxWeightLabel);
        infoPanel.add(lastSetLabel);

        JButton closeButton = new JButton("Close");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(closeButton);

        add(topPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        closeButton.addActionListener(e -> dispose());
        exerciseCombo.addActionListener(e -> updateStats());

        if (exerciseCombo.getItemCount() == 0) {
            maxWeightLabel.setText("Max Weight: N/A (no exercises available)");
            lastSetLabel.setText("Last Set: N/A");
        } else {
            updateStats();
        }
    }

    private void updateStats() {
        String selected = (String) exerciseCombo.getSelectedItem();
        if (selected == null || selected.trim().isEmpty()) {
            maxWeightLabel.setText("Max Weight: N/A");
            lastSetLabel.setText("Last Set: N/A");
            return;
        }

        ExerciseStats stats = tracker.getExerciseStatistics(selected);
        if (stats == null) {
            maxWeightLabel.setText("Max Weight: N/A");
            lastSetLabel.setText("Last Set: N/A");
            return;
        }

        maxWeightLabel.setText(String.format("Max Weight: %.2f lbs", stats.getMaxWeight()));

        WorkoutSet lastSet = stats.getLastSet();
        if (lastSet == null || stats.getLastWorkoutDate() == null) {
            lastSetLabel.setText("Last Set: N/A");
        } else {
            lastSetLabel.setText(String.format(
                    "Last Set: %s — %d reps @ %.2f lbs",
                    stats.getLastWorkoutDate(),
                    lastSet.getReps(),
                    lastSet.getWeight()
            ));
        }
    }
}
