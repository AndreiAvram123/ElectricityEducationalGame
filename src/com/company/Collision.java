package com.company;

import com.company.models.ObjectOnScreen;
import com.company.models.Player;

public class Collision {
    private ObjectOnScreen objectCollided;
    private Player player;
    private Sides collisionSidePlayer;

    public Collision(ObjectOnScreen objectCollided, Player player, Sides collisionSidePlayer) {
        this.objectCollided = objectCollided;
        this.player = player;
        this.collisionSidePlayer = collisionSidePlayer;
    }

    public ObjectOnScreen getObjectCollided() {
        return objectCollided;
    }

    public Sides getCollisionSidePlayer() {
        return collisionSidePlayer;
    }

    public Player getPlayer() {
        return player;
    }
}
