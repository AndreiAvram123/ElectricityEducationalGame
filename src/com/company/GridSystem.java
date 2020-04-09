package com.company;

import com.company.models.NullObject;
import com.company.models.ObjectOnScreen;
import com.company.models.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GridSystem {

    private GraphicsContext graphicsContext;
    private final double gridHeight = AppConstants.SCREEN_HEIGHT;
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

    private void drawSelectorPane() {
        graphicsContext.setFill(Color.LIGHTBLUE);
        graphicsContext.fillRect(0, gridHeight - 100, gridWidth, 100);
    }

    public void addObjectToGameScreen(@NotNull ObjectOnScreen objectOnScreen) {
        levelModel.addObjectToGameScreen(objectOnScreen);
    }


    public ObjectOnScreen getObjectMouseOver(double mouseX, double mouseY) {
        for (ObjectOnScreen objectOnScreen : levelModel.getObjectsOnGameScreen()) {
            if (objectOnScreen.getX() < mouseX && objectOnScreen.getX() + objectOnScreen.getWidth() > mouseX
                    && objectOnScreen.getY() < mouseY && objectOnScreen.getY() + objectOnScreen.getHeight() > mouseY) {
                return objectOnScreen;
            }
        }
        for (ObjectOnScreen objectOnScreen : levelModel.getObjectsOnSelectorPane()) {
            if (objectOnScreen.getX() < mouseX && objectOnScreen.getX() + objectOnScreen.getWidth() > mouseX
                    && objectOnScreen.getY() < mouseY && objectOnScreen.getY() + objectOnScreen.getHeight() > mouseY) {
                return objectOnScreen;
            }
        }
        return null;
    }


    public boolean isObjectOverAnother(@NotNull ObjectOnScreen object) {
        for (ObjectOnScreen objectOnScreen : levelModel.getObjectsOnGameScreen()) {
            if (objectOnScreen != object && objectOnScreen.getX() == object.getX() && object.getY() == objectOnScreen.getY()) {
                return true;
            }
        }
        for (ObjectOnScreen objectOnScreen : levelModel.getObjectsOnSelectorPane()) {
            if (objectOnScreen != object && objectOnScreen.getX() == object.getX() && objectOnScreen.getY() == object.getY()) {
                return true;
            }
        }
        return false;
    }


    public void updateGrid() {
        drawGrid();
        drawSelectorPane();
    }

    public void snapOnGrid(@NotNull ObjectOnScreen objectOnScreen) {
        //get the center of the object
        Point oldCenter = new Point(objectOnScreen.getX() + objectOnScreen.getWidth() / 2,
                objectOnScreen.getY() + objectOnScreen.getHeight() / 2);


        double newX = ((int) (oldCenter.getX() / 50)) * 50;
        double newY = ((int) (oldCenter.getY() / 50)) * 50;
        objectOnScreen.setX(newX);
        objectOnScreen.setY(newY);
    }


    public void setGridLinesEnabled(boolean gridLinesEnabled) {
        this.gridLinesEnabled = gridLinesEnabled;
    }
}
