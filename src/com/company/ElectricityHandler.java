package com.company;

import com.company.models.*;

public class ElectricityHandler {

    private LevelModel levelModel;


    public void setLevelModel(LevelModel levelModel) {
        this.levelModel = levelModel;
    }


    public void update() {
        if (levelModel != null) {
            resetObjectsState();

            levelModel.getObjectsOnGameScreen().forEach(objectOnScreen -> {
                if (objectOnScreen instanceof ElectricitySource && ((ElectricitySource) objectOnScreen).isElectricityEnabled()) {
                    enableElectricityOnNeighbours(objectOnScreen);
                }
                if (objectOnScreen instanceof ElectricityFuel) {
                    enableElectricitySources((ElectricityFuel) objectOnScreen);
                }

            });
        }
    }

    private void resetObjectsState() {
        levelModel.getObjectsOnGameScreen().forEach(objectOnScreen -> {
            if (objectOnScreen instanceof ElectricObject) {
                ((ElectricObject) objectOnScreen).setUnderElectricity(false);
            }
            if (objectOnScreen instanceof ElectricitySource) {
                ((ElectricitySource) objectOnScreen).setElectricityEnabled(false);
            }
        });
    }

    private void enableElectricitySources(ElectricityFuel electricityFuel) {

        levelModel.getObjectsOnGameScreen().forEach(gameObject -> {
            if (gameObject instanceof WindTurbine && electricityFuel instanceof Wind) {
                ElectricitySource electricitySource = (ElectricitySource) gameObject;
                startElectricitySource(electricityFuel, electricitySource);
            }
            if (gameObject instanceof SolarPanel && electricityFuel instanceof Sun) {
                SolarPanel solarPanel = (SolarPanel) gameObject;

                if (solarPanel.isNeighbourBottom(electricityFuel)) {
                    solarPanel.setElectricityEnabled(true);
                }


            }
        });

    }

    private void startElectricitySource(ElectricityFuel electricityFuel, ElectricitySource electricitySource) {
        if (!electricitySource.isElectricityEnabled()) {
            if (areObjectsNeighboursHorizontally(electricityFuel, electricitySource) ||
                    areObjectsNeighboursVertically(electricityFuel, electricitySource)) {
                electricitySource.setElectricityEnabled(true);
            }
        }
    }

    private void enableElectricityOnNeighbours(ObjectOnScreen object) {
        for (ObjectOnScreen loopObject : levelModel.getObjectsOnGameScreen()) {
            if (loopObject instanceof ElectricObject) {
                ElectricObject electricObject = (ElectricObject) loopObject;
                //no need to go to a node that is already traversed
                if (!electricObject.isUnderElectricity()) {
                    if (areObjectsNeighboursVertically(object, loopObject)
                            || areObjectsNeighboursHorizontally(object, loopObject)) {
                        electricObject.setUnderElectricity(true);
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
