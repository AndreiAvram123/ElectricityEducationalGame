package com.company.models;

import com.company.Sides;

public interface Rotating {
    void rotate();
    Sides getPlayerCollisionSideForReaction();
}
