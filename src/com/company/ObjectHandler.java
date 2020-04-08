package com.company;

import com.company.interfaces.HintOnHover;
import com.company.models.GameObject;
import com.company.models.Point;
import javafx.scene.canvas.Canvas;
import org.jetbrains.annotations.NotNull;

public class ObjectHandler {

    private Canvas canvas;
    private GameObject currentlyDraggedObject;
    private GameObject currentlyMouseOverObject;
    private GridSystem gridSystem;
    private Point lastObjectPosition;
    private HintWindow hintWindow;
    private boolean shouldDisplayHint = true;

    public ObjectHandler(@NotNull Canvas canvas, @NotNull GridSystem gridSystem) {
        this.gridSystem = gridSystem;
        this.canvas = canvas;
    }

    public void setHintWindow(@NotNull HintWindow hintWindow) {
        this.hintWindow = hintWindow;
    }

    public void start() {
        attachListenersOnCanvas();
    }

    private void attachListenersOnCanvas() {
        canvas.setOnMouseMoved(event -> {
            if (shouldDisplayHint && hintWindow != null) {
                GameObject gameObject = gridSystem.getObjectMouseOver(event.getX(), event.getY());
                if (gameObject != null) {
                    if (gameObject instanceof HintOnHover) {
                        if (currentlyMouseOverObject != gameObject) {
                            currentlyMouseOverObject = gameObject;
                            hintWindow.showHint(((HintOnHover) gameObject).getHint(), gameObject.getX() + 50, gameObject.getY());
                        }
                    }
                } else {
                    hintWindow.hide();
                    currentlyMouseOverObject = null;
                }
            }
        });


        //listen to weather the mouse has been dragged or not
        canvas.setOnMouseDragged(event -> {
            shouldDisplayHint = false;
            if (hintWindow != null) {
                hintWindow.hide();
            }

            if (currentlyDraggedObject == null) {
                currentlyDraggedObject = gridSystem.getObjectMouseOver(event.getX(), event.getY());
            }

            if (currentlyDraggedObject != null) {
                if (lastObjectPosition == null) {
                    lastObjectPosition = new Point(currentlyDraggedObject.getX(), currentlyDraggedObject.getY());
                }
                currentlyDraggedObject.setNewCenter(event.getX(), event.getY());
            }
        });

        canvas.setOnMouseReleased(event -> {
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
        });
    }


}









