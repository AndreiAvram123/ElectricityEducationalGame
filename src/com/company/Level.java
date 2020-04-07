package com.company;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Level class that stores all the necessary data for
 * the current level
 */
public class Level extends Observable {

    private final Pane layer;
    private final Canvas canvas;
    protected GraphicsContext graphicsContext;
    private AnimationTimer animationTimer;
    private ObjectDragger objectDragger;
    private GridSystem gridSystem;
    private CollisionDetector collisionDetector;
    private CollisionHandler collisionHandler;
    private ElectricityHandler electricityHandler;
    private int levelNumber;
    private String hintBeforeStart;
    private String hintAfterFinish;
    private Player player;
    private LevelDataReader levelDataReader;
    private boolean shouldNotifyObserver = true;


    public Level(@NotNull Pane root, int levelNumber) {
        this.levelNumber = levelNumber;
        this.layer = new Pane();
        this.layer.setPrefHeight(root.getHeight());
        this.layer.setPrefWidth(root.getWidth());
        this.layer.setVisible(false);
        root.getChildren().add(layer);
        this.canvas = new Canvas(AppConstants.SCREEN_WIDTH, AppConstants.SCREEN_HEIGHT);
        this.layer.getChildren().add(canvas);
        this.graphicsContext = canvas.getGraphicsContext2D();
        levelDataReader = new LevelDataReader(graphicsContext);
        this.hintBeforeStart = levelDataReader.getHintBeforeStart(levelNumber);
        this.hintAfterFinish = levelDataReader.getHintAfterFinish(levelNumber);
    }

    public String getHintBeforeStart() {
        return hintBeforeStart;
    }

    public String getHintAfterFinish() {
        return hintAfterFinish;
    }

    private void initializeLevel() {
        gridSystem = new GridSystem(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight());
        objectDragger = new ObjectDragger(canvas, gridSystem);
        objectDragger.start();
        addObjectsToLevel();
        player = Player.getInstance();
        collisionDetector = new CollisionDetector(gridSystem.getGameScreenObjects(), player);
        collisionHandler = new CollisionHandler();
        collisionDetector.addObserver(collisionHandler);
        electricityHandler = new ElectricityHandler(gridSystem.getGameScreenObjects());
    }


    private void addObjectsToLevel() {
        ArrayList<GameObject> gameScreenObjects = levelDataReader.getObjectsArrayFromJsonFile(levelNumber, "objectsOnScreen");
        ArrayList<GameObject> gameObjectsSelectorPane = levelDataReader.getObjectsArrayFromJsonFile(levelNumber, "selectorPaneObjects");
        gridSystem.addObjectsToGameScreen(gameScreenObjects);
        gridSystem.addObjectsToSelectorPane(gameObjectsSelectorPane);
    }


    private void initializeStartRestartButton() {
        Button button = new Button();
        button.setMinSize(100, 30);
        button.setLayoutX(350);
        button.setLayoutY(610);
        button.setText("Start");
        this.layer.getChildren().add(button);
        button.setOnMouseClicked(event -> {
            if (button.getText().equals("Start")) {
                button.setText("Restart");
                electricityHandler.startElectricityHandler();
                gridSystem.disableMovement();
                gridSystem.setGridLinesEnabled(false);
            } else {
                restartLevel();
                button.setText("Start");
            }
        });

    }

    private void restartLevel() {
        initializeLevel();
        start();
    }


    public void showLevel() {
        initializeLevel();
        initializeStartRestartButton();
        start();
    }

    public void start() {
        this.layer.setVisible(true);
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!collisionHandler.isLevelCompleted()) {
                    collisionDetector.checkCollisionWithPlayer();
                    gridSystem.updateGrid();
                    electricityHandler.update();
                    player.update();
                }else{
                    animationTimer.stop();
                    if(shouldNotifyObserver){
                        setChanged();
                        notifyObservers(levelNumber);
                    }
                }
            }
        };
        animationTimer.start();

    }


    public void hide() {
        this.layer.setVisible(false);
    }
}
