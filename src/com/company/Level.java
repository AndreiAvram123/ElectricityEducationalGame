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
    private GameObjectsFactory gameObjectsFactory;
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
        this.gameObjectsFactory = new GameObjectsFactory(graphicsContext);
        gridSystem = new GridSystem(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight());
        objectGrabber = new ObjectGrabber(canvas, gridSystem);
        objectGrabber.start();
        player = Player.createInstance(200, 200, graphicsContext);
        collisionDetector = new CollisionDetector(gridSystem.getObjectsOnScreen(), player);
        collisionHandler = new CollisionHandler();
        electricityHandler = new ElectricityHandler(gridSystem.getObjectsOnScreen());
        collisionDetector.addObserver(collisionHandler);
        addObjectsToLevel();

    }

    private void addObjectsToLevel() {


        gridSystem.addObjectToGrid(gameObjectsFactory.createObject("rectangle", 200, 250));
        gridSystem.addObjectToGrid(gameObjectsFactory.createObject("rectangle", 200, 250));
        gridSystem.addObjectToGrid(gameObjectsFactory.createObject("rectangle", 250, 250));
        gridSystem.addObjectToGrid(gameObjectsFactory.createObject("triangle", 300, 250));
        gridSystem.addObjectToGrid(gameObjectsFactory.createObject("rectangle", 300, 300));
        gridSystem.addObjectToGrid(gameObjectsFactory.createObject("rectangle", 300, 300));
        gridSystem.addObjectToGrid(gameObjectsFactory.createObject("triangle", 350, 300));
        gridSystem.addObjectToGrid(gameObjectsFactory.createObject("triangle", 400, 350));
        gridSystem.addObjectToGrid(gameObjectsFactory.createObject("wind_turbine", 450, 400));
        gridSystem.addObjectToGrid(gameObjectsFactory.createObject("rectangle", 150, 550));
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
