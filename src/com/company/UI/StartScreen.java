package com.company.UI;

import com.company.AppConstants;
import com.company.AudioManager;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

/**
 * The screen that appears when the user launches the game
 */
public class StartScreen {
    private final Pane root;
    private Button startButton;

    public StartScreen(@NotNull Pane root) {
        this.root = root;
        addStartButton();
    }

    /**
     * Method used to add the start button
     */
    private void addStartButton() {
        double width = 300;
        double height = 60;
        startButton = new Button("Start Game");
        startButton.setPrefWidth(width);
        startButton.setPrefHeight(height);
        startButton.setLayoutX(AppConstants.SCREEN_WIDTH / 2 - width / 2);
        startButton.setLayoutY(300);
        startButton.setOnMouseEntered(event -> AudioManager.getInstance().playButtonHoverSound());
        this.root.getChildren().add(startButton);
    }


    public Button getStartButton() {
        return startButton;
    }
}
