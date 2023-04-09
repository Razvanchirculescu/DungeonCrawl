package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.CasperCross;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.util.Direction;
import com.codecool.dungeoncrawl.util.GenerateRandom;

import java.util.Objects;

public class Casper extends Actor {
    Item cross;

    public Casper(Cell cell) {
        super(cell);
        this.name = "Casper";
        this.setHealth(10);
        this.setDamage(10);
        this.setCross();
    }

    private void setCross() {
        this.cross = new CasperCross(getCell().getNeighbor(1, 0), this);
    }

    @Override
    public String getTileName() {
        return "casper";
    }

    public void autoMove() {
        Direction[] directions = Direction.values();
        Direction moveDirection = directions[GenerateRandom.nextInt(0, directions.length)];
        Cell nextCell;
        switch (moveDirection) {
            case NORTH:
                nextCell = getCell().getNeighbor(+1, 0);
                break;
            case SOUTH:
                nextCell = getCell().getNeighbor(-1, 0);
                break;
            case WEST:
                nextCell = getCell().getNeighbor(0, -1);
                break;
            case EAST:
                nextCell = getCell().getNeighbor(0, +1);
                break;
            default:
                nextCell = getCell().getNeighbor(0, 0);
                break;
        }
        if (nextCell.getActor() == null && nextCell.getItem() == null && (!Objects.equals(nextCell.getTileName(), "wall"))) {
            getCell().setActor(null);
            nextCell.setActor(this);
            setCell(nextCell);
        }
    }
}