package com.company.interfaces;

import com.company.Player;
import org.jetbrains.annotations.NotNull;

public class MovePlayerHorizontally implements IElectricityReaction {
    private Player player;

    public MovePlayerHorizontally(@NotNull Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.moveOnX(50);
    }
}
