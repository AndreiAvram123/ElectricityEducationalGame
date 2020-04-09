package com.company;

import com.company.interfaces.HintOnHover;
import com.company.models.ObjectOnScreen;
import com.company.models.Point;
import com.company.models.Rotating;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class ObjectHandler {

    private Canvas canvas;
    private Pane root;
    private ObjectOnScreen currentlyDraggedObject;
    private ObjectOnScreen currentlyMouseOverObject;
    private GridSystem gridSystem;
    private Point lastObjectPosition;
    private HintWindow hintWindow;
    private boolean shouldDisplayHint = true;

    /**
     * The pane is necessary in order to set a keyboard listener
     *
     * @param canvas
     * @param root
     */
    public ObjectHandler(@NotNull Canvas canvas, @NotNull Pane root, @NotNull GridSystem gridSystem) {
        this.gridSystem = gridSystem;
        this.canvas = canvas;
        this.root = root;
    }


    public void setHintWindow(@NotNull HintWindow hintWindow) {
        this.hintWindow = hintWindow;
    }

    public void start() {
        attachListenersOnCanvas();
    }

    private void attachListenersOnCanvas() {
        attachKeyboardListener();

        canvas.setOnMouseMoved(event -> {
            if (shouldDisplayHint && hintWindow != null) {
                ObjectOnScreen gameObject = gridSystem.getObjectMouseOver(event.getX(), event.getY());
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

    private void attachKeyboardListener() {
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.A) {
                if (currentlyDraggedObject != null && currentlyDraggedObject instanceof Rotating) {
                    ((Rotating) currentlyDraggedObject).rotate();
                }
            }
        });
    }


}









