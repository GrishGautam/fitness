package com.myfitnessapp;

import com.myfitnessapp.model.FitnessTracker;
import com.myfitnessapp.model.FileStorage;
import com.myfitnessapp.ui.MainWindow;

import javax.swing.*;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FitnessTracker tracker;
            try {
                tracker = FileStorage.load(FileStorage.DEFAULT_FILE_PATH);
            } catch (IOException e) {
                System.err.println("Could not load data file: " + e.getMessage());
                tracker = new FitnessTracker();
            }

            MainWindow window = new MainWindow(tracker);
            window.setVisible(true);
        });
    }
}
