package com.company;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GridSystem {

    private GraphicsContext graphicsContext;
    private double objectSelectorHeight = 100;
    private int rectangleWidth = 50;
    private double levelHeight;
    private double levelWidth;
    private ArrayList<GameObject> objectsOnScreen = new ArrayList<>();

    public GridSystem(@NotNull GraphicsContext graphicsContext, double levelWidth, double levelHeight) {
        this.graphicsContext = graphicsContext;
        this.levelHeight = levelHeight;
        this.levelWidth = levelWidth;
        //test


        drawGameScreen();
        drawGameScreen();
    }


    private void drawGameScreen() {
        for (int indexWidth = 0; indexWidth <= levelWidth; indexWidth += 50) {
            for (int indexHeight = 0; indexHeight <= levelHeight - objectSelectorHeight; indexHeight += 50) {
                graphicsContext.setFill(Color.BLUE);
                graphicsContext.fillRect(indexWidth, indexHeight, rectangleWidth, rectangleWidth);
                graphicsContext.setStroke(Color.BLACK);
                graphicsContext.strokeRect(indexWidth, indexHeight, rectangleWidth, rectangleWidth);
            }

        }
    }

    private void drawObjectSelectorPane() {
        graphicsContext.setFill(Color.PINK);
        graphicsContext.fillRect(0, levelHeight - objectSelectorHeight, levelWidth, objectSelectorHeight);
    }

    public void addObjectToGrid(@NotNull GameObject gameObject) {
        objectsOnScreen.add(gameObject);
    }

    public GameObject getObjectSelected(double mouseX, double mouseY) {
        for (GameObject gameObject : objectsOnScreen) {
            if (gameObject.x < mouseX && gameObject.x + gameObject.width > mouseX
                    && gameObject.y < mouseY && gameObject.y + gameObject.height > mouseY) {
                return gameObject;
            }
        }
        //todo
        //maybe replace with null object design pattern
        return null;
    }

    public void updateGrid() {
        drawGameScreen();
        drawObjectSelectorPane();
        objectsOnScreen.forEach(GameObject::update);
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
}
