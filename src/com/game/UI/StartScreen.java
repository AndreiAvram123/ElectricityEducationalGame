package com.game.UI;

import com.game.AppConstants;
import com.game.AudioManager;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.jetbrains.annotations.NotNull;

/**
 * The screen that appears when the user launches the game
 */
public class StartScreen {
    private final Pane root;
    private Button startButton;

    public StartScreen(@NotNull Pane root) {
        this.root = root;
        this.root.setStyle("-fx-background-color : #006DB8");
        addStartButton();
    }

    /**
     * Method used to add the start button
     */
    private void addStartButton() {
        double width = 300;
        double height = 60;
        startButton = new Button("Start Game");
        startButton.setStyle("-fx-background-color: #EB540F; -fx-text-fill: white");
        startButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 32));
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
