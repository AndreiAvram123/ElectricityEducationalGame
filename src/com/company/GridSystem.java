package com.company;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GridSystem {

    private GraphicsContext graphicsContext;
    private int rectangleWidth = 50;
    private double gridHeight;
    private double gridWidth;
    private double objectSelectorHeight = 100;
    private ArrayList<GameObject> gameScreenObjects = new ArrayList<>();
    private ArrayList<GameObject> objectsInSelectorPane = new ArrayList<>();
    private boolean gridLinesEnabled = true;

    public GridSystem(@NotNull GraphicsContext graphicsContext, double gridWidth, double gridHeight) {
        this.graphicsContext = graphicsContext;
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        drawGameScreen();
    }

    public ArrayList<GameObject> getGameScreenObjects() {
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
                graphicsContext.fillRect(indexWidth, indexHeight, rectangleWidth, rectangleWidth);
                if (gridLinesEnabled) {
                    graphicsContext.setStroke(Color.BLACK);
                    graphicsContext.strokeRect(indexWidth, indexHeight, rectangleWidth, rectangleWidth);
                }
            }
        }
        graphicsContext.setFill(Color.LIGHTBLUE);
        graphicsContext.fillRect(0, gridHeight - 100, gridWidth, 100);
    }

    public void addObjectToGameScreen(@NotNull GameObject gameObject) {
        gameScreenObjects.add(gameObject);
        if (objectsInSelectorPane.contains(gameObject)) {
            objectsInSelectorPane.remove(gameObject);
        }
    }

    public void addObjectsToGameScreen(@NotNull ArrayList<GameObject> gameObjects) {
        gameScreenObjects.addAll(gameObjects);
    }

    public void addObjectsToSelectorPane(@NotNull ArrayList<GameObject> gameObjects) {
        objectsInSelectorPane.addAll(gameObjects);
    }

    public void removeGameScreenObject( GameObject gameObject) {
        gameScreenObjects.remove(gameObject);
    }


    public GameObject getObjectMouseOver(double mouseX, double mouseY) {
        for (GameObject gameObject : gameScreenObjects) {
            if (gameObject.x < mouseX && gameObject.x + gameObject.width > mouseX
                    && gameObject.y < mouseY && gameObject.y + gameObject.height > mouseY) {
                return gameObject;
            }
        }
        for (GameObject gameObject : objectsInSelectorPane) {
            if (gameObject.x < mouseX && gameObject.x + gameObject.width > mouseX
                    && gameObject.y < mouseY && gameObject.y + gameObject.height > mouseY) {
                return gameObject;
            }
        }
        return null;
    }


    public boolean isObjectOverAnother(@NotNull GameObject object) {
        for (GameObject gameObject : gameScreenObjects) {
            if (gameObject != object && gameObject.x == object.x && gameObject.y == object.y) {
                return true;
            }
        }
        for (GameObject gameObject : objectsInSelectorPane) {
            if (gameObject != object && gameObject.x == object.x && gameObject.y == object.y) {
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
        double centerX = gameObject.x + gameObject.width / 2;
        double centerY = gameObject.y + gameObject.width / 2;

        double newX = ((int) (centerX / rectangleWidth)) * rectangleWidth;
        double newY = ((int) (centerY / rectangleWidth)) * rectangleWidth;
        gameObject.x = newX;
        gameObject.y = newY;
    }

    public void disableObjectsDrag() {
        gameScreenObjects.forEach(gameObject -> gameObject.setHasDragEnabled(false));
        objectsInSelectorPane.forEach(gameObject -> gameObject.setHasDragEnabled(false));
    }


    public void setGridLinesEnabled(boolean gridLinesEnabled) {
        this.gridLinesEnabled = gridLinesEnabled;
    }
}
