package com.game;

import com.game.models.LevelModel;
import com.game.models.ObjectOnScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The grid system that appears on the screen
 * The main role is to position the elements in theirs corresponding positions
 */
public class GridSystem {

    private LevelModel levelModel;

    public void setLevelModel(@NotNull LevelModel levelModel) {
        this.levelModel = levelModel;
    }


    /**
     * Method used to return (if exists ) the currently object that has the
     * mouse over
     *
     * @param mouseX - the mouse x position
     * @param mouseY - the mouse y position
     */
    @Nullable
    public ObjectOnScreen getObjectMouseOver(double mouseX, double mouseY) {
        for (ObjectOnScreen objectOnScreen : levelModel.getObjectsOnGameScreen()) {
            if (objectOnScreen.getX() < mouseX && objectOnScreen.getX() + objectOnScreen.getWidth() > mouseX
                    && objectOnScreen.getY() < mouseY && objectOnScreen.getY() + objectOnScreen.getHeight() > mouseY) {
                return objectOnScreen;
            }
        }
        return null;
    }

    /**
     * Determine if an object is on top of another
     * Useful to determine whether the user wants to place an
     * object on top of another
     *
     * @param object
     * @return
     */
    public boolean isObjectOverAnother(@NotNull ObjectOnScreen object) {
        for (ObjectOnScreen objectOnScreen : levelModel.getObjectsOnGameScreen()) {
            if (objectOnScreen != object && objectOnScreen.getX() == object.getX() && object.getY() == objectOnScreen.getY()) {
                return true;
            }
        }
        return false;
    }



    /**
     * Method used in order to snap an object on the grid
     *
     * @param objectOnScreen
     */
    public void snapOnGrid(@NotNull ObjectOnScreen objectOnScreen) {
        //get the center of the object
        double oldCenterX = objectOnScreen.getX() + objectOnScreen.getWidth() / 2;
        double oldCenterY = objectOnScreen.getY() + objectOnScreen.getHeight() / 2;


        double newX = ((int) (oldCenterX / 50)) * 50;
        double newY = ((int) (oldCenterY / 50)) * 50;
        objectOnScreen.setX(newX);
        objectOnScreen.setY(newY);
    }

}
