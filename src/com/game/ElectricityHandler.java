package com.game;

import com.game.models.*;

/**
 * Class used to enable or disable electricity on elements on the screen
 * Call the update() method each frame to update
 * elements
 */
public class ElectricityHandler {

    private LevelModel levelModel;

    public void setLevelModel( LevelModel levelModel) {
        this.levelModel = levelModel;
    }


    /**
     * Method that needs to be called each each time an object is moved on the screen
     * It updates the electricity state of each object on the
     * screen
     * There is a dependency between objects : An energy factor objects in combination with
     * an electricity source can produce electricity. All the neighbours of an electricity source that is
     * currently producing electricity are powered. Moreover all the neighbours of those neighbours are powered
     * as well
     */
    public void update() {

        if (levelModel != null) {
            //each time we call update we need to reset the state of the objects
            resetObjectsState();
            //process the objects on screen
            levelModel.getObjectsOnGameScreen().forEach(objectOnScreen -> {
                //start producing energy for electricity sources around an energy factor object
                if (objectOnScreen instanceof EnergyFactor) {
                    enableElectricitySources((EnergyFactor) objectOnScreen);
                }
            });
            levelModel.getObjectsOnGameScreen().forEach(objectOnScreen -> {
                //for all objects around an electricity source object enable electricity
                if (objectOnScreen instanceof ElectricitySource && ((ElectricitySource) objectOnScreen).isElectricityEnabled()) {
                    enableElectricityOnNeighbours(objectOnScreen);
                }

            });
        }
    }

    /**
     * Method used to reset the  state of objects on the screen
     */
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

    private void enableElectricitySources( EnergyFactor energyFactor) {

        levelModel.getObjectsOnGameScreen().forEach(objectOnScreen -> {

            if (objectOnScreen instanceof ElectricitySource) {
                ElectricitySource electricitySource = (ElectricitySource) objectOnScreen;
                if (electricitySource.isPoweredBy(energyFactor) && energyFactor.isPositionValidToGiveEnergy(electricitySource)) {
                    electricitySource.setElectricityEnabled(true);
                }
            }
        });

    }


    private void enableElectricityOnNeighbours(ScreenObject object) {
        for (ScreenObject loopObject : levelModel.getObjectsOnGameScreen()) {
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

    private boolean areObjectsNeighboursVertically(ScreenObject object1, ScreenObject object2) {
        return object1.isNeighbourTopWith(object2) || object1.isNeighbourBottomWith(object2);
    }

    private boolean areObjectsNeighboursHorizontally(ScreenObject object1, ScreenObject object2) {
        return object1.isNeighbourRightWith(object2) || object1.isNeighbourLeftWith(object2);
    }

}
