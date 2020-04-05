package com.company;

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
    private final Canvas canvas;
    protected GraphicsContext graphicsContext;
    private GameObjectsFactory gameObjectsFactory;
    private AnimationTimer animationTimer;
    private ObjectDragger objectDragger;
    private GridSystem gridSystem;
    private CollisionDetector collisionDetector;
    private CollisionHandler collisionHandler;
    private ElectricityHandler electricityHandler;
    private int levelNumber;
    private Player player;


    public Level(@NotNull Pane root, int levelNumber) {
        this.levelNumber = levelNumber;
        this.canvas = new Canvas(800, 600);
        this.root = root;
        this.root.getChildren().add(canvas);
        this.graphicsContext = canvas.getGraphicsContext2D();
        //get selectable objects should be made abstract in the super class
        this.gameObjectsFactory = new GameObjectsFactory(graphicsContext);
        initializeLevel(canvas);
        initializeStartRestartButton();
    }

    private void initializeLevel(@NotNull Canvas canvas) {
        gridSystem = new GridSystem(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight());
        objectDragger = new ObjectDragger(canvas, gridSystem);
        objectDragger.start();
        addObjectsToLevel();
        player = Player.getInstance();
        collisionDetector = new CollisionDetector(gridSystem.getGameScreenObjects(), player);
        collisionHandler = new CollisionHandler();
        collisionDetector.addObserver(collisionHandler);
    }


    private void addObjectsToLevel() {
        LevelDataReader levelDataReader = new LevelDataReader(graphicsContext);
        String level = Integer.toString(levelNumber);
        ArrayList<GameObject> gameScreenObjects = levelDataReader.getObjectsArrayFromJsonFile(level, "objectsOnScreen");
        ArrayList<GameObject> gameObjectsSelectorPane = levelDataReader.getObjectsArrayFromJsonFile(level, "selectorPaneObjects");
        gridSystem.addObjectsToGameScreen(gameScreenObjects);
        gridSystem.addObjectsToSelectorPane(gameObjectsSelectorPane);
    }


    private void initializeStartRestartButton() {
        Button button = new Button();
        button.setMinSize(100, 30);
        button.setLayoutX(350);
        button.setLayoutY(610);
        button.setText("Start");
        this.root.getChildren().add(button);
        button.setOnMouseClicked(event -> {
            if (button.getText().equals("Start")) {
                button.setText("Restart");
                electricityHandler = new ElectricityHandler(gridSystem.getGameScreenObjects());
                gridSystem.disableMovement();
                gridSystem.setGridLinesEnabled(false);
            } else {
                restartLevel();
                button.setText("Start");
            }
        });

    }

    private void restartLevel() {
        initializeLevel(canvas);
        showLevel();
    }



    public void showLevel() {
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
