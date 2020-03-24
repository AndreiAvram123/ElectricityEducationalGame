package com.company;

import com.company.models.ElectricitySource;
import com.sun.istack.internal.NotNull;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Level class that stores all the necessary data for
 * the current level
 */
public class Level {

    protected GraphicsContext graphicsContext;
    private Factory factory;
    private AnimationTimer animationTimer;
    private ObjectGrabber objectGrabber;
    private GridSystem gridSystem;
    private CollisionDetector collisionDetector;
    private CollisionHandler collisionHandler;
    private Player player;
    private ElectricityHandler electricityHandler;

    public Level(@NotNull Canvas canvas) {
        this.graphicsContext = canvas.getGraphicsContext2D();
        //get selectable objects should be made abstract in the super class
        this.factory = new Factory(graphicsContext);
        gridSystem = new GridSystem(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight());
        objectGrabber = new ObjectGrabber(canvas, gridSystem);
        objectGrabber.start();
        player = Player.createInstance(200, 200, graphicsContext);
        collisionDetector = new CollisionDetector(gridSystem.getObjectsOnScreen(), player);
        collisionHandler = new CollisionHandler();
        electricityHandler = new ElectricityHandler(gridSystem.getObjectsOnScreen());
        collisionDetector.addObserver(collisionHandler);
        addObjectsToLevel(canvas);

    }

    private void addObjectsToLevel(@NotNull Canvas canvas) {
        gridSystem.addObjectToGrid(factory.createObject("bulb", 50, canvas.getHeight() - 50));
        gridSystem.addObjectToGrid(factory.createObject("bulb", 150, canvas.getHeight() - 50));
        gridSystem.addObjectToGrid(factory.createObject("launcher", 250, canvas.getHeight() - 50));
        gridSystem.addObjectToGrid(factory.createObject("rectangle", 150, 250));
        gridSystem.addObjectToGrid(factory.createObject("rectangle", 200, 250));
        gridSystem.addObjectToGrid(factory.createObject("rectangle", 250, 250));
        gridSystem.addObjectToGrid(factory.createObject("triangle", 300, 250));
        gridSystem.addObjectToGrid(factory.createObject("rectangle", 300, 300));
        gridSystem.addObjectToGrid(factory.createObject("triangle", 350, 300));
        gridSystem.addObjectToGrid(factory.createObject("triangle", 400, 350));
        gridSystem.addObjectToGrid(factory.createObject("wind_turbine", 450, 400));
        gridSystem.addObjectToGrid(new ElectricitySource(500,500,graphicsContext));
    }


    public void start() {

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                collisionDetector.checkCollisionWithPlayer();
                gridSystem.updateGrid();
                electricityHandler.update();
                player.update();

            }
        };
        animationTimer.start();

    }


}
