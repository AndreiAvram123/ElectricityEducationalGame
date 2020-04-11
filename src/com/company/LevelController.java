package com.company;

import com.company.UI.LevelView;
import com.company.collision.PlayerCollisionDetector;
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

/**
 * Controller responsible for handling interactions inside the level
 * It can be observed and notifies any relevant observers when the level is finished
 */
public class LevelController extends Observable implements EventHandler<Event> {

    private final LevelView levelView;
    private LevelModel levelModel;
    private final ObjectHandler objectHandler;
    private final GridSystem gridSystem;
    private final ElectricityHandler electricityHandler;
    private final PlayerCollisionDetector playerCollisionDetector;
    private boolean shouldNotifyObserverOnFinish = true;
    private final int numberOfLevels;
    private final LevelDataReader levelDataReader;
    private State currentState = State.STOPPED;

    public LevelController(@NotNull LevelView levelView) {
        this.levelView = levelView;
        levelDataReader = new LevelDataReader(new GameObjectsFactory(levelView.getGraphicsContext()));
        gridSystem = new GridSystem(levelView.getGraphicsContext());
        objectHandler = new ObjectHandler(this.levelView.getCanvas(), gridSystem, levelView.getHintWindow());
        playerCollisionDetector = new PlayerCollisionDetector();
        electricityHandler = new ElectricityHandler();
        getAnimationTimer().start();
        this.numberOfLevels = levelDataReader.getNumberOfLevels();
        getNextLevelModel();
        attachEvents();
    }

    /**
     * Enum class used to represent the state of the level
     */
    private enum State {
        STARTED, STOPPED;
    }

    private void attachEvents() {
        this.levelView.getStartButton().setOnMouseClicked(this);
        this.levelView.getCurrentLayer().setOnKeyPressed(this);
        this.levelView.getStartButton().setOnMouseEntered(this);
    }


    /**
     * @return An animation timer responsible for updating the UI each frame
     * Inside the handle() method it makes relevant calls to objects such as grid system
     */
    @NotNull
    private AnimationTimer getAnimationTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!playerCollisionDetector.hasCollidedWithFinish()) {
                    playerCollisionDetector.checkCollisionWithPlayer();
                    gridSystem.updateGrid();
                    if (currentState == State.STOPPED) {
                        electricityHandler.update();
                    }
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


    /**
     * Handle event used to process events that are happening on the screen
     */
    @Override
    public void handle(Event event) {
        //if the user pressed A then rotate the object
        if (event.getEventType() == KeyEvent.KEY_PRESSED && ((KeyEvent) event).getCode() == KeyCode.A) {
            objectHandler.rotateObjectCurrentlyDraggedObject(levelModel.getPlayer());
        }
        // if the user pressed the start button start the level
        //or if the user pressed the restart button restart the level

        if (event.getSource() == this.levelView.getStartButton()) {
            if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                if (currentState == State.STOPPED) {
                    currentState = State.STARTED;
                    this.levelView.getStartButton().setText("Restart");
                    startLevel();
                } else {
                    currentState = State.STOPPED;
                    this.levelView.getStartButton().setText("Start");
                    restartLevel();

                }
            }
        }
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
            AudioManager.getInstance().playButtonHoverSound();
        }
    }

    /**
     * Public method used to show the level to the
     * user
     */
    public void showLevel() {
        this.levelView.getCurrentLayer().setVisible(true);
        shouldNotifyObserverOnFinish = true;
        objectHandler.start();

    }

    private void startLevel() {
        playerCollisionDetector.start();
        objectHandler.stop();
        gridSystem.setGridLinesEnabled(false);
    }

    private void restartLevel() {
        getLevelModel(levelModel.getLevelNumber());
        updateModelAfterFetch();
        objectHandler.start();
        playerCollisionDetector.stop();
    }


    /**
     * This method needs to be called each time the level encountered any change in
     * the level model in order to update components depending on the level model
     */
    private void updateDependentComponents() {
        gridSystem.setLevelModel(levelModel);
        playerCollisionDetector.setLevelModel(levelModel);
        electricityHandler.setLevelModel(levelModel);
        levelView.setLevelModel(levelModel);
        gridSystem.enableGridLines();
    }

    /**
     * This method is used to hide the level from the user
     * The method is not affected by the current state of the level
     */
    public void hideLevel() {
        this.levelView.getCurrentLayer().setVisible(false);
    }


    /**
     * Method used to fetch the next level model
     */
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
        //  updateObjectsStrategies(levelModel.getObjectsOnSelectorPane());
        updateDependentComponents();

    }

    private void getLevelModel(int currentLevel) {
        if (currentLevel > numberOfLevels) {
            currentLevel = 1;
        }
        ArrayList<ObjectOnScreen> staticObjects =
                levelDataReader.getObjectsArrayFromJsonFile(currentLevel, "staticObjects");
        ArrayList<ObjectOnScreen> draggableObjects =
                levelDataReader.getObjectsArrayFromJsonFile(currentLevel, "draggableObjects");
        draggableObjects.forEach(objectOnScreen -> objectOnScreen.setHasDragEnabled(true));
        ArrayList<ObjectOnScreen> allObjects = new ArrayList<>();
        allObjects.addAll(staticObjects);
        allObjects.addAll(draggableObjects);


        levelModel = new LevelModel(
                allObjects,
                levelDataReader.getPlayerFromJsonFile(currentLevel),
                levelDataReader.getHintAfterFinish(currentLevel),
                levelDataReader.getHintBeforeStart(currentLevel),
                currentLevel
        );
    }

    /**
     * After fetching the data on each specific level, for certain objects,
     * their player reaction needs to be updates
     *
     * @param objects
     */
    private void updateObjectsStrategies(@NotNull ArrayList<ObjectOnScreen> objects) {
        objects.forEach(gameObject -> {
            if (gameObject instanceof ElectricObject) {
                ((Rectangle) gameObject).setPlayerReaction(new MovePlayerRight(levelModel.getPlayer()));
            }
        });
    }

    /**
     * Public method that tells the controller to get the next level
     */
    public void goToNextLevel() {
        getNextLevelModel();
    }

    public int getNumberOfLevels() {
        return numberOfLevels;
    }

    public LevelModel getControllerLevelModel() {
        return levelModel;
    }
}
