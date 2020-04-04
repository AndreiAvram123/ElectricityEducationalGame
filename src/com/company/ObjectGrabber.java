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
                if (gridSystem.isObjectOnSelectorPane(currentlyDraggedObject)) {
                    cachedPosition = new Point(currentlyDraggedObject.x, currentlyDraggedObject.y);
                }
                currentlyDraggedObject.setNewCenter(event.getX(), event.getY());
            }
        });

        canvas.setOnMouseReleased(event -> {
            if (currentlyDraggedObject != null) {
                if (cachedPosition != null && gridSystem.isObjectOnSelectorPane(currentlyDraggedObject)) {
                    currentlyDraggedObject.x = cachedPosition.getX();
                    currentlyDraggedObject.y = cachedPosition.getY();
                }else{
                    gridSystem.addObjectToGameScreen(currentlyDraggedObject);

                }
                gridSystem.snapOnGrid(currentlyDraggedObject);
                currentlyDraggedObject = null;
                cachedPosition = null;
            }
        });
    }


}









