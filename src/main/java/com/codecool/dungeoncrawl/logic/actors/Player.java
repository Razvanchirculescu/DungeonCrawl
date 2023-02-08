package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.items.BluePotion;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;
import com.codecool.dungeoncrawl.logic.levels.OpenDoor;
import com.codecool.dungeoncrawl.util.Music;
import com.codecool.dungeoncrawl.logic.Cell;

import java.util.Objects;

public class Player extends Actor {

    Music attackSoundEffect;
    Music inventoryPickUpItem;
    Music openDoor;
    String name;
    String playerName = "name";

    public Player(Cell cell) {

        super(cell);
        this.setHealth(66);
        this.setDamage(5);
    }

    public String getTileName() {
        return "player";
    }


    @Override
    public void move(int dx, int dy) {
        if (!checkNeighbour(dx, dy)) {
            Cell nextCell = getCell().getNeighbor(dx, dy);
            if (validMove(dx, dy)) {
                getCell().setActor(null);
                nextCell.setActor(this);
                setCell(nextCell);
            }
        }
    }

    private boolean checkNeighbour(int dx, int dy) {
        Actor enemy = getCell().getNeighbor(dx, dy).getActor();
        if ((enemy instanceof Skeleton) || (enemy instanceof CreepyBug)) {
            ((Player) getCell().getActor()).attackEnemy(enemy);
            attackSoundEffect = new Music("src/main/resources/sword-hit-7160.wav");
            attackSoundEffect.play();
            return true;
        }
        return false;
    }

    public void attackEnemy(Actor Enemy) {
        Enemy.setHealth(Enemy.getHealth() - getDamage());
        if (Enemy.getHealth() > 0) {
            this.setHealth(this.getHealth() - Enemy.getDamage());
        } else {
            if (Objects.equals(Enemy.getTileName(), "casper")) {
                for (Item cross : Enemy.getItems()) {
                    this.getCell().setActor(null);
                    cross.getCell().setActor(null);
                    this.setCell(cross.getCell());
                }
            } else {
                this.getCell().setActor(null);
                Enemy.getCell().setActor(this);
                this.setCell(Enemy.getCell());
            }
        }
    }

    public void pickUpItem() {
        if (this.getCell().getItem() != null) {
            if (Objects.equals(this.getCell().getItem().getTileName(), "casperCross")) {
                Music destroyGrave = new Music("src/main/resources/rock-destroy-6409.wav");
                destroyGrave.play();
                this.getCell().getItem().killCasper();
                this.getCell().setItem(null);
            } else if (this.getCell().getItem().getTileName().equals("sword") ||
                    this.getCell().getItem().getTileName().equals("key") ||
                    this.getCell().getItem().getTileName().equals("bluePotion"))  {
                this.getItems().add(this.getCell().getItem());
                inventoryPickUpItem = new Music("src/main/resources/mixkit-magic-sweep-game-trophy-257.wav");
                inventoryPickUpItem.play();
                this.getCell().setItem(null);
            }
        }
    }

    public void useItem(String itemType) {
        int swordsCount = 0;
        int potionCount = 0;
        Item object = null;
        int objectCount = 0;
        for (Item item : getItems()) {
            if (itemType.equals("sword")) {
                if (item.getTileName().equals("sword")) {
                    swordsCount++;
                    object = item;
                    objectCount = swordsCount;

                }
            } else if (itemType.equals("bluePotion")) {
                if (item.getTileName().equals("bluePotion")) {
                    potionCount++;
                    object = item;
                    objectCount = potionCount;
                }
            }
        }
        if (objectCount > 0) {
            getItems().remove(object);
            if (object instanceof BluePotion) {
                setHealth(getHealth() + 5);
                Music usePotionSound = new Music("src/main/resources/splash.wav");
                usePotionSound.play();
            } else if (object instanceof Sword) {
                setDamage(getDamage() + 1);
                Music damageUpgradeSound = new Music("src/main/resources/epicAmulet.wav");
                damageUpgradeSound.play();
            }
        }
    }

    public void openDoor() {
        Cell doorCell = getCell().getNeighbor(1, 0);
        int keyCount = 0;
        Key key = null;
        for (Item item : getItems()) {
            if (item instanceof Key) {
                keyCount++;
                key = (Key) item;
            }
        }
        if (!(Objects.equals(getCell().getTileName(), null)) && Objects.equals(doorCell.getItem().getTileName(), "yellowDoor")) {
            if (keyCount > 0) {
                openDoor = new Music("src/main/resources/Closing-Squeaky-Door-4-www.fesliyanstudios.com.wav");
                openDoor.play();
                doorCell.setItem(new OpenDoor(doorCell));
                getItems().remove(key);
            }
        }
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
}