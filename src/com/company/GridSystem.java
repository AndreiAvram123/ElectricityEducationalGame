package com.company;

import com.company.models.GameObject;
import com.company.models.ObjectOnScreen;
import com.company.models.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GridSystem {

    private GraphicsContext graphicsContext;
    private double gridHeight;
    private double gridWidth;
    private ArrayList<ObjectOnScreen> gameScreenObjects = new ArrayList<>();
    private ArrayList<ObjectOnScreen> objectsInSelectorPane = new ArrayList<>();
    private boolean gridLinesEnabled = true;

    public GridSystem(@NotNull GraphicsContext graphicsContext, double gridWidth, double gridHeight) {
        this.graphicsContext = graphicsContext;
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        drawGameScreen();
    }

    public ArrayList<ObjectOnScreen> getGameScreenObjects() {
        return gameScreenObjects;
    }

    /**
     * This method is used to draw all the rectangles that appear
     * on the screen
     */
    private void drawGameScreen() {

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
        graphicsContext.setFill(Color.LIGHTBLUE);
        graphicsContext.fillRect(0, gridHeight - 100, gridWidth, 100);
    }

    public void addObjectToGameScreen(@NotNull ObjectOnScreen objectOnScreen) {
        gameScreenObjects.add(objectOnScreen);
        if (objectsInSelectorPane.contains(objectOnScreen)) {
            objectsInSelectorPane.remove(objectOnScreen);
        }
    }

    public void addObjectsToGameScreen(@NotNull ArrayList<ObjectOnScreen> objectOnScreens) {
        gameScreenObjects.addAll(objectOnScreens);
    }

    public void addObjectsToSelectorPane(@NotNull ArrayList<ObjectOnScreen> objectOnScreens) {
        objectsInSelectorPane.addAll(objectOnScreens);
    }


    public ObjectOnScreen getObjectMouseOver(double mouseX, double mouseY) {
        for (ObjectOnScreen objectOnScreen : gameScreenObjects) {
            if (objectOnScreen.getX() < mouseX && objectOnScreen.getX() + objectOnScreen.getWidth() > mouseX
                    && objectOnScreen.getY() < mouseY && objectOnScreen.getY() + objectOnScreen.getHeight() > mouseY) {
                return objectOnScreen;
            }
        }
        for (ObjectOnScreen objectOnScreen : objectsInSelectorPane) {
            if (objectOnScreen.getX() < mouseX && objectOnScreen.getX() + objectOnScreen.getWidth() > mouseX
                    && objectOnScreen.getY() < mouseY && objectOnScreen.getY() + objectOnScreen.getHeight() > mouseY) {
                return objectOnScreen;
            }
        }
        return null;
    }


    public boolean isObjectOverAnother(@NotNull GameObject object) {
        for (GameObject gameObject : gameScreenObjects) {
            if (gameObject != object && gameObject.getX() == object.getX() && gameObject.getY() == object.getY()) {
                return true;
            }
        }
        for (GameObject gameObject : objectsInSelectorPane) {
            if (gameObject != object && gameObject.getX() == object.getX() && gameObject.getY() == object.getY()) {
                return true;
            }
        }
        return false;
    }


    public void updateGrid() {
        drawGameScreen();
        gameScreenObjects.forEach(gameObject -> gameObject.update());
        objectsInSelectorPane.forEach(gameObject -> gameObject.update());
    }

    public void snapOnGrid(@NotNull GameObject gameObject) {
        //get the center of the object
        Point oldCenter = new Point(gameObject.getX() + gameObject.getWidth() / 2,
                gameObject.getY() + gameObject.getHeight() / 2);

        double newX = ((int) (oldCenter.getX() / 50)) * 50;
        double newY = ((int) (oldCenter.getY() / 50)) * 50;
        gameObject.setX(newX);
        gameObject.setY(newY);
    }

    public void disableObjectsDrag() {
        gameScreenObjects.forEach(gameObject -> gameObject.setHasDragEnabled(false));
        objectsInSelectorPane.forEach(gameObject -> gameObject.setHasDragEnabled(false));
    }


    public void setGridLinesEnabled(boolean gridLinesEnabled) {
        this.gridLinesEnabled = gridLinesEnabled;
    }
}
