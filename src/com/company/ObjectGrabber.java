package com.company;

import javafx.scene.canvas.Canvas;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Observable;

public class ObjectGrabber extends Observable {

    private Canvas canvas;
    private ArrayList<GameObject> objectsOnScreen;
    private GameObject currentlyDraggedObject;

    public ObjectGrabber(@NotNull Canvas canvas, @NotNull ArrayList<GameObject> objectsOnScreen) {
        this.canvas = canvas;
        this.objectsOnScreen = objectsOnScreen;
        attachListener();
    }

    private void attachListener() {
        canvas.setOnMouseDragged(event -> {
            //reinitialize the currently dragged object
            currentlyDraggedObject = null;
            objectsOnScreen.forEach(gameObject -> {
                if (isMouseOverObject(gameObject, event.getX(), event.getY())) {
                    currentlyDraggedObject = gameObject;
                    gameObject.setCenter(event.getX(), event.getY());
                }
            });
        });
        canvas.setOnMouseReleased(event -> {
            if (currentlyDraggedObject != null) {
                setChanged();
                notifyObservers(currentlyDraggedObject);
            }
            currentlyDraggedObject = null;
        });


    }

    private boolean isMouseOverObject(GameObject gameObject, double mouseX, double mouseY) {
        return gameObject.x < mouseX && gameObject.x + gameObject.width > mouseX
                && gameObject.y < mouseY && gameObject.y + gameObject.height > mouseY;
    }


}









