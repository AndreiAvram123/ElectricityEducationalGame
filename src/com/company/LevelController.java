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

    private final LevelView levelView;
    private LevelModel levelModel;
    private final ObjectHandler objectHandler;
    private final GridSystem gridSystem;
    private final ElectricityHandler electricityHandler;
    private final PlayerCollisionDetector playerCollisionDetector;
    private boolean shouldNotifyObserverOnFinish = true;
    private final int numberOfLevels;
    private final LevelDataReader levelDataReader;

    public LevelController(@NotNull LevelView levelView) {
        this.levelView = levelView;
        levelDataReader = new LevelDataReader(new GameObjectsFactory(levelView.getGraphicsContext()));
        gridSystem = new GridSystem(levelView.getGraphicsContext());
        objectHandler = new ObjectHandler(this.levelView.getCanvas(), gridSystem, levelView.getHintWindow());
        playerCollisionDetector = new PlayerCollisionDetector();
        electricityHandler = new ElectricityHandler();
        AnimationTimer animationTimer = getAnimationTimer();
        animationTimer.start();
        this.numberOfLevels = levelDataReader.getNumberOfLevels();
        getNextLevelModel();
        attachEvents();
    }

    private void attachEvents() {
        this.levelView.getStartButton().setOnMouseClicked(this);
        this.levelView.getCurrentLayer().setOnKeyPressed(this);
        this.levelView.getStartButton().setOnMouseEntered(this);
    }


    @NotNull
    private AnimationTimer getAnimationTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!playerCollisionDetector.hasCollidedWithFinish()) {
                    playerCollisionDetector.checkCollisionWithPlayer();
                    gridSystem.updateGrid();
                    electricityHandler.update();
                } else {
                    if (shouldNotifyObserverOnFinish) {
                        shouldNotifyObserverOnFinish = false;
                        //set changed method needs to be
                        //called before notifying
                        //any observe
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
        if (event.getSource() == this.levelView.getStartButton()) {
            if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
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
        if(event.getEventType() == MouseEvent.MOUSE_ENTERED){
            AudioManager.getInstance().playButtonHoverSound();
        }

    }

    private void restartLevel() {
        getLevelModel(levelModel.getLevelNumber());
        updateModelAfterFetch();
        objectHandler.start();
        electricityHandler.stopElectricityHandler();
    }


    public void startLevel() {
        this.levelView.getCurrentLayer().setVisible(true);
        shouldNotifyObserverOnFinish = true;
        objectHandler.start();
    }

    private void updateDependentComponents() {
        gridSystem.setLevelModel(levelModel);
        playerCollisionDetector.setLevelModel(levelModel);
        electricityHandler.setLevelModel(levelModel);
        levelView.setLevelModel(levelModel);
        gridSystem.reset();
    }

    public void hideLevel() {
        this.levelView.getCurrentLayer().setVisible(false);
    }


    private void getNextLevelModel() {
        int currentLevel = 1;
        if (levelModel != null) {
            currentLevel = levelModel.getLevelNumber() + 1;
        }
        getLevelModel(currentLevel);
        updateModelAfterFetch();
    }

    private void updateModelAfterFetch() {
        updateObjectsStrategies(levelModel.getObjectsOnGameScreen());
        updateObjectsStrategies(levelModel.getObjectsOnSelectorPane());
        updateDependentComponents();

    }

    private void getLevelModel(int currentLevel) {
        levelModel = new LevelModel(
                levelDataReader.getObjectsArrayFromJsonFile(currentLevel, "objectsOnScreen"),
                levelDataReader.getObjectsArrayFromJsonFile(currentLevel, "selectorPaneObjects"),
                levelDataReader.getPlayerFromJsonFile(currentLevel),
                levelDataReader.getHintAfterFinish(currentLevel),
                levelDataReader.getHintBeforeStart(currentLevel),
                currentLevel
        );
    }

    private void updateObjectsStrategies(@NotNull ArrayList<ObjectOnScreen> objects) {
        objects.forEach(gameObject -> {
            if (gameObject instanceof Rectangle) {
                ((Rectangle) gameObject).setElectricityReaction(new MovePlayerRight(levelModel.getPlayer()));
            }
            if (gameObject instanceof Slope) {
                ((Slope) gameObject).setElectricityReaction(new MovePlayerDiagonallyDownRight(levelModel.getPlayer()));
            }
            if (gameObject instanceof Fan) {
                ((Fan) gameObject).setElectricityReaction(new MovePlayerRight(levelModel.getPlayer()));
            }
        });
    }

    public void getNextLevel() {
        getNextLevelModel();
    }

    public int getNumberOfLevels() {
        return numberOfLevels;
    }

    public LevelModel getControllerLevelModel() {
        return levelModel;
    }
}
