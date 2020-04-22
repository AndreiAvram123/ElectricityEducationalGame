package com.game.UI;

import com.game.AppConstants;
import com.game.AudioManager;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;
import org.jetbrains.annotations.NotNull;

/**
 * Panel used to display a message to the user
 * usually before and after each level
 */
public class TextPanel {

    private Pane layer;
    private Button nextButton;
    private Text text;
    private TextFlow textFlow;

    public TextPanel(@NotNull Pane root) {
        this.layer = new Pane();
        this.layer.setStyle("-fx-background-color : #006DB8");
        this.layer.setVisible(false);
        this.layer.setMinWidth(root.getWidth());
        this.layer.setPrefHeight(root.getHeight());
        initializePanel();
        root.getChildren().add(this.layer);

    }

    private void initializePanel() {
        textFlow = getTextFlow();
        text = getTextNode();
        textFlow.getChildren().add(text);
        layer.getChildren().add(textFlow);
        nextButton = initializeButton();
        this.layer.getChildren().add(nextButton);
        attachOnHoveEffectOnButton();
    }

    public void showPanel(String displayText) {
        text.setText(displayText);
        layer.setVisible(true);
    }

    private void attachOnHoveEffectOnButton() {
        nextButton.setOnMouseEntered(event -> {
            AudioManager.getInstance().playButtonHoverSound();
        });
    }

    @NotNull
    private TextFlow getTextFlow() {
        double verticalPadding = 100;
        double horizontalPadding = 200;
        TextFlow textFlow = new TextFlow();
        textFlow.setLayoutX(verticalPadding);
        textFlow.setLayoutY(horizontalPadding);
        textFlow.setMaxHeight(AppConstants.SCREEN_HEIGHT - horizontalPadding * 2);
        textFlow.setMaxWidth(AppConstants.SCREEN_WIDTH - verticalPadding * 2);
        textFlow.setTextAlignment(TextAlignment.CENTER);
        return textFlow;
    }

    private Button initializeButton() {
        double width = 500;
        double height = 120;
        nextButton = new Button();
        nextButton.setText("Alright");
        nextButton.setStyle("-fx-background-color: #EB540F; -fx-text-fill: white");
        nextButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 32));
        //position the button in the middle
        nextButton.setLayoutY(textFlow.getLayoutY() + textFlow.getMaxHeight());
        nextButton.setLayoutX(AppConstants.SCREEN_WIDTH / 2 - width / 2);
        nextButton.setPrefWidth(width);
        nextButton.setPrefHeight(height);
        return nextButton;
    }

    public Button getNextButton() {
        return nextButton;
    }

    @NotNull
    private Text getTextNode() {
        Text text = new Text();
        text.setFont(Font.font("Helvetica", FontWeight.MEDIUM, 32));
        return text;
    }

    public void hidePanel() {
        layer.setVisible(false);
    }
}
