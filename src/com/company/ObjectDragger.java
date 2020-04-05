package com.company;

import com.company.models.Point;
import javafx.scene.canvas.Canvas;
import org.jetbrains.annotations.NotNull;

public class ObjectDragger {

    private Canvas canvas;
    private GameObject currentlyDraggedObject;
    private GridSystem gridSystem;
    private Point objectPanePosition;

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
                if (gridSystem.isObjectOnSelectorPane(currentlyDraggedObject)) {
                    objectPanePosition = new Point(currentlyDraggedObject.x, currentlyDraggedObject.y);
                }
                currentlyDraggedObject.setNewCenter(event.getX(), event.getY());
            }
        });

        canvas.setOnMouseReleased(event -> {
            if (currentlyDraggedObject != null) {
                if (objectPanePosition != null && gridSystem.isObjectOnSelectorPane(currentlyDraggedObject)) {
                    currentlyDraggedObject.x = objectPanePosition.getX();
                    currentlyDraggedObject.y = objectPanePosition.getY();
                } else {
                    gridSystem.addObjectToGameScreen(currentlyDraggedObject);

                }
                gridSystem.snapOnGrid(currentlyDraggedObject);
                currentlyDraggedObject = null;
                objectPanePosition = null;
            }
        });
    }


}









