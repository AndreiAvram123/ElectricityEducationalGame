package com.game.interfaces;

import com.game.Directions;
import com.game.models.Player;
import com.sun.istack.internal.NotNull;

public class MovePlayer implements IPlayerReaction {

    private Player player;
    private double distance;
    private Directions direction;

    public MovePlayer(@NotNull Player player, Directions direction) {
        this.player = player;
        this.direction = direction;
    }

    @Override
    public void execute() {
        switch (direction) {
            case UP:
                player.moveOnY(-distance);
                break;
            case LEFT:
                player.moveOnX(-distance);
                break;
            case RIGHT:
                player.moveOnX(distance);
                break;
        }
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

}
