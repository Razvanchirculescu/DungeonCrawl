package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.GameMap;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import java.util.Objects;
import java.util.Optional;

public class DataSaver {

    public void saveData(GameDatabaseManager dbManager, GameMap map) {
        TextInputDialog saveDialogBox = new TextInputDialog("Save");
//        nameInputDialogBox.contentTextProperty().set("-fx-font-family: 'serif'");
        saveDialogBox.setTitle("Save Game");
        saveDialogBox.setHeaderText("Please enter details for your save");
        Button saveButton = (Button) saveDialogBox.getDialogPane().lookupButton(ButtonType.OK);
        saveButton.setText("Save and continue");
        Optional<String> result = saveDialogBox.showAndWait();
        if (result.isPresent()) {
            String name = result.get();
//            if (dbManager.getAllNames().contains(name)) {
//                int gameStateId = dbManager.getGameStateId(name);
//                for (int x = 0; x < map.getMapWidth(); x++) {
//                    for (int y = 0; y < map.getMapHeight(); y++) {
//                        if (!Objects.equals(map.getCell(x, y).getActor(), null)) {
//                            dbManager.updateGameStateDetails(map.getCell(x, y).getActor(), gameStateId);
//                        }
////                        } else if (!Objects.equals(map.getCell(x, y).getItem(), null)) {
////                            dbManager.saveItems(map.getCell(x, y).getItem(), gameStateId);
////                        }
//                    }
//                }
//            } else {
            dbManager.saveGameState("/emptymap2.txt", name);
            int gameStateId = dbManager.getGameStateId(name);
            for (int x = 0; x < map.getMapWidth(); x++) {
                for (int y = 0; y < map.getMapHeight(); y++) {
                    if (!Objects.equals(map.getCell(x, y).getActor(), null)) {
                        dbManager.savePlayer(map.getCell(x, y).getActor(), gameStateId);
                    } else if (!Objects.equals(map.getCell(x, y).getItem(), null)) {
                        dbManager.saveItems(map.getCell(x, y).getItem(), gameStateId);
                    }
                }
            }
        }
//        }
//        System.out.println("Progress saved in the database!");
//    }
    }
}
