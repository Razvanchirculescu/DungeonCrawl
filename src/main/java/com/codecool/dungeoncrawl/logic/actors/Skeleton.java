package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {

        super(cell);
        this.setHealth(10);
        this.setDamage(2);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }


}
