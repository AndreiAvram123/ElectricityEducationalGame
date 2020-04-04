package com.company;

import com.company.models.Finish;
import com.sun.istack.internal.NotNull;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
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
    private int levelNumber;

    public Level(@NotNull Pane root, int levelNumber) {
        this.levelNumber = levelNumber;
        Canvas canvas = new Canvas(800, 600);
        this.root = root;
        this.root.getChildren().add(canvas);
        this.graphicsContext = canvas.getGraphicsContext2D();
        //get selectable objects should be made abstract in the super class
        this.gameObjectsFactory = new GameObjectsFactory(graphicsContext);
        player = Player.createInstance(200, 200, graphicsContext);
        gridSystem = new GridSystem(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight());
        addObjectsToLevel();

        initializeStartButton();
        objectGrabber = new ObjectGrabber(canvas, gridSystem);
        objectGrabber.start();
        collisionDetector = new CollisionDetector(gridSystem.getGameScreenObjects(), player);
        collisionHandler = new CollisionHandler();
        collisionDetector.addObserver(collisionHandler);
    }

    private void addObjectsToLevel() {
        gridSystem.addObjectsToGameScreen(getStaticObjects());
        gridSystem.addObjectsToSelectorPane(getSelectorPaneObjects());
    }

    private void initializeStartButton() {
        Button button = new Button();
        button.setMinSize(100, 30);
        button.setLayoutX(350);
        button.setLayoutY(610);
        button.setText("Start");
        this.root.getChildren().add(button);
        button.setOnMouseClicked(event -> electricityHandler = new ElectricityHandler(gridSystem.getGameScreenObjects()));

    }

    private ArrayList<GameObject> getStaticObjects() {

        ArrayList<GameObject> toReturn = new ArrayList<>();
        toReturn.add(gameObjectsFactory.createObject("rectangle", 200, 250));
        toReturn.add(gameObjectsFactory.createObject("rectangle", 200, 250));
        toReturn.add(gameObjectsFactory.createObject("rectangle", 250, 250));
        toReturn.add(gameObjectsFactory.createObject("triangle", 300, 250));
        toReturn.add(gameObjectsFactory.createObject("rectangle", 300, 300));
        toReturn.add(gameObjectsFactory.createObject("rectangle", 300, 300));
        toReturn.add(gameObjectsFactory.createObject("triangle", 350, 300));
        toReturn.add(gameObjectsFactory.createObject("triangle", 400, 350));
        toReturn.add(gameObjectsFactory.createObject("rectangle", 350, 350));
        toReturn.add(gameObjectsFactory.createObject("rectangle", 400, 400));
        toReturn.add(gameObjectsFactory.createObject("rectangle", 450, 400));
        toReturn.add(gameObjectsFactory.createObject("rectangle", 500, 400));
        toReturn.add(Finish.getInstance(550, 400, graphicsContext));
        return toReturn;
    }

    private ArrayList<GameObject> getSelectorPaneObjects() {
        ArrayList<GameObject> toReturn = new ArrayList<>();
        GameObject gameObject = gameObjectsFactory.createObject("wind_turbine", 400, 500);
        gameObject.enableDrag();
        toReturn.add(gameObject);
        return toReturn;
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
                    notifyObservers(levelNumber);
                }

            }
        };
        animationTimer.start();

    }


}
