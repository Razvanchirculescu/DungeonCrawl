package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Casper;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.codecool.dungeoncrawl.util.Music;
import com.codecool.dungeoncrawl.logic.items.Sword;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Main extends Application {

    Music gameplayMusic;
    Music inventoryPickUpSoundEffect;
    Music footstepsSoundEffect;
    Music attackSoundEffect;

    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getDisplayWidth() * Tiles.TILE_WIDTH,
            map.getDisplayHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label itemInventoryLabel = new Label();
    Label swordLabel = new Label();
    Label keysLabel = new Label();
    Label bluePotionLabel = new Label();
    Label damageLabel = new Label();
    Label playerNameLabel = new Label();
    GameDatabaseManager dbManager;


    int deltaX =0;
    int deltaY =0;




    public static void main(String[] args) {
        launch(args);
    }

    public void loadSounds() {
        gameplayMusic = new Music("src/main/resources/gameplayMusic.wav");
        inventoryPickUpSoundEffect = new Music("src/main/resources/inventory-grab-36275.wav");
        attackSoundEffect = new Music("src/main/resources/punch-boxing-02wav-14897.wav");
        footstepsSoundEffect = new Music("src/main/resources/Single-footstep-on-concrete-with-sneaker-shoes-2-www.FesliyanStudios.com.wav");
    }

    public void setSoundIsPlaying(Music music, boolean isPlaying) {
        music.stop();
        if (isPlaying)
            music.play();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
////        TESTS for serializer
        PlayerModel playerModelTest = setPlayerModel(map.getPlayer());
        Date currentSQLTime = getCurrentSQLTime();
//        String stringMap = getStringObjGson(map.getCell(7,1).getActor().getHealth());
        String stringMap = "map2.txt";
        GameState gameStateTest = setGameState(stringMap, currentSQLTime, playerModelTest);
//        System.out.println("TEST: "+stringMap);
        String testPlayer = getStringObjGson(gameStateTest);
        System.out.println("Back: "+testPlayer);
        GameState resultBack = getObjFromJSON_Gson(testPlayer);
        System.out.println("result: "+resultBack.toString());

        GridPane ui = new GridPane();
        ui.setPrefWidth(300);
        ui.setPadding(new Insets(10));
        loadSounds();
        setSoundIsPlaying(gameplayMusic, true);

        ui.add(new Label("Player: "), 0, 1);
        ui.add(new Label("Health: "), 0, 2);
        ui.add(new Label("Damage: "), 0, 3);
        ui.add(new Label("Inventory: "), 0, 4);
        ui.add(new Label("    -swords: "), 0, 5);
        ui.add(new Label("  Press S to increase damage "), 0, 6);
        ui.add(new Label("    -keys: "), 0, 7);
        ui.add(new Label("  Press K to use key"), 0, 8);
        ui.add(new Label("    -blue potions: "), 0, 9);
        ui.add(new Label("  Press B to increase health"), 0, 10);
        ui.add(playerNameLabel, 1, 1);
        ui.add(healthLabel, 1, 2);
        ui.add(damageLabel, 1, 3);
        ui.add(itemInventoryLabel, 1, 4);
        ui.add(swordLabel, 1, 5);
        ui.add(keysLabel, 1, 7);
        ui.add(bluePotionLabel, 1, 9);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Button quitButton = new Button();
        quitButton.setText("Quit Game");
        quitButton.setFocusTraversable(false);
        ui.add(quitButton, 0, 20);
        quitButton.setOnAction(actionEvent -> System.exit(0));

        Button loadButton = new Button();
        loadButton.setText("Load Saved Game");
        loadButton.setFocusTraversable(false);
        ui.add(loadButton, 0, 21);
        loadButton.setOnAction(actionEvent -> {
            List<String> savedGamesList = new ArrayList<>();
            savedGamesList.add("Save1");
            savedGamesList.add("Save2");
            savedGamesList.add("Save3");
            savedGamesList.add("Save4");
            ChoiceDialog<String> loadGameDialog = new ChoiceDialog<>("Save1", savedGamesList);
            loadGameDialog.setTitle("Load Game");
            loadGameDialog.setHeaderText("");
            loadGameDialog.setContentText("Choose a save to load");
            Optional<String> result = loadGameDialog.showAndWait();
            if(result.isPresent()){
                //load saved game
            }


        });


//        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
//        StyleManager.getInstance().addUserAgentStylesheet(getClass()
//                .getResource("src/main/resources/style.css").toString());
//        StyleManager.getInstance().addUserAgentStylesheet(getResource("src/main/resources/style.css").toString());
//        StyleManager.getInstance().addUserAgentStylesheet("-fx-font-family: 'serif'");


        TextInputDialog nameInputDialogBox = new TextInputDialog("Name goes here");
//        nameInputDialogBox.contentTextProperty().set("-fx-font-family: 'serif'");
        nameInputDialogBox.setTitle("NameBox");
        nameInputDialogBox.setHeaderText("Please enter your name below");
        Optional<String> result = nameInputDialogBox.showAndWait();
        String name = result.get();
        playerNameLabel.setText(name);

        Scene scene = new Scene(borderPane);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        primaryStage.setScene(scene);
        refresh(deltaX, deltaY);
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
//        saveInDb();
    }

    private void showSaveDialogBox(){
        TextInputDialog saveDialogBox = new TextInputDialog("Save");
//        nameInputDialogBox.contentTextProperty().set("-fx-font-family: 'serif'");
        saveDialogBox.setTitle("Save Game");
        saveDialogBox.setHeaderText("Please enter details for your save");
        Button saveButton = (Button) saveDialogBox.getDialogPane().lookupButton(ButtonType.OK);
        saveButton.setText("Save and continue");
        Optional<String> result = saveDialogBox.showAndWait();
        if (result.isPresent()){
            saveInDb();
            System.out.println("Progress saved in the database!");
        }


    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.isControlDown()&& keyEvent.getCode()==KeyCode.S){
             showSaveDialogBox();
        }
        switch (keyEvent.getCode()) {
            case UP:
                setSoundIsPlaying(footstepsSoundEffect, true);
                if (deltaY==0) {
                    map.getPlayer().move(0, -1);
                } else if ((deltaY>= map.getMapHeight()-map.getDisplayHeight()) &&
                        (map.getPlayer().getY() >
                                (map.getMapHeight()-(map.getDisplayHeight()-1)/2))) {
                    map.getPlayer().move(0, -1);
                } else {
                    if (map.getPlayer().validMove(0,-1)) {
                        deltaY -= 1;
                        map.getPlayer().move(0, -1);
                    }
                }
                refresh(deltaX, deltaY);
                break;
            case DOWN:
                setSoundIsPlaying(footstepsSoundEffect, true);
                if (deltaY==0 && (map.getPlayer().getY() < (map.getDisplayHeight()-1)/2)) {
                    map.getPlayer().move(0, 1);
                } else if (deltaY >= (map.getMapHeight()-map.getDisplayHeight())) {
                    map.getPlayer().move(0, 1);
                } else {
                    if (map.getPlayer().validMove(0,1)) {
                        deltaY += 1;
                        map.getPlayer().move(0, 1);
                    }
                }
                refresh(deltaX, deltaY);
                break;
            case LEFT:
                setSoundIsPlaying(footstepsSoundEffect, true);
                if (deltaX==0) {
                    map.getPlayer().move(-1, 0);
                } else if ((deltaX>= map.getMapWidth()-map.getDisplayWidth()) &&
                        (map.getPlayer().getX() >
                                (map.getMapWidth()-(map.getDisplayWidth()-1)/2))) {
                    map.getPlayer().move(-1, 0);
                } else {
                    if (map.getPlayer().validMove(-1,0)) {
                        deltaX -= 1;
                        map.getPlayer().move(-1, 0);
                    }
                }
                refresh(deltaX, deltaY);
                break;
            case RIGHT:
                setSoundIsPlaying(footstepsSoundEffect, true);
                if (deltaX==0 && (map.getPlayer().getX() < (map.getDisplayWidth()-1)/2)) {
                    map.getPlayer().move(1, 0);
                } else if (deltaX >= (map.getMapWidth()-map.getDisplayWidth())) {
                    map.getPlayer().move(1, 0);
                } else {
                    if (map.getPlayer().validMove(1,0)) {
                        deltaX += 1;
                        map.getPlayer().move(1, 0);
                    }
                }
                refresh(deltaX, deltaY);
                break;
            case ENTER:
                map.getPlayer().pickUpItem();
                refresh(deltaX, deltaY);
                break;
            case S:
                map.getPlayer().useItem("sword");
                refresh(deltaX, deltaY);
                break;
            case B:
                map.getPlayer().useItem("bluePotion");
                refresh(deltaX, deltaY);
                break;
            case K:
                map.getPlayer().openDoor();
                refresh(deltaX, deltaY);
                break;
        }
        checkGameOver();
        checkWin();
    }

    private void refresh(int deltaX, int deltaY) {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getDisplayWidth(); x++) {
            for (int y = 0; y < map.getDisplayHeight(); y++) {
                Cell cell = map.getCell(x+deltaX, y+deltaY);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        damageLabel.setText("" + map.getPlayer().getDamage());
        int swords = 0;
        int keys = 0;
        int bluePotion = 0;
        for (int i = 0; i < map.getPlayer().getItems().size(); i++) {
            if (map.getPlayer().getItems().get(i) instanceof Sword) {
                swords++;
            } else if (map.getPlayer().getItems().get(i) instanceof Key) {
                keys++;
            } else {
                bluePotion++;
            }
        }
        swordLabel.setText(String.valueOf(swords));
        keysLabel.setText(String.valueOf(keys));
        bluePotionLabel.setText(String.valueOf(bluePotion));
        for (int x = 0; x < map.getDisplayWidth(); x++) {
            for (int y = 0; y < map.getDisplayHeight(); y++) {
                if (map.getCell(x, y).getActor() instanceof Casper) {
                    ((Casper) map.getCell(x, y).getActor()).autoMove();
                }
            }
        }
    }

    private void saveInDb() {
        setupDbManager();
        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                if (!Objects.equals(map.getCell(x, y).getActor(), null)) {
                    dbManager.savePlayer(map.getCell(x, y).getActor());
                } else if (!Objects.equals(map.getCell(x, y).getItem(), null)) {
                    dbManager.saveItems(map.getCell(x, y).getItem());
                }
            }
        }
    }

    public void checkGameOver() {
        Dialog gameOverDialog = null;
        if (map.getPlayer().getHealth() <= 0) {
            Music gameOverSound = new Music("src/main/resources/mixkit-arcade-retro-game-over-213.wav");
            gameOverSound.play();
            gameOverDialog = new Dialog();
            gameOverDialog.setTitle("Bad luck!");
            gameOverDialog.setContentText("Game Over\n Restart?");
            gameOverSelection(gameOverDialog);
        }
    }

    private void gameOverSelection(Dialog gameOverDialog) {
        gameOverDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        gameOverDialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
        Optional result = gameOverDialog.showAndWait();
        if (!(result.isPresent() && result.get() == ButtonType.OK)) {
            System.exit(0);
        }
        map = MapLoader.loadMap();
        refresh(deltaX, deltaY);
    }

    public void checkWin() {
        ArrayList<Actor> actors = new ArrayList<>();
        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                if (!Objects.equals(map.getCell(x, y).getActor(), null)) {
                    actors.add(map.getCell(x, y).getActor());
                }
            }
        }
        if (actors.size() == 1) {
            if (actors.get(0) instanceof Player) {
                Dialog winDialog = new Dialog();
                winDialog.setTitle("Congratulation!");
                winDialog.setContentText("You won\n Restart?");
                gameOverSelection(winDialog);
            }
        }
    }

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //tests on row 82 !!!
    public PlayerModel setPlayerModel (Player player) {
        return new PlayerModel(player);
    }

    public Date getCurrentSQLTime () {
        return Date.valueOf(LocalDate.now());
    }


    public <K> String getStringObjGson (K obj) {
        return new Gson().toJson(obj);
    }

    public GameState getObjFromJSON_Gson (String mapString) {
        return new Gson().fromJson(mapString,GameState.class);
    }


    public GameState setGameState (String currentMap, Date currentDate, PlayerModel playerModel) {
        return new GameState(currentMap, currentDate, playerModel);
    }




}
