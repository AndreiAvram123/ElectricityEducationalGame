package com.company;

import com.company.models.Point;
import javafx.scene.canvas.Canvas;
import org.jetbrains.annotations.NotNull;

public class ObjectGrabber {

    private Canvas canvas;
    private GameObject currentlyDraggedObject;
    private GridSystem gridSystem;
    private Point cachedPosition;

    public ObjectGrabber(@NotNull Canvas canvas, @NotNull GridSystem gridSystem) {
        this.gridSystem = gridSystem;
        this.canvas = canvas;
    }

    public void start() {
        attachListener();
    }

    private void attachListener() {
        //listen to weather the mouse has been dragged or not
        canvas.setOnMouseDragged(event -> {
            //reset ethe currently dragged object and check weather
            //an object is still dragged
            currentlyDraggedObject = gridSystem.getObjectSelected(event.getX(), event.getY());

            if (currentlyDraggedObject != null) {
                if (gridSystem.isObjectFromSelectorPane(currentlyDraggedObject)) {
                    cachedPosition = new Point(currentlyDraggedObject.x, currentlyDraggedObject.y);
                }
                currentlyDraggedObject.setCenter(event.getX(), event.getY());
            }
        });
        canvas.setOnMouseReleased(event -> {
            if (currentlyDraggedObject != null) {
                gridSystem.snapOnGrid(currentlyDraggedObject);
                if (cachedPosition != null && !gridSystem.isObjectFromSelectorPane(currentlyDraggedObject)) {
                    if (currentlyDraggedObject instanceof Rectangle) {
                        gridSystem.addObjectToGrid(new Rectangle(cachedPosition.getX(), cachedPosition.getY(), currentlyDraggedObject.gc));
                    }
                }
                currentlyDraggedObject = null;
                cachedPosition = null;
            }
        });
    }


}









