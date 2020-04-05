package com.company;

import com.company.models.ElectricitySource;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class ElectricityHandler {

    private ArrayList<GameObject> reactiveObjectsOnScreen;
    private boolean isStarted = false;

    public ElectricityHandler(ArrayList<GameObject> reactiveObjectsOnScreen) {
        this.reactiveObjectsOnScreen = reactiveObjectsOnScreen;
    }


    public void startElectricityHandler() {
        isStarted = true;
    }

    public void update() {
        if (isStarted) {
            reactiveObjectsOnScreen.forEach(gameObject -> {
                if (gameObject instanceof ElectricitySource && ((ElectricitySource) gameObject).isElectricityEnabled()) {
                    enableElectricityOnNeighbours(gameObject);
                }
            });
        }
    }

    private void enableElectricityOnNeighbours(GameObject gameObject) {
        for (GameObject loopObject : reactiveObjectsOnScreen) {
            if (loopObject instanceof ReactiveObject) {
                ReactiveObject reactiveObject = (ReactiveObject) loopObject;
                //no need to go to a node that is already traversed
                if (!reactiveObject.isUnderElectricity()) {
                    if (areObjectsNeighboursVertically(gameObject, loopObject)) {
                        reactiveObject.setUnderElectricity(true);
                        enableElectricityOnNeighbours(loopObject);
                    }
                    if (areObjectsNeighboursHorizontally(gameObject, loopObject)) {
                        reactiveObject.setUnderElectricity(true);
                        enableElectricityOnNeighbours(loopObject);
                    }
                }
            }
        }

    }

    private boolean areObjectsNeighboursVertically(GameObject gameObject, GameObject gameObject2) {
        return ((gameObject.y + gameObject.height == gameObject2.y || gameObject2.y + gameObject2.height == gameObject.y) && gameObject.x == gameObject2.x);

    }

    private boolean areObjectsNeighboursHorizontally(GameObject gameObject, GameObject gameObject2) {
        return ((gameObject.x + gameObject.width == gameObject2.x || gameObject2.x + gameObject2.width == gameObject.x) && gameObject.y == gameObject2.y);
    }
}
