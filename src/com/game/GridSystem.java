package com.game;

import com.game.models.LevelModel;
import com.game.models.ScreenObject;
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
    public ScreenObject getObjectMouseOver(double mouseX, double mouseY) {
        for (ScreenObject screenObject : levelModel.getObjectsOnGameScreen()) {
            if (screenObject.getX() < mouseX && screenObject.getX() + screenObject.getWidth() > mouseX
                    && screenObject.getY() < mouseY && screenObject.getY() + screenObject.getHeight() > mouseY) {
                return screenObject;
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
    public boolean isObjectOverAnother(@NotNull ScreenObject object) {
        for (ScreenObject screenObject : levelModel.getObjectsOnGameScreen()) {
            if (screenObject != object && screenObject.getX() == object.getX() && object.getY() == screenObject.getY()) {
                return true;
            }
        }
        return false;
    }



    /**
     * Method used in order to snap an object on the grid
     *
     * @param screenObject
     */
    public void snapOnGrid(@NotNull ScreenObject screenObject) {
        //get the center of the object
        double oldCenterX = screenObject.getX() + screenObject.getWidth() / 2;
        double oldCenterY = screenObject.getY() + screenObject.getHeight() / 2;


        double newX = ((int) (oldCenterX / 50)) * 50;
        double newY = ((int) (oldCenterY / 50)) * 50;
        screenObject.setX(newX);
        screenObject.setY(newY);
    }

}
