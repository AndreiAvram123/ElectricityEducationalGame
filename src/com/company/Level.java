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
public class Level implements Observer {
    protected GraphicsContext graphicsContext;
    private Canvas canvas;
    protected ArrayList<GameObject> selectableObjects = new ArrayList<>();
    private Factory factory;
    private AnimationTimer animationTimer;
    private ObjectGrabber objectGrabber;


    public Level(@NotNull Canvas canvas) {
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
        //get selectable objects should be made abstract in the super class
        this.factory = new Factory(graphicsContext);
        selectableObjects.addAll(getSelectableObjects());
    }

    @NotNull
    private ArrayList<GameObject> getSelectableObjects() {
        ArrayList<GameObject> gameObjects = new ArrayList<>();
        gameObjects.add(factory.createObject("bulb", 100, 525));
        gameObjects.add(factory.createObject("bulb", 200, 525));

        return gameObjects;
    }


    public void start() {

        objectGrabber = new ObjectGrabber(canvas, selectableObjects);
        objectGrabber.addObserver(this);

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
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
                canvas.getHeight()-100, 800, 100);
    }

    private void updateGame() {
        drawBackground();
        drawObjectSelectorPanel();
        selectableObjects.forEach(GameObject::update);
    }

    @Override
    public void update(Observable o, Object arg) {
        //find which observable  has triggered the notify method
        if(o instanceof ObjectGrabber){
            GameObject gameObject = (GameObject) arg;
            System.out.println(gameObject.x);
       }
    }
}
