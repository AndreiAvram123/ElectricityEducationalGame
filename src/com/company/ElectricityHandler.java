package com.company;

import com.company.models.ElectricitySource;

import java.util.ArrayList;

public class ElectricityHandler {

    private ArrayList<GameObject> reactiveObjectsOnScreen;

    public ElectricityHandler(ArrayList<GameObject> reactiveObjectsOnScreen) {
        this.reactiveObjectsOnScreen = reactiveObjectsOnScreen;
    }


    public void update() {
        reactiveObjectsOnScreen.forEach((gameObject -> {
            if (gameObject instanceof ElectricitySource) {
                enableElectricityOnNeighbours(gameObject);
            }
        }));
    }

    private void enableElectricityOnNeighbours(GameObject gameObject) {
        for(GameObject loopObject : reactiveObjectsOnScreen) {
            if (loopObject instanceof ReactiveObject) {
                ReactiveObject reactiveObject = (ReactiveObject) loopObject;
                //no need to go to a node that is already traversed
                if (!reactiveObject.isUnderElectricity) {
                    if (areObjectsNeighboursVertically(gameObject, loopObject)) {
                        reactiveObject.isUnderElectricity = true;
                        enableElectricityOnNeighbours(loopObject);
                    }
                    if (areObjectsNeighboursHorizontally(gameObject, loopObject)) {
                        reactiveObject.isUnderElectricity = true;
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
