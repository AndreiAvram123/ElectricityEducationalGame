package com.company;

import com.company.models.*;

import java.util.ArrayList;

public class ElectricityHandler {

    private ArrayList<GameObject> objectsOnGameScreen;
    private boolean isStarted = false;

    public ElectricityHandler(ArrayList<GameObject> objectsOnGameScreen) {
        this.objectsOnGameScreen = objectsOnGameScreen;
    }


    public void startElectricityHandler() {
        isStarted = true;
    }

    public void update() {
        if (isStarted) {
            objectsOnGameScreen.forEach(gameObject -> {
                if (gameObject instanceof ElectricitySource && ((ElectricitySource) gameObject).isElectricityEnabled()) {
                    enableElectricityOnNeighbours(gameObject);
                }
                if (gameObject instanceof ElectricityFuel) {
                    enableElectricitySources((ElectricityFuel) gameObject);
                }

            });
        }
    }

    private void enableElectricitySources(ElectricityFuel electricityFuel) {
        for (GameObject gameObject : objectsOnGameScreen) {

            if (gameObject instanceof WindTurbine && electricityFuel instanceof Wind) {
                ElectricitySource electricitySource = (ElectricitySource) gameObject;
                startElectricitySource(electricityFuel, electricitySource);
            }
            if (gameObject instanceof SolarPanel && electricityFuel instanceof Sun) {
                ElectricitySource electricitySource = (ElectricitySource) gameObject;
                startElectricitySource(electricityFuel, electricitySource);
            }

        }
    }

    private void startElectricitySource(ElectricityFuel electricityFuel, ElectricitySource electricitySource) {
        if (!electricitySource.isElectricityEnabled()) {
            if (areObjectsNeighboursHorizontally(electricityFuel, electricitySource)) {
                electricitySource.setElectricityEnabled(true);
            }
        }
    }


    private void enableElectricityOnNeighbours(GameObject gameObject) {
        for (GameObject loopObject : objectsOnGameScreen) {
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
        return ((gameObject.getY() + gameObject.getHeight() == gameObject2.getY() || gameObject2.getY() + gameObject2.getHeight() == gameObject.getY()) && gameObject.getX() == gameObject2.getX());

    }

    private boolean areObjectsNeighboursHorizontally(GameObject gameObject, GameObject gameObject2) {
        return ((gameObject.getX() + gameObject.getWidth() == gameObject2.getX() || gameObject2.getX() + gameObject2.getWidth() == gameObject.getX()) && gameObject.getY() == gameObject2.getY());
    }
}
