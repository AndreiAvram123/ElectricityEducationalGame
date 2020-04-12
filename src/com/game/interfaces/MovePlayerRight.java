package com.game.interfaces;

import com.game.models.Player;
import org.jetbrains.annotations.NotNull;

public class MovePlayerRight implements IPlayerReaction {

    private Player player;

    public MovePlayerRight(@NotNull Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.moveOnX(50);
    }
}
