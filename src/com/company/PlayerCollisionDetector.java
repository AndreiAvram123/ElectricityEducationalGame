package com.company;

import com.company.models.GameObject;
import com.company.models.ObjectOnScreen;
import com.company.models.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Observable;

public class PlayerCollisionDetector extends Observable {

    private final Player player;
    private ArrayList<ObjectOnScreen> objectsOnScreen;

    public PlayerCollisionDetector(@NotNull ArrayList<ObjectOnScreen> objectsOnScreen, @NotNull Player player) {
        this.objectsOnScreen = objectsOnScreen;
        this.player = player;
    }


    public void checkCollisionWithPlayer() {
        objectsOnScreen.forEach((gameObject -> {
            Sides collisionSide = getCollisionSide(gameObject);
            if (collisionSide != Sides.NONE) {
                handleCollision(gameObject, collisionSide);
            }
        }));
    }

    private void handleCollision(GameObject gameObject, Sides collisionSide) {
        setChanged();
        notifyObservers(gameObject);
    }

    /**
     * This method return the collision side on witch the player has collided
     * if the player is on top of the game object then the method will return  Sides.Bottom
     * if the player if left of the game object then the method will return Sides.Right
     *
     * @param gameObject
     * @return
     */
    private Sides getCollisionSide(@NotNull ObjectOnScreen objectOnScreen) {
        if (player.isNeighbourTop(objectOnScreen)) {
            return Sides.BOTTOM;
        }
        return Sides.NONE;
    }

}
