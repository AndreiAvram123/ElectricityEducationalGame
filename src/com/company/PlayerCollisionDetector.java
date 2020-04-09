package com.company;

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
        //we should not check collision while the player is in the moving state
        if (!player.isMoving()) {
            //we enable gravity only if the player has not collided with an
            //object at the bottom
            boolean enableGravity = true;
            for (ObjectOnScreen objectOnScreen : objectsOnScreen) {
                Sides collisionSide = getCollisionSide(objectOnScreen);
                if (collisionSide != Sides.NONE) {
                    handleCollision(objectOnScreen, collisionSide);
                }
                if (collisionSide == Sides.BOTTOM) {
                    enableGravity = false;
                }
            }
            player.setGravityEnabled(enableGravity);
        }
    }

    private void handleCollision(ObjectOnScreen collider, Sides collisionSide) {
        setChanged();
        notifyObservers(new Collision(collider, player, collisionSide));
    }

    /**
     * This method return the collision side on witch the player has collided
     * if the player is on top of the game object then the method will return  Sides.Bottom
     * if the player if left of the game object then the method will return Sides.Right
     *
     * @return
     */
    private Sides getCollisionSide(@NotNull ObjectOnScreen objectOnScreen) {
        if (player.isNeighbourTop(objectOnScreen)) {
            return Sides.BOTTOM;
        }
        if (player.isNeighbourBottom(objectOnScreen)) {
            return Sides.TOP;
        }
        if (player.isNeighbourLeft(objectOnScreen)) {
            return Sides.RIGHT;
        }
        if (player.isNeighbourRight(objectOnScreen)) {
            return Sides.LEFT;
        }
        return Sides.NONE;
    }

}
