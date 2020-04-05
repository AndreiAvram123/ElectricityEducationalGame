package com.company;

import com.company.models.Point;
import javafx.scene.canvas.Canvas;
import org.jetbrains.annotations.NotNull;

public class ObjectDragger {

    private Canvas canvas;
    private GameObject currentlyDraggedObject;
    private GridSystem gridSystem;
    private Point lastObjectPosition;

    public ObjectDragger(@NotNull Canvas canvas, @NotNull GridSystem gridSystem) {
        this.gridSystem = gridSystem;
        this.canvas = canvas;
    }

    public void start() {
        attachListener();
    }

    private void attachListener() {
        //listen to weather the mouse has been dragged or not
        canvas.setOnMouseDragged(event -> {
            if (currentlyDraggedObject == null) {
                currentlyDraggedObject = gridSystem.getObjectSelected(event.getX(), event.getY());
            }

            if (currentlyDraggedObject != null) {
                // if (gridSystem.isObjectOnSelectorPane(currentlyDraggedObject)) {
                if (lastObjectPosition == null) {
                    lastObjectPosition = new Point(currentlyDraggedObject.x, currentlyDraggedObject.y);
                }
                //}
                currentlyDraggedObject.setNewCenter(event.getX(), event.getY());
            }
        });

        canvas.setOnMouseReleased(event -> {
            if (currentlyDraggedObject != null) {
                gridSystem.snapOnGrid(currentlyDraggedObject);

                if (gridSystem.isObjectOverAnother(currentlyDraggedObject)) {
                    currentlyDraggedObject.x = lastObjectPosition.getX();
                    currentlyDraggedObject.y = lastObjectPosition.getY();
                } else {
                    gridSystem.addObjectToGameScreen(currentlyDraggedObject);

                }
                currentlyDraggedObject = null;
                lastObjectPosition = null;
            }
        });
    }


}









