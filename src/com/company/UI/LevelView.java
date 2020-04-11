package com.company.UI;

import com.company.AppConstants;
import com.company.models.LevelModel;
import com.company.models.GameObject;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

/**
 * The class representing the view for the levels
 */
public class LevelView {

    private LevelModel levelModel;
    private final Pane currentLayer;
    private final Canvas canvas;
    private final GraphicsContext graphicsContext;
    private final HintWindow hintWindow;
    private Button startButton;

    public LevelView(@NotNull Pane root) {
        this.currentLayer = new Pane();
        this.currentLayer.setMinHeight(root.getHeight());
        this.currentLayer.setMinWidth(root.getWidth());
        this.canvas = new Canvas(AppConstants.SCREEN_WIDTH, AppConstants.SCREEN_HEIGHT);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.hintWindow = new HintWindow();
        initializeStartRestartButton();
        currentLayer.getChildren().addAll(canvas, startButton, hintWindow.getLayout());
        root.getChildren().add(currentLayer);
        this.currentLayer.setVisible(false);
    }


    private void initializeStartRestartButton() {
        //the button should have the width equal to the
        //difference between the screen height and the grid system height
        double buttonHeight = AppConstants.SCREEN_HEIGHT - AppConstants.GRID_SYSTEM_HEIGHT;
        startButton = new Button();

        startButton.setMinSize(150, buttonHeight);
        startButton.setLayoutX(AppConstants.SCREEN_WIDTH / 2 - 100);
        startButton.setLayoutY(AppConstants.GRID_SYSTEM_HEIGHT);
        startButton.setText("Start");


    }

    /**
     * Convenient setter in order to update the underlying data of the view
     *
     * @param levelModel
     */
    public void setLevelModel(@NotNull LevelModel levelModel) {
        this.levelModel = levelModel;
    }

    /**
     * Method that needs to be called each frame to update the objects on the screen
     */
    public void update() {
        if (levelModel != null) {
            this.levelModel.getObjectsOnGameScreen().forEach(GameObject::update);
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


}
