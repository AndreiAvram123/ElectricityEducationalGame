package com.company;

import com.company.interfaces.HintOnHover;
import com.company.interfaces.MovePlayerLeft;
import com.company.interfaces.MovePlayerRight;
import com.company.interfaces.MovePlayerUp;
import com.company.models.*;
import javafx.scene.canvas.Canvas;
import org.jetbrains.annotations.NotNull;

public class ObjectHandler {

    private Canvas canvas;
    private ObjectOnScreen currentlyDraggedObject;
    private ObjectOnScreen currentlyMouseOverObject;
    private GridSystem gridSystem;
    private Point lastObjectPosition;
    private HintWindow hintWindow;
    private boolean shouldDisplayHint = true;
    private boolean isDragStarted = false;

    public ObjectHandler(@NotNull Canvas canvas, @NotNull GridSystem gridSystem, @NotNull HintWindow hintWindow) {
        this.canvas = canvas;
        this.gridSystem = gridSystem;
        this.hintWindow = hintWindow;
        attachListenersOnCanvas();
    }


    public void start() {
        isDragStarted = true;
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
            }});


        canvas.setOnMouseReleased(event -> {
            if (isDragStarted) {
                shouldDisplayHint = true;
                if (currentlyDraggedObject != null) {
                    gridSystem.snapOnGrid(currentlyDraggedObject);

                    if (gridSystem.isObjectOverAnother(currentlyDraggedObject)) {
                        //reset position and put the object back into the last
                        //known position
                        currentlyDraggedObject.setX(lastObjectPosition.getX());
                        currentlyDraggedObject.setY(lastObjectPosition.getY());
                    } else {
                        gridSystem.addObjectToGameScreen(currentlyDraggedObject);
                    }
                    currentlyDraggedObject = null;
                    lastObjectPosition = null;
                }
            }
        });
    }

    public void rotateObject(@NotNull Player player) {
        if (currentlyDraggedObject != null && currentlyDraggedObject instanceof Rotating) {
            ((Rotating) currentlyDraggedObject).rotate();
            updateStrategy(player, currentlyDraggedObject);
        }
    }

    private void updateStrategy(@NotNull Player player, @NotNull ObjectOnScreen objectOnScreen) {
        if (objectOnScreen instanceof ReactiveObject) {
            ReactiveObject reactiveObject = (ReactiveObject) objectOnScreen;
            switch (reactiveObject.getPlayerCollisionSideForReaction()) {
                case LEFT:
                    reactiveObject.setElectricityReaction(new MovePlayerRight(player));
                    break;
                case RIGHT:
                    reactiveObject.setElectricityReaction(new MovePlayerLeft(player));
                    break;

                case BOTTOM:
                    reactiveObject.setElectricityReaction(new MovePlayerUp(player));
                    break;
            }
        }

    }


}









