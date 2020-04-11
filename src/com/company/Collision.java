package com.company;

/**
 * Class representing a collision between
 * the player an object on screen
 */

import com.company.models.ObjectOnScreen;
import com.company.models.Player;
import org.jetbrains.annotations.NotNull;

public class Collision {
    //the object with which the player collided
    private ObjectOnScreen collisionObject;
    private Player player;
    //the collision side of the player
    private Sides collisionSidePlayer;

    public Collision(@NotNull ObjectOnScreen collisionObject, @NotNull Player player, @NotNull Sides collisionSidePlayer) {
        this.collisionObject = collisionObject;
        this.player = player;
        this.collisionSidePlayer = collisionSidePlayer;
    }

    public ObjectOnScreen getCollisionObject() {
        return collisionObject;
    }

    public Sides getCollisionSidePlayer() {
        return collisionSidePlayer;
    }

    public Player getPlayer() {
        return player;
    }
}
