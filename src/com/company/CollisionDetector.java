package com.company;

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
        for (GameObject gameObject : objectsOnScreen) {
            if (hasCollidedBottom(gameObject)) {
                handleCollision(gameObject);
            }
        }
    }

    private void handleCollision(GameObject gameObject) {
        setChanged();
        notifyObservers(gameObject);
    }

    private boolean hasCollidedBottom(@NotNull GameObject gameObject) {
        return gameObject.x == player.x && gameObject.y == player.y + player.height;

    }

}
