package com.company;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import org.jetbrains.annotations.NotNull;

public class TextPanel {

    private Pane layer;
    private Button nextButton;
    private Text text;
    private TextFlow textFlow;

    public TextPanel(@NotNull Pane root) {
        this.layer = new Pane();
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
        double width = 300;
        nextButton = new Button();
        nextButton.setText("Alright");
        //position the button in the middle
        nextButton.setLayoutY(textFlow.getLayoutY() + textFlow.getMaxHeight());
        nextButton.setLayoutX(AppConstants.SCREEN_WIDTH / 2 - width / 2);
        nextButton.setPrefWidth(width);
        return nextButton;
    }

    public Button getNextButton() {
        return nextButton;
    }

    @NotNull
    private Text getTextNode() {
        Text text = new Text();
        text.setStyle("-fx-font: 32 arial;");
        return text;
    }

    public void hidePanel() {
        layer.setVisible(false);
    }
}
