package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.levels.YellowDoor;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Actor implements Drawable {
    private final ArrayList<Item> items = new ArrayList<>();
    private Cell cell;
    private int damage;
    private int health = 10;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        if (!((cell.getX() + dx) < 0 || (cell.getX() + dx) > (cell.getGameMap().getMapWidth() - 1) ||
                (cell.getY() + dy) < 0 || (cell.getY() + dy) > (cell.getGameMap().getMapHeight() - 1))) {
            Cell nextCell = cell.getNeighbor(dx, dy);
            if (!Objects.equals(nextCell.getTileName(), "wall") &&
                    !Objects.equals(nextCell.getActor().getTileName(), "skeleton")) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
        if (!((cell.getX() + dx) < 0 || (cell.getX() + dx) > (cell.getGameMap().getMapWidth() - 1) ||
                (cell.getY() + dy) < 0 || (cell.getY() + dy) > (cell.getGameMap().getMapHeight() - 1))) {
            Cell nextCell = cell.getNeighbor(dx, dy);
            if (!Objects.equals(nextCell.getTileName(), "wall") && ((nextCell.getActor() == null) ||
                    !Objects.equals(nextCell.getActor().getTileName(), "skeleton"))) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
    }

    public boolean validMove(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if(nextCell.getActor() == null) {
            return !(nextCell.getItem() instanceof YellowDoor) && !nextCell.getType().getTileName().equals("wall");
        } return false;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    protected void setItemsNull() {
        this.getItems().clear();
    }
}
