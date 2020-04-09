package com.company.interfaces;

import com.company.models.Player;
import org.jetbrains.annotations.NotNull;

public class MovePlayerRight implements IElectricityReaction {

    private Player player;

    public MovePlayerRight(@NotNull Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.moveOnX(50);
    }
}
