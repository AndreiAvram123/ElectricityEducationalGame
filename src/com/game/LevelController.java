package com.game;

import com.game.UI.LevelView;
import com.game.collision.PlayerCollisionDetector;
import com.game.interfaces.MovePlayer;
import com.game.interfaces.MovePlayerDiagonallyDown;
import com.game.interfaces.NoMovingPlayerBehaviour;
import com.game.models.ElectricObject;
import com.game.models.LevelModel;
import com.game.models.ScreenObject;
import com.game.models.Slope;
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

    private LevelView levelView;
    private LevelModel levelModel;
    private ObjectHandler objectHandler;
    private ElectricityHandler electricityHandler;
    private PlayerCollisionDetector playerCollisionDetector;
    private boolean shouldNotifyObserverOnFinish = true;
    private final int numberOfLevels;
    private final LevelDataReader levelDataReader;
    private State currentState = State.STOPPED;
    private AnimationTimer animationTimer;

    public LevelController(@NotNull LevelView levelView) {
        this.levelView = levelView;
        levelDataReader = new LevelDataReader(new GameObjectsFactory(levelView.getGraphicsContext()));
        playerCollisionDetector = new PlayerCollisionDetector();
        electricityHandler = new ElectricityHandler();
        objectHandler = new ObjectHandler(this.levelView.getCanvas(), levelView.getHintWindow(), electricityHandler);
        this.numberOfLevels = levelDataReader.getNumberOfLevels();
        animationTimer = getAnimationTimer();
        getNextLevelModel();
        initializeLevel();
        attachEvents();
    }

    private void initializeLevel() {
        animationTimer.start();
        playerCollisionDetector.stop();
        objectHandler.start();
        levelView.getStartButton().setText("Start");
        currentState = State.STOPPED;
    }

    /**
     * Enum class used to represent the state of the level
     */
    private enum State {
        STARTED, STOPPED;
    }

    private void attachEvents() {
        this.levelView.getStartButton().setOnMouseClicked(this);
        this.levelView.getLayout().setOnKeyPressed(this);
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
                } else {
                    if (shouldNotifyObserverOnFinish) {
                        shouldNotifyObserverOnFinish = false;
                        //set changed method needs to be
                        //called before notifying
                        //any observe
                        initializeLevel();
                        setChanged();
                        notifyObservers(levelModel.getLevelNumber());
                    }
                }
                levelView.updateView();
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
    public void showLevelView() {
        this.levelView.getLayout().setVisible(true);
        shouldNotifyObserverOnFinish = true;
        objectHandler.start();

    }

    private void startLevel() {
        playerCollisionDetector.start();
        objectHandler.stop();
        levelView.setGridLinesEnabled(false);
    }

    private void restartLevel() {
        getLevelModel(levelModel.getLevelNumber());
        updateModelAfterFetch();
        initializeLevel();

    }


    /**
     * This method needs to be called each time the level encountered any change in
     * the level model in order to update components depending on the level model
     */
    private void updateDependentComponents() {
        objectHandler.setLevelModel(levelModel);
        playerCollisionDetector.setLevelModel(levelModel);
        electricityHandler.setLevelModel(levelModel);
        levelView.setLevelModel(levelModel);
        levelView.setGridLinesEnabled(true);
    }

    /**
     * This method is used to hide the level from the user
     * The method is not affected by the current state of the level
     */
    public void hideLevelView() {
        this.levelView.getLayout().setVisible(false);
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
        updateDependentComponents();

    }

    private void getLevelModel(int currentLevel) {
        if (currentLevel > numberOfLevels) {
            currentLevel = 1;
        }
        levelModel = levelDataReader.getLevelModel(currentLevel);

    }

    /**
     * After fetching the data on each specific level, for certain objects,
     * their player reaction needs to be updates
     *
     * @param objects
     */
    private void updateObjectsStrategies(@NotNull ArrayList<ScreenObject> objects) {
        objects.forEach(object -> {
            if (object instanceof ElectricObject) {
                ElectricObject electricObject = (ElectricObject) object;
                if (electricObject.getPlayerPushForce() == 0) {
                    electricObject.setPlayerReaction(new NoMovingPlayerBehaviour());
                } else {
                    MovePlayer movePlayer = new MovePlayer(levelModel.getPlayer(), Directions.RIGHT);
                    movePlayer.setDistance(electricObject.getPlayerPushForce());
                    electricObject.setPlayerReaction(movePlayer);

                }
            }else{
                if(object instanceof Slope){
                    ((Slope) object).setPlayerReaction(new MovePlayerDiagonallyDown(levelModel.getPlayer()));
                }
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
