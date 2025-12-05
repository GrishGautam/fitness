package com.myfitnessapp.ui;

import com.myfitnessapp.model.Exercise;
import com.myfitnessapp.model.WorkoutSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ExerciseWindow extends JDialog {

    private final Exercise exercise;
    private final JTextField nameField;
    private final JTextField muscleField;
    private final JTable setTable;
    private final DefaultTableModel tableModel;

    public ExerciseWindow(Dialog owner, Exercise exercise) {
        super(owner, "Exercise Details", true);
        this.exercise = exercise;

        setSize(500, 400);
        setLocationRelativeTo(owner);

        nameField = new JTextField(exercise.getName(), 20);
        muscleField = new JTextField(exercise.getMuscleGroup(), 20);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.add(new JLabel("Exercise Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Muscle Group:"));
        formPanel.add(muscleField);

        tableModel = new DefaultTableModel(new Object[]{ "Weight", "Reps" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        for (WorkoutSet set : exercise.getSets()) {
            tableModel.addRow(new Object[]{ set.getWeight(), set.getReps() });
        }

        setTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(setTable);

        JPanel buttonPanel = new JPanel();
        JButton addSetButton = new JButton("Add Set");
        JButton deleteSetButton = new JButton("Delete Set");
        JButton closeButton = new JButton("Save & Close");

        buttonPanel.add(addSetButton);
        buttonPanel.add(deleteSetButton);
        buttonPanel.add(closeButton);

        add(formPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addSetButton.addActionListener(e -> onAddSet());
        deleteSetButton.addActionListener(e -> onDeleteSet());
        closeButton.addActionListener(e -> onSaveAndClose());
    }

    private void onAddSet() {
        tableModel.addRow(new Object[]{ 0.0, 0 });
    }

    private void onDeleteSet() {
        int row = setTable.getSelectedRow();
        if (row >= 0) {
            tableModel.removeRow(row);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a set to delete.",
                    "No Selection", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void onSaveAndClose() {
        try {
            String name = nameField.getText().trim();
            String muscle = muscleField.getText().trim();
            exercise.setName(name);
            exercise.setMuscleGroup(muscle);

            exercise.getSets().clear();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Object weightObj = tableModel.getValueAt(i, 0);
                Object repsObj = tableModel.getValueAt(i, 1);

                if (weightObj == null || repsObj == null) {
                    continue;
                }

                try {
                    double weight = Double.parseDouble(weightObj.toString());
                    int reps = Integer.parseInt(repsObj.toString());
                    if (reps <= 0 || weight < 0) {
                        continue;
                    }
                    WorkoutSet set = new WorkoutSet(weight, reps);
                    exercise.addSet(set);
                } catch (NumberFormatException ex) {
                    // skip invalid row
                }
            }

            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
