package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Casper;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.util.Music;
import com.codecool.dungeoncrawl.logic.items.Sword;
import com.sun.javafx.css.StyleManager;
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

import java.util.ArrayList;
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


        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
//        StyleManager.getInstance().addUserAgentStylesheet(getClass()
//                .getResource("src/main/resources/style.css").toString());
//        StyleManager.getInstance().addUserAgentStylesheet(getResource("src/main/resources/style.css").toString());
        StyleManager.getInstance().addUserAgentStylesheet("-fx-font-family: 'serif'");


        TextInputDialog nameInputDialogBox = new TextInputDialog("Name goes here");
//        nameInputDialogBox.contentTextProperty().set("-fx-font-family: 'serif'");
        nameInputDialogBox.setTitle("NameBox");
        nameInputDialogBox.setHeaderText("Please enter your name below");
        Optional<String> result = nameInputDialogBox.showAndWait();
        result.ifPresent(name -> playerNameLabel.setText(name));

        Scene scene = new Scene(borderPane);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        primaryStage.setScene(scene);
        refresh(deltaX, deltaY);
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                if (deltaY==0) {
                    map.getPlayer().move(0, -1);
                } else if ((deltaY>= map.getMapHeight()-map.getDisplayHeight()) &&
                        (map.getPlayer().getY() >
                                (map.getMapHeight()-(map.getDisplayHeight()-1)/2))) {
                    map.getPlayer().move(0, -1);
                } else {
                    deltaY -= 1;
                    map.getPlayer().move(0, -1);
                }
                setSoundIsPlaying(footstepsSoundEffect, true);
                refresh(deltaX, deltaY);
                break;
//                map.getPlayer().move(0, -1);
//                setSoundIsPlaying(footstepsSoundEffect, true);
//                refresh();
//                break;
            case DOWN:
                if (deltaY==0 && (map.getPlayer().getY() < (map.getDisplayHeight()-1)/2)) {
                    map.getPlayer().move(0, 1);
                } else if (deltaY >= (map.getMapHeight()-map.getDisplayHeight())) {
                    map.getPlayer().move(0, 1);
                } else {
                    deltaY += 1;
                    map.getPlayer().move(0, 1);
                }
                setSoundIsPlaying(footstepsSoundEffect, true);
                refresh(deltaX, deltaY);
                break;
//                map.getPlayer().move(0, 1);
//                setSoundIsPlaying(footstepsSoundEffect, true);
//                refresh();
//                break;
            case LEFT:
                if (deltaX==0) {
                    map.getPlayer().move(-1, 0);
                } else if ((deltaX>= map.getMapWidth()-map.getDisplayWidth()) &&
                        (map.getPlayer().getX() >
                                (map.getMapWidth()-(map.getDisplayWidth()-1)/2))) {
                    map.getPlayer().move(-1, 0);
                } else {
                    deltaX -= 1;
                    map.getPlayer().move(-1, 0);
                }
                setSoundIsPlaying(footstepsSoundEffect, true);
                refresh(deltaX, deltaY);
                break;
//                map.getPlayer().move(-1, 0);
//                setSoundIsPlaying(footstepsSoundEffect, true);
//                refresh();
//                break;
            case RIGHT:
                if (deltaX==0 && (map.getPlayer().getX() < (map.getDisplayWidth()-1)/2)) {
                    map.getPlayer().move(1, 0);
                } else if (deltaX >= (map.getMapWidth()-map.getDisplayWidth())) {
                    map.getPlayer().move(1, 0);
                } else {
                    deltaX += 1;
                    map.getPlayer().move(1, 0);
                }
                setSoundIsPlaying(footstepsSoundEffect, true);
                refresh(deltaX, deltaY);
                break;
//                map.getPlayer().move(1, 0);
//                setSoundIsPlaying(footstepsSoundEffect, true);
//                refresh();
//                break;
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
            case A:
                context.setFill(Color.BLACK);
                context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                context.translate(100,0);
                refresh(deltaX, deltaY);
                break;
            case D:
                context.setFill(Color.BLACK);
                context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                context.translate(-100,0);
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
                    ((Casper) map.getCell(x+deltaX, y+deltaY).getActor()).autoMove();
                }
            }
        }
    }

    public void checkGameOver() {
        if (map.getPlayer().getHealth() <= 0) {
            Music gameOverSound = new Music("src/main/resources/mixkit-arcade-retro-game-over-213.wav");
            gameOverSound.play();
            Dialog gameOverDialog = new Dialog();
            gameOverDialog.setTitle("Bad luck!");
            gameOverDialog.setContentText("Game Over\n Restart?");
            gameOverDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            gameOverDialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
            Optional result = gameOverDialog.showAndWait();
            if (!(result.isPresent() && result.get() == ButtonType.OK)) {
                System.exit(0);
            }
            map = MapLoader.loadMap();
            refresh(deltaX, deltaY);
        }
    }

    public void checkWin() {
        ArrayList<Actor> actors = new ArrayList<>();
        for(int x=0; x<map.getMapWidth(); x++) {
            for(int y=0; y< map.getMapHeight(); y++) {
                if(!Objects.equals(map.getCell(x,y).getActor(), null)) {
                    actors.add(map.getCell(x,y).getActor());
                }
            }
        }
        if(actors.size() == 1) {
            if(actors.get(0) instanceof Player) {
                Dialog winDialog = new Dialog();
                winDialog.setTitle("Congratulation!");
                winDialog.setContentText("You won\n Restart?");
                winDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                winDialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
                Optional result = winDialog.showAndWait();
                if(!(result.isPresent() && result.get() == ButtonType.OK)){
                    System.exit(0);
                }
                map = MapLoader.loadMap();
                refresh(deltaX, deltaY);;
            }
        }
        System.out.println(actors);
    }


}
