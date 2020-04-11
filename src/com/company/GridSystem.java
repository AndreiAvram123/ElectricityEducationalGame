package com.company;

import com.company.models.LevelModel;
import com.company.models.ObjectOnScreen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The grid system that appears on the screen
 * The main role is to position the elements in theirs corresponding positions
 */
public class GridSystem {

    private final GraphicsContext graphicsContext;
    private final double gridHeight = AppConstants.GRID_SYSTEM_HEIGHT;
    private final double gridWidth = AppConstants.SCREEN_WIDTH;
    private boolean gridLinesEnabled = true;
    private LevelModel levelModel;

    public GridSystem(@NotNull GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        drawGrid();
    }

    public void setLevelModel(@NotNull LevelModel levelModel) {
        this.levelModel = levelModel;
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


    /**
     * Method used to return (if exists ) the currently object that has the
     * mouse over
     *
     * @param mouseX - the mouse x position
     * @param mouseY - the mouse y position
     */
    @Nullable
    public ObjectOnScreen getObjectMouseOver(double mouseX, double mouseY) {
        for (ObjectOnScreen objectOnScreen : levelModel.getObjectsOnGameScreen()) {
            if (objectOnScreen.getX() < mouseX && objectOnScreen.getX() + objectOnScreen.getWidth() > mouseX
                    && objectOnScreen.getY() < mouseY && objectOnScreen.getY() + objectOnScreen.getHeight() > mouseY) {
                return objectOnScreen;
            }
        }
        return null;
    }

    /**
     * Determine if an object is on top of another
     * Useful to determine whether the user wants to place an
     * object on top of another
     *
     * @param object
     * @return
     */
    public boolean isObjectOverAnother(@NotNull ObjectOnScreen object) {
        for (ObjectOnScreen objectOnScreen : levelModel.getObjectsOnGameScreen()) {
            if (objectOnScreen != object && objectOnScreen.getX() == object.getX() && object.getY() == objectOnScreen.getY()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Method that needs to called each frame to update the grid
     */
    public void updateGrid() {
        drawGrid();
        drawSelectorPane();
    }

    /**
     * Method used in order to snap an object on the grid
     *
     * @param objectOnScreen
     */
    public void snapOnGrid(@NotNull ObjectOnScreen objectOnScreen) {
        //get the center of the object
        double oldCenterX = objectOnScreen.getX() + objectOnScreen.getWidth() / 2;
        double oldCenterY = objectOnScreen.getY() + objectOnScreen.getHeight() / 2;


        double newX = ((int) (oldCenterX / 50)) * 50;
        double newY = ((int) (oldCenterY / 50)) * 50;
        objectOnScreen.setX(newX);
        objectOnScreen.setY(newY);
    }


    public void setGridLinesEnabled(boolean gridLinesEnabled) {
        this.gridLinesEnabled = gridLinesEnabled;
    }

    /**
     * Enable the grid lines
     */
    public void enableGridLines() {
        gridLinesEnabled = true;
    }
}
