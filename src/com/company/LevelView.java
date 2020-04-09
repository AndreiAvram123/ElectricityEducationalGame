package com.company;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class LevelView {

    private LevelModel levelModel;
    private final Pane currentLayer;
    private final Canvas canvas;
    private final GraphicsContext graphicsContext;
    private final HintWindow hintWindow;
    private Button startButton;

    public LevelView(@NotNull Pane root) {
        this.currentLayer = new Pane();
        this.currentLayer.setPrefHeight(root.getHeight());
        this.currentLayer.setPrefWidth(root.getWidth());
        this.currentLayer.setVisible(false);
        this.canvas = new Canvas(AppConstants.SCREEN_WIDTH, AppConstants.SCREEN_HEIGHT);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.hintWindow = new HintWindow(root);
        initializeStartRestartButton();
        currentLayer.getChildren().addAll(canvas, startButton);
        root.getChildren().add(currentLayer);
    }


    private void initializeStartRestartButton() {
        startButton = new Button();
        startButton.setMinSize(100, 30);
        startButton.setLayoutX(350);
        startButton.setLayoutY(610);
        startButton.setText("Start");

    }

    public void update() {
        if (levelModel != null) {
            this.levelModel.getObjectsOnGameScreen().forEach(gameObject -> gameObject.update());
            this.levelModel.getObjectsOnSelectorPane().forEach(gameObject -> gameObject.update());
            this.levelModel.getPlayer().update();
        }
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }


    public Canvas getCanvas() {
        return canvas;
    }

    public HintWindow getHintWindow() {
        return hintWindow;
    }


    public Button getStartButton() {
        return startButton;
    }

    public Pane getCurrentLayer() {
        return currentLayer;
    }

    public void setLevelModel(@NotNull LevelModel levelModel) {
        this.levelModel = levelModel;
    }
}
