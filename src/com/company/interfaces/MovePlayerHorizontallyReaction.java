package com.company.interfaces;

import com.company.Player;
import org.jetbrains.annotations.NotNull;

public class MovePlayerHorizontallyReaction implements IElectricityReaction {

    private Player player;

    public MovePlayerHorizontallyReaction(@NotNull Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.moveOnX(50);
    }
}
