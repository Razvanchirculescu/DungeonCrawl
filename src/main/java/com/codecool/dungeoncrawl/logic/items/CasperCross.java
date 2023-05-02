package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Casper;

public class CasperCross extends Item {

    private Casper assignedCasper;

    public CasperCross(Cell cell) {
        super(cell);
    }

    public CasperCross(Cell cell, Casper assignedCasper) {
        super(cell);
        this.assignedCasper = assignedCasper;
    }

    @Override
    public void killCasper() {
        this.assignedCasper.getCell().setActor(null);
    }


    @Override
    public String getTileName() {
        return "casperCross";
    }
}
