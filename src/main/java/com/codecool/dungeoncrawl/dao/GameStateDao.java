package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;

public interface GameStateDao {
    void add(GameState state);
    void update(GameState state, String saveName);
    GameState get(int id);
}
