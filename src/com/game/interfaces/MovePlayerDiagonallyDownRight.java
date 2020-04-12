package com.game.interfaces;

import com.game.models.Player;

public class MovePlayerDiagonallyDownRight implements IPlayerReaction {

    private Player player;

    public MovePlayerDiagonallyDownRight(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.moveOnX(50);
        player.moveOnY(50);
    }
}
