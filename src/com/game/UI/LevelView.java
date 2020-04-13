package com.game.UI;

import com.game.AppConstants;
import com.game.models.GameObject;
import com.game.models.LevelModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

/**
 * The class representing the view for the levels
 */
public class LevelView {

    private LevelModel levelModel;
    private final Pane layout;
    private final Canvas canvas;
    private final GraphicsContext graphicsContext;
    private final HintWindow hintWindow;
    private Button startButton;
    private final double gridHeight = AppConstants.GRID_HEIGHT;
    private final double gridWidth = AppConstants.SCREEN_WIDTH;
    private boolean gridLinesEnabled = true;

    public LevelView(@NotNull Pane layout) {
        this.layout = new Pane();
        this.layout.setMinHeight(layout.getHeight());
        this.layout.setMinWidth(layout.getWidth());
        this.canvas = new Canvas(AppConstants.SCREEN_WIDTH, AppConstants.SCREEN_HEIGHT);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.hintWindow = new HintWindow();
        initializeStartRestartButton();
        this.layout.getChildren().addAll(canvas, startButton, hintWindow.getLayout());
        layout.getChildren().add(this.layout);
        this.layout.setVisible(false);
    }

    public void setGridLinesEnabled(boolean gridLinesEnabled) {
        this.gridLinesEnabled = gridLinesEnabled;
    }

    private void initializeStartRestartButton() {
        //the button should have the width equal to the
        //difference between the screen height and the grid system height
        double buttonHeight = AppConstants.SCREEN_HEIGHT - AppConstants.GRID_HEIGHT;
        startButton = new Button();

        startButton.setMinSize(150, buttonHeight);
        startButton.setLayoutX(AppConstants.SCREEN_WIDTH / 2 - 100);
        startButton.setLayoutY(AppConstants.GRID_HEIGHT);
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
    public void updateView() {
        if (levelModel != null) {
            drawGrid();
            drawSelectorPane();
            this.levelModel.getObjectsOnGameScreen().forEach(GameObject::update);
            this.levelModel.getPlayer().update();
        }
    }

    /**
     * This method is used to draw all the rectangles that appear
     * on the screen
     */
    private void drawGrid() {
        for (int indexWidth = 0; indexWidth <= gridWidth; indexWidth += 50) {
            for (int indexHeight = 0; indexHeight <= gridHeight - 100; indexHeight += 50) {
                graphicsContext.setFill(Color.DARKBLUE);
                graphicsContext.fillRect(indexWidth, indexHeight, 50, 50);
                if (gridLinesEnabled) {
                    graphicsContext.setStroke(Color.BLACK);
                    graphicsContext.strokeRect(indexWidth, indexHeight, 50, 50);
                }
            }
        }
    }
    /**
     * The selector pane represent a small area at
     * the bottom of the screen that stores draggable objects
     * There is nothing special about this area expect that it
     * has a lightBlue color
     */
    private void drawSelectorPane() {
        graphicsContext.setFill(Color.LIGHTBLUE);
        graphicsContext.fillRect(0, gridHeight - 100, gridWidth, 100);
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

    public Pane getLayout() {
        return layout;
    }


}
