package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Actor;

public class ActorModel extends BaseModel {

    private String actorName;
    private int hp;
    private int x;
    private int y;
    private int dm;


    public ActorModel(String actorName, int x, int y, int hp, int dm) {
        this.actorName = actorName;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.dm = dm;
    }

    public ActorModel(Actor actor) {
        this.actorName = actor.getTileName();
        this.x = actor.getX();
        this.y = actor.getY();
        this.hp = actor.getHealth();
        this.dm = actor.getDamage();

    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDm() {
        return dm;
    }
}
