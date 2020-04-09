package com.company;

import com.company.models.Player;
import com.company.models.Rectangle;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

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

    public LevelController(@NotNull LevelView levelView, @NotNull LevelModel levelModel) {
        this.levelView = levelView;
        this.levelModel = levelModel;
        gridSystem = new GridSystem(levelView.getGraphicsContext(), levelView.getCanvas().getWidth(),
                levelView.getCanvas().getHeight());
        objectHandler = new ObjectHandler(this.levelView.getCanvas(), gridSystem);
        objectHandler.setHintWindow(levelView.getHintWindow());
        collisionHandler = new CollisionHandler();
        playerCollisionDetector = new PlayerCollisionDetector(levelModel.getObjectsOnGameScreen(), levelModel.getPlayer());
        playerCollisionDetector.addObserver(collisionHandler);
        electricityHandler = new ElectricityHandler(levelModel.getObjectsOnGameScreen());
        animationTimer = getAnimationTimer();
    }


    @NotNull
    private AnimationTimer getAnimationTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                levelView.update();
                if (!collisionHandler.isLevelCompleted()) {
                    playerCollisionDetector.checkCollisionWithPlayer();
                    gridSystem.updateGrid();
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
            objectHandler.rotateObject();
        }
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (event.getSource() == this.levelView.getStartButton()) {
                if (this.levelView.getStartButton().getText().equals("Start")) {
                    this.levelView.getStartButton().setText("Restart");
                    electricityHandler.startElectricityHandler();
                    gridSystem.disableObjectsDrag();
                    gridSystem.setGridLinesEnabled(false);
                } else {
                    restartLevel();
                }
            }
        }


    }

    private void restartLevel() {
     levelModel.restoreInitialState();
     startLevel();
    }

    public void startLevel() {
        shouldNotifyObserverOnFinish = true;
        this.levelView.getCurrentLayer().setVisible(true);
        this.levelView.getStartButton().setOnMouseClicked(this);
        this.levelView.getCurrentLayer().setOnKeyPressed(this);
        animationTimer.start();
        objectHandler.start();
    }

    public void hideLevel() {
        this.levelView.getCurrentLayer().setVisible(false);
    }

    public void setLevelModel(LevelModel levelModel) {
        this.levelModel = levelModel;
    }
}
