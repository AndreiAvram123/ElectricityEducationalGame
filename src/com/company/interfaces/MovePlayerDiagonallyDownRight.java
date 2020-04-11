package com.company.interfaces;

import com.company.models.Player;

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
