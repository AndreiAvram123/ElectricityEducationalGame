package com.game;

import com.game.UI.HintWindow;
import com.game.interfaces.MovePlayer;
import com.game.interfaces.NoMovingPlayerBehaviour;
import com.game.interfaces.Rotating;
import com.game.models.*;
import javafx.scene.canvas.Canvas;
import org.jetbrains.annotations.NotNull;

/**
 * Class used to handle interactions with objects on the
 * screen (for example dragging ,clicking, hovering)
 * This class is mainly used by the controller to delegate actions such
 * as handling when the user hovers over an object or when an object is dragged
 */
public class ObjectHandler {

    private final Canvas canvas;
    private final ElectricityHandler electricityHandler;
    private ScreenObject currentlyDraggedObject;
    private ScreenObject currentlyMouseOverObject;
    private final GridSystem gridSystem;
    private Point lastObjectPosition;
    private final HintWindow hintWindow;
    private boolean shouldDisplayHint = true;
    private boolean isDragStarted = false;


    public ObjectHandler(@NotNull Canvas canvas,
                         @NotNull HintWindow hintWindow,
                         @NotNull ElectricityHandler electricityHandler) {
        this.canvas = canvas;
        this.gridSystem = new GridSystem();
        this.hintWindow = hintWindow;
        this.electricityHandler = electricityHandler;
        attachListenersOnCanvas();
    }

    public void setLevelModel(@NotNull LevelModel levelModel) {
        gridSystem.setLevelModel(levelModel);
    }

    public void start() {
        isDragStarted = true;
        electricityHandler.update();
    }

    public void stop() {
        isDragStarted = false;
    }

    private void attachListenersOnCanvas() {
        addMouseMovedListener();
        addMouseDraggedListener();
        addMouseReleasedListener();
    }

    private void addMouseReleasedListener() {
        canvas.setOnMouseReleased(event -> {
            if (isDragStarted) {
                shouldDisplayHint = true;
                if (currentlyDraggedObject != null) {
                    gridSystem.snapOnGrid(currentlyDraggedObject);
                    electricityHandler.update();
                    if (gridSystem.isObjectOverAnother(currentlyDraggedObject)) {
                        //reset position and put the object back into the last
                        //known position
                        currentlyDraggedObject.setX(lastObjectPosition.getX());
                        currentlyDraggedObject.setY(lastObjectPosition.getY());
                    }
                    currentlyDraggedObject = null;
                    lastObjectPosition = null;
                }
            }
        });
    }

    private void addMouseDraggedListener() {
        //listen to weather the mouse has been dragged or not
        canvas.setOnMouseDragged(event -> {
            if (isDragStarted) {
                shouldDisplayHint = false;
                hintWindow.hide();
                if (currentlyDraggedObject == null) {
                    currentlyDraggedObject = gridSystem.getObjectMouseOver(event.getX(), event.getY());
                } else {
                    if (lastObjectPosition == null) {
                        lastObjectPosition = new Point(currentlyDraggedObject.getX(), currentlyDraggedObject.getY());
                    }
                    currentlyDraggedObject.setNewCenter(event.getX(), event.getY());

                }
            }
        });
    }

    private void addMouseMovedListener() {
        canvas.setOnMouseMoved(event -> {
            if (shouldDisplayHint) {
                ScreenObject gameObject = gridSystem.getObjectMouseOver(event.getX(), event.getY());
                if (gameObject != null) {
                    if (currentlyMouseOverObject != gameObject) {
                        currentlyMouseOverObject = gameObject;
                        hintWindow.showHint(gameObject.getHint(), gameObject.getX() + 50, gameObject.getY());
                    }
                } else {
                    hintWindow.hide();
                    currentlyMouseOverObject = null;
                }
            }
        });
    }

    /**
     * Method used to rotate an object and also to
     * call the method updateStrategy in order to update the player reaction
     * strategy of that object
     */
    public void rotateObjectCurrentlyDraggedObject(@NotNull Player player) {
        if (currentlyDraggedObject != null && currentlyDraggedObject instanceof Rotating) {
            ((Rotating) currentlyDraggedObject).rotate();
            updateStrategy(player, currentlyDraggedObject);
        }
    }

    public void updateStrategy(@NotNull Player player, @NotNull ScreenObject screenObject) {
        if (screenObject instanceof ElectricObject) {

            ElectricObject electricObject = (ElectricObject) screenObject;
            MovePlayer movePlayer;
            switch (electricObject.getPlayerCollisionSideForReaction()) {
                case LEFT:
                    movePlayer = new MovePlayer(player, Directions.RIGHT);
                    break;
                case RIGHT:
                    movePlayer = new MovePlayer(player, Directions.LEFT);
                    break;

                default:
                    movePlayer = new MovePlayer(player, Directions.UP);
                    break;
            }
            if (electricObject.getPlayerPushForce() == 0) {
                electricObject.setPlayerReaction(new NoMovingPlayerBehaviour());
            } else {
                movePlayer.setDistance(electricObject.getPlayerPushForce());
                electricObject.setPlayerReaction(movePlayer);
            }


        }

    }

}









