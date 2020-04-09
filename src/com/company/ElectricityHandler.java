package com.company;

import com.company.models.*;

import java.util.ArrayList;

public class ElectricityHandler {

    private LevelModel levelModel;
    private boolean isStarted = false;


    public void setLevelModel(LevelModel levelModel) {
        this.levelModel = levelModel;
    }

    public void startElectricityHandler() {
        isStarted = true;
    }

    public void stopElectricityHandler() {
        isStarted = false;
    }

    public void update() {
        if (isStarted && levelModel != null) {
            levelModel.getObjectsOnGameScreen().forEach(gameObject -> {
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

        levelModel.getObjectsOnGameScreen().forEach(gameObject -> {
            if (gameObject instanceof WindTurbine && electricityFuel instanceof Wind) {
                ElectricitySource electricitySource = (ElectricitySource) gameObject;

                startElectricitySource(electricityFuel, electricitySource);
            }
            if (gameObject instanceof SolarPanel && electricityFuel instanceof Sun) {
                SolarPanel solarPanel = (SolarPanel) gameObject;
                //check weather the sun is on top of the solar panel

                if (solarPanel.isNeighbourBottom(electricityFuel)) {
                    solarPanel.setElectricityEnabled(true);
                }


            }
        });

    }

    private void startElectricitySource(ElectricityFuel electricityFuel, ElectricitySource electricitySource) {
        if (!electricitySource.isElectricityEnabled()) {
            if (areObjectsNeighboursHorizontally(electricityFuel, electricitySource)) {
                electricitySource.setElectricityEnabled(true);
            }
            if (areObjectsNeighboursVertically(electricityFuel, electricitySource)) {
                electricitySource.setElectricityEnabled(true);
            }
        }
    }

    private void enableElectricityOnNeighbours(ObjectOnScreen object) {
        for (ObjectOnScreen loopObject : levelModel.getObjectsOnGameScreen()) {
            if (loopObject instanceof ReactiveObject) {
                ReactiveObject reactiveObject = (ReactiveObject) loopObject;
                //no need to go to a node that is already traversed
                if (!reactiveObject.isUnderElectricity()) {
                    if (areObjectsNeighboursVertically(object, loopObject)) {
                        reactiveObject.setUnderElectricity(true);
                        enableElectricityOnNeighbours(loopObject);
                    }
                    if (areObjectsNeighboursHorizontally(object, loopObject)) {
                        reactiveObject.setUnderElectricity(true);
                        enableElectricityOnNeighbours(loopObject);
                    }
                }
            }
        }
    }

    private boolean areObjectsNeighboursVertically(ObjectOnScreen object1, ObjectOnScreen object2) {
        return object1.isNeighbourTop(object2) || object1.isNeighbourBottom(object2);
    }


    private boolean areObjectsNeighboursHorizontally(ObjectOnScreen object1, ObjectOnScreen object2) {
        return object1.isNeighbourRight(object2) || object1.isNeighbourLeft(object2);
    }


}
