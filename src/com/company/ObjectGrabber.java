package com.company;

import javafx.scene.canvas.Canvas;
import org.jetbrains.annotations.NotNull;

public class ObjectGrabber {

    private Canvas canvas;
    private GameObject currentlyDraggedObject;
    private GridSystem gridSystem;


    public ObjectGrabber(@NotNull Canvas canvas, @NotNull GridSystem gridSystem) {
        this.gridSystem = gridSystem;
        this.canvas = canvas;
        attachListener();
    }

    private void attachListener() {
        //listen to weather the mouse has been dragged or not
        canvas.setOnMouseDragged(event -> {
            //reset ethe currently dragged object and check weather
            //an object is still dragged
            currentlyDraggedObject = gridSystem.getObjectSelected(event.getX(), event.getY());
            if (currentlyDraggedObject != null) {
                currentlyDraggedObject.setCenter(event.getX(), event.getY());
            }
        });
        canvas.setOnMouseReleased(event -> {
            if (currentlyDraggedObject != null) {
                gridSystem.snapOnGrid(currentlyDraggedObject);
            }
        });
    }


}









