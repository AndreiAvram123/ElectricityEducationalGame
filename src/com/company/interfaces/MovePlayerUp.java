package com.company.interfaces;

import com.company.models.Player;
import org.jetbrains.annotations.NotNull;

public class MovePlayerUp implements IElectricityReaction {

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