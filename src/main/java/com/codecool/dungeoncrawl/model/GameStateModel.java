package com.codecool.dungeoncrawl.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class GameStateModel extends BaseModel {
    PlayerModel playerModel;
    private Date savedAt;
    private String currentMap;
    private List<String> discoveredMaps = new ArrayList<>();
    private String savedName;

    public GameStateModel(String currentMap, String savedName, Date savedAt) {
        this.savedName = savedName;
        this.savedAt = savedAt;
        this.currentMap = currentMap;
    }

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(String currentMap) {
        this.currentMap = currentMap;
    }

    public List<String> getDiscoveredMaps() {
        return discoveredMaps;
    }

    public void addDiscoveredMap(String map) {
        this.discoveredMaps.add(map);
    }

    public String getSavedName() {
        return savedName;
    }

    public void setSavedName(String savedName) {
        this.savedName = savedName;
    }
}
