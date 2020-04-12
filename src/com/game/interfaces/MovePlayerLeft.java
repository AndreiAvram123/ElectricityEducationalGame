package com.game.interfaces;

import com.game.models.Player;
import org.jetbrains.annotations.NotNull;

public class MovePlayerLeft implements IPlayerReaction {
    private Player player;

    public MovePlayerLeft(@NotNull Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.moveOnX(-50);
    }
}
