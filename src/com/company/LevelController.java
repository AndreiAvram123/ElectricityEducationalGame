package com.company;

import com.company.interfaces.MovePlayerDiagonallyDownRight;
import com.company.interfaces.MovePlayerRight;
import com.company.models.*;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Observable;

public class LevelController extends Observable implements EventHandler {

    private LevelView levelView;
    private LevelModel levelModel;
    private AnimationTimer animationTimer;
    private ObjectHandler objectHandler;
    private GridSystem gridSystem;
    private CollisionHandler collisionHandler;
    private ElectricityHandler electricityHandler;
    private PlayerCollisionDetector playerCollisionDetector;
    private boolean shouldNotifyObserverOnFinish = true;
    private final int numberOfLevels;
    private int currentLevel = 1;
    private LevelDataReader levelDataReader;

    public LevelController(@NotNull LevelView levelView) {
        this.levelView = levelView;
        levelDataReader = new LevelDataReader(new GameObjectsFactory(levelView.getGraphicsContext()));
        gridSystem = new GridSystem(levelView.getGraphicsContext());
        objectHandler = new ObjectHandler(this.levelView.getCanvas(), gridSystem, levelView.getHintWindow());
        collisionHandler = new CollisionHandler();
        playerCollisionDetector = new PlayerCollisionDetector();
        playerCollisionDetector.addObserver(collisionHandler);
        electricityHandler = new ElectricityHandler();
        animationTimer = getAnimationTimer();
        this.numberOfLevels = levelDataReader.getNumberOfLevels();
        getLevelModel();
        attachEvents();
    }

    private void attachEvents() {
        this.levelView.getStartButton().setOnMouseClicked(this);
        this.levelView.getCurrentLayer().setOnKeyPressed(this);
    }


    @NotNull
    private AnimationTimer getAnimationTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!collisionHandler.isLevelCompleted()) {
                    playerCollisionDetector.checkCollisionWithPlayer();
                    gridSystem.updateGrid();
                    electricityHandler.update();
                } else {
                    animationTimer.stop();
                    if (shouldNotifyObserverOnFinish) {
                        shouldNotifyObserverOnFinish = false;
                        //set changed method needs to be
                        //called before notifying
                        //any observer
                        setChanged();
                        notifyObservers(levelModel.getLevelNumber());
                    }
                }
                levelView.update();
            }
        };
    }


    @Override
    public void handle(Event event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED && ((KeyEvent) event).getCode() == KeyCode.A) {
            objectHandler.rotateObject(levelModel.getPlayer());
        }
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (event.getSource() == this.levelView.getStartButton()) {
                if (this.levelView.getStartButton().getText().equals("Start")) {
                    this.levelView.getStartButton().setText("Restart");
                    electricityHandler.startElectricityHandler();
                    objectHandler.stop();
                    gridSystem.setGridLinesEnabled(false);
                } else {
                    this.levelView.getStartButton().setText("Start");
                    restartLevel();

                }
            }
        }

    }

    private void restartLevel() {
        getLevelModel();
        objectHandler.start();
        electricityHandler.stopElectricityHandler();
    }


    public void startLevel() {
        this.levelView.getCurrentLayer().setVisible(true);
        shouldNotifyObserverOnFinish = true;
        gridSystem.setGridLinesEnabled(true);
        animationTimer.start();
        objectHandler.start();
    }

    private void updateDependentComponents() {
        gridSystem.setLevelModel(levelModel);
        playerCollisionDetector.setLevelModel(levelModel);
        electricityHandler.setLevelModel(levelModel);
        levelView.setLevelModel(levelModel);
    }

    public void hideLevel() {
        this.levelView.getCurrentLayer().setVisible(false);
    }


    private void getLevelModel() {
        levelModel = new LevelModel(
                levelDataReader.getObjectsArrayFromJsonFile(currentLevel, "objectsOnScreen"),
                levelDataReader.getObjectsArrayFromJsonFile(currentLevel, "selectorPaneObjects"),
                levelDataReader.getPlayerFromJsonFile(currentLevel),
                levelDataReader.getHintAfterFinish(currentLevel),
                levelDataReader.getHintBeforeStart(currentLevel),
                currentLevel
        );
        updateObjectsStrategies(levelModel.getObjectsOnGameScreen());
        updateObjectsStrategies(levelModel.getObjectsOnSelectorPane());
        updateDependentComponents();
    }

    private void updateObjectsStrategies(@NotNull ArrayList<ObjectOnScreen> objects) {
        objects.forEach(gameObject -> {
            if (gameObject instanceof Rectangle) {
                ((Rectangle) gameObject).setElectricityReaction(new MovePlayerRight(levelModel.getPlayer()));
            }
            if (gameObject instanceof Triangle) {
                ((Triangle) gameObject).setElectricityReaction(new MovePlayerDiagonallyDownRight(levelModel.getPlayer()));
            }
            if (gameObject instanceof Fan) {
                ((Fan) gameObject).setElectricityReaction(new MovePlayerRight(levelModel.getPlayer()));
            }
        });
    }

    public void startNextLevel() {
        currentLevel++;
        getLevelModel();
        startLevel();
    }

    public int getNumberOfLevels() {
        return numberOfLevels;
    }

    public LevelModel getControllerLevelModel() {
        return levelModel;
    }
}
