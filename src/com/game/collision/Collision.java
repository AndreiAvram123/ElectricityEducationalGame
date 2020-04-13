package com.game.collision;

/**
 * Class representing a collision between
 * the player an object on screen
 */

import com.game.models.Player;
import com.game.models.ScreenObject;
import com.game.models.Sides;
import org.jetbrains.annotations.NotNull;

public class Collision {
    //the object with which the player collided
    private ScreenObject collisionObject;
    private Player player;
    //the collision side of the player
    private Sides collisionSidePlayer;

    public Collision(@NotNull ScreenObject collisionObject, @NotNull Player player, @NotNull Sides collisionSidePlayer) {
        this.collisionObject = collisionObject;
        this.player = player;
        this.collisionSidePlayer = collisionSidePlayer;
    }

    public ScreenObject getCollisionObject() {
        return collisionObject;
    }

    public Sides getCollisionSidePlayer() {
        return collisionSidePlayer;
    }

    public Player getPlayer() {
        return player;
    }
}
