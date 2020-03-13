package com.company;

import com.sun.istack.internal.NotNull;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Level class that stores all the necessary data for
 * the current level
 */
public class Level {
    protected GraphicsContext graphicsContext;
    private Canvas canvas;
    protected ArrayList<GameObject> selectableObjects = new ArrayList<>();
    private Factory factory;
    private AnimationTimer animationTimer;
    private ObjectGrabber objectGrabber;
    private GridSystem gridSystem;

    public Level(@NotNull Canvas canvas) {
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
        //get selectable objects should be made abstract in the super class
        this.factory = new Factory(graphicsContext);
        gridSystem = new GridSystem(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight());
        objectGrabber = new ObjectGrabber(canvas, gridSystem);
        gridSystem.addObjectToGrid(factory.createObject("bulb", 50, canvas.getHeight() - 50));
        gridSystem.addObjectToGrid(factory.createObject("bulb", 150, canvas.getHeight() - 50));
    }


    public void start() {

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gridSystem.updateGrid();
            }
        };
        animationTimer.start();

    }

    private void drawBackground() {
        this.graphicsContext.setFill(Color.ALICEBLUE);
        this.graphicsContext.fillRect(0, 0, 800, 600);
    }

    private void drawObjectSelectorPanel() {
        this.graphicsContext.setFill(Color.GREY);
        this.graphicsContext.fillRect(0,
                canvas.getHeight() - 100, 800, 100);
    }


}
