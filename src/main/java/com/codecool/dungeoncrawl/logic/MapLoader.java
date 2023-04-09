package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.levels.YellowDoor;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        String path = "/map2.txt";
//        String path = "/map.txt";
        InputStream is = MapLoader.class.getResourceAsStream(path);
        if (is == null) {
            System.out.println("No valid path to file!");
            throw new RuntimeException("No valid path to file!");
        }
        Scanner scanner = new Scanner(is);
        int mapWidth = scanner.nextInt();
        int mapHeight = scanner.nextInt();


        int displayWidth = 25;
        int displayHeight = 20;

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(mapWidth, mapHeight, displayWidth, displayHeight, CellType.EMPTY);

        for (int y = 0; y < mapHeight; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < mapWidth; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case 'm':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            new Casper(cell);
                            break;
                        case 'c':
                            cell.setType(CellType.FLOOR);
                            new CreepyBug(cell);
                            break;
                        case 'y':
                            cell.setType(CellType.FLOOR);
                            new YellowDoor(cell);
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            new BluePotion(cell);
                            break;
                        case 'h':
                            cell.setType(CellType.FLOOR);
                            new CasperCross(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

    public static GameMap loadBlankMap(String mapPath) {
        InputStream is = MapLoader.class.getResourceAsStream(mapPath);
        if (is == null) {
            System.out.println("No valid path to file!");
            throw new RuntimeException("No valid path to file!");
        }
        Scanner scanner = new Scanner(is);
        int mapWidth = scanner.nextInt();
        int mapHeight = scanner.nextInt();

        int displayWidth = 25;
        int displayHeight = 20;

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(mapWidth, mapHeight, displayWidth, displayHeight, CellType.EMPTY);

        for (int y = 0; y < mapHeight; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < mapWidth; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

    public static GameMap populateBlankMap(GameMap blankMap, List<Actor> actors, List<Item> items) {
        for (Actor actor: actors) {
            Cell cell = blankMap.getCell(actor.getX(), actor.getY());
            cell.setType(CellType.FLOOR);
            cell.setActor(actor);
            if(actor.getTileName().equals("player")) {
                blankMap.setPlayer((Player) actor);
            }
        }

        for (Item item: items) {
            Cell cell = blankMap.getCell(item.getX(), item.getY());
            cell.setType(CellType.FLOOR);
            cell.setItem(item);
  
        }
        return blankMap;
    }

}
