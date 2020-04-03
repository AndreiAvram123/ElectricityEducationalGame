package com.company;

import com.company.models.Finish;
import com.sun.istack.internal.NotNull;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.Observable;

/**
 * Level class that stores all the necessary data for
 * the current level
 */
public class Level extends Observable {

    private final Pane root;
    protected GraphicsContext graphicsContext;
    private GameObjectsFactory gameObjectsFactory;
    private AnimationTimer animationTimer;
    private ObjectGrabber objectGrabber;
    private GridSystem gridSystem;
    private CollisionDetector collisionDetector;
    private CollisionHandler collisionHandler;
    private Player player;
    private ElectricityHandler electricityHandler;
    private String levelTag = "1";

    public Level(@NotNull Pane root) {
        Canvas canvas = new Canvas(800, 600);
        this.root = root;
        this.root.getChildren().add(canvas);
        this.graphicsContext = canvas.getGraphicsContext2D();
        //get selectable objects should be made abstract in the super class
        this.gameObjectsFactory = new GameObjectsFactory(graphicsContext);
        gridSystem = new GridSystem(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight());
        objectGrabber = new ObjectGrabber(canvas, gridSystem);
        objectGrabber.start();
        player = Player.createInstance(200, 200, graphicsContext);
        collisionDetector = new CollisionDetector(gridSystem.getObjectsOnScreen(), player);
        collisionHandler = new CollisionHandler();
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
        gridSystem.addObjectToGrid(Finish.getInstance(550, 400, graphicsContext));
        gridSystem.addObjectToGrid(gameObjectsFactory.createObject("rectangle", 150, 550));

        Button button = new Button();
        button.setMinSize(100, 30);
        button.setLayoutX(350);
        button.setLayoutY(610);
        button.setText("Start");
        this.root.getChildren().add(button);
        button.setOnMouseClicked(event -> electricityHandler = new ElectricityHandler(gridSystem.getObjectsOnScreen()));
    }


    public void start() {

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                collisionDetector.checkCollisionWithPlayer();
                gridSystem.updateGrid();
                if (electricityHandler != null) {
                    electricityHandler.update();
                }
                player.update();
                if (collisionHandler.isLevelCompleted()) {
                    animationTimer.stop();
                    setChanged();
                    notifyObservers(levelTag);
                }
            }
        };
        animationTimer.start();

    }


}
