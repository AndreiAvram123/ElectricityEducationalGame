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

    public TextPanel(Pane root) {
        this.layer = new Pane();
        this.layer.setVisible(false);
        this.layer.setMinWidth(root.getWidth());
        this.layer.setPrefHeight(root.getHeight());
        initializePanel();
        root.getChildren().add(this.layer);
    }

    private void initializePanel() {
        TextFlow textFlow = getTextFlow();
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
            AudioManager.getInstance().playButtonHoveSound();
        });
    }

    @NotNull
    private TextFlow getTextFlow() {
        TextFlow textFlow = new TextFlow();
        textFlow.setLayoutX(200);
        textFlow.setLayoutY(100);
        textFlow.setMaxHeight(400);
        textFlow.setMaxWidth(400);
        textFlow.setTextAlignment(TextAlignment.CENTER);
        return textFlow;
    }

    private Button initializeButton() {
        nextButton = new Button();
        nextButton.setText("OK");
        nextButton.setLayoutY(500);
        nextButton.setLayoutX(300);
        nextButton.setPrefWidth(300);
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
