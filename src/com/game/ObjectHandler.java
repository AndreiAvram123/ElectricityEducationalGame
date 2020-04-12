package com.game;

import com.game.UI.HintWindow;
import com.game.interfaces.HintOnHover;
import com.game.interfaces.MovePlayer;
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
    private ObjectOnScreen currentlyDraggedObject;
    private ObjectOnScreen currentlyMouseOverObject;
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
        canvas.setOnMouseMoved(event -> {
            if (shouldDisplayHint) {
                ObjectOnScreen gameObject = gridSystem.getObjectMouseOver(event.getX(), event.getY());
                if (gameObject instanceof HintOnHover) {
                    if (currentlyMouseOverObject != gameObject) {
                        currentlyMouseOverObject = gameObject;
                        hintWindow.showHint(((HintOnHover) gameObject).getHint(), gameObject.getX() + 50, gameObject.getY());
                    }
                } else {
                    hintWindow.hide();
                    currentlyMouseOverObject = null;
                }
            }
        });


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

    public void updateStrategy(@NotNull Player player, @NotNull ObjectOnScreen objectOnScreen) {
        if (objectOnScreen instanceof ElectricObject) {

            ElectricObject electricObject = (ElectricObject) objectOnScreen;
            MovePlayer movePlayer;
            switch (electricObject.getPlayerCollisionSideForReaction()) {
                case LEFT:
                    movePlayer = new MovePlayer(player, Directions.RIGHT);
                    ;
                    break;
                case RIGHT:
                    movePlayer = new MovePlayer(player, Directions.LEFT);

                    break;

                default:
                    BOTTOM:
                    movePlayer = new MovePlayer(player, Directions.UP);
                    break;
            }
            if (objectOnScreen instanceof Fan) {
                movePlayer.setCustomDistance(400);
            }
            electricObject.setPlayerReaction(movePlayer);

        }

    }

}









