package com.game.interfaces;

import com.game.models.Player;
import org.jetbrains.annotations.NotNull;

public class MovePlayerUp implements IPlayerReaction {

    private Player player;
    private double distance = 100;


    public MovePlayerUp(@NotNull Player player) {
        this.player = player;
    }


    @Override
    public void execute() {
        player.moveOnY(-distance);
    }
}
