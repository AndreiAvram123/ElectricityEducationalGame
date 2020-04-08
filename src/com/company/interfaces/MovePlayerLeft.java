package com.company.interfaces;

import com.company.models.Player;
import org.jetbrains.annotations.NotNull;

public class MovePlayerLeft implements IElectricityReaction {
    private Player player;

    public MovePlayerLeft(@NotNull com.company.models.Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.moveOnX(-50);
    }
}
