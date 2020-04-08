package com.company;

import com.company.models.GameObject;
import com.company.models.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GridSystem {

    private GraphicsContext graphicsContext;
    private double gridHeight;
    private double gridWidth;
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
            if (gameObject.getX() < mouseX && gameObject.getX() + gameObject.getWidth() > mouseX
                    && gameObject.getY() < mouseY && gameObject.getY() + gameObject.getHeight() > mouseY) {
                return gameObject;
            }
        }
        for (GameObject gameObject : objectsInSelectorPane) {
            if (gameObject.getX() < mouseX && gameObject.getX() + gameObject.getWidth() > mouseX
                    && gameObject.getY() < mouseY && gameObject.getY() + gameObject.getHeight() > mouseY) {
                return gameObject;
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
        Point oldCenter = new Point(gameObject.getX() + gameObject.getWidth()/2,
                                    gameObject.getY() + gameObject.getHeight()/2);

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
