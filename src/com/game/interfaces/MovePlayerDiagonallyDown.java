package com.game.interfaces;

import com.game.models.Player;
import com.sun.istack.internal.NotNull;

public class MovePlayerDiagonallyDown implements IPlayerReaction {

    private Player player;

    public MovePlayerDiagonallyDown(@NotNull Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.moveOnX(50);
        player.moveOnY(50);
    }
}
