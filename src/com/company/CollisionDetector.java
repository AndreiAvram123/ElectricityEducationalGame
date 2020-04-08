package com.company;

import com.company.models.GameObject;
import com.company.models.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Observable;

public class CollisionDetector extends Observable {

    private final Player player;
    private ArrayList<GameObject> objectsOnScreen;

    public CollisionDetector(@NotNull ArrayList<GameObject> objectsOnScreen, @NotNull Player player) {
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

    private Sides getCollisionSide(@NotNull GameObject gameObject) {
        if (gameObject.getX() == player.getX() && gameObject.getY() == player.getY() + player.getHeight()) {
            return Sides.BOTTOM;
        }
        return Sides.NONE;
    }

}
