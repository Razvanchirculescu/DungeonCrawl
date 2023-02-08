package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ActorModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface PlayerDao {
    void add(ActorModel player);
    void update(ActorModel player);
    PlayerModel get(int id);
    List<PlayerModel> getAll();
}
