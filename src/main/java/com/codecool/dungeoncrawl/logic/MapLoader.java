package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.BluePotion;
import com.codecool.dungeoncrawl.logic.items.CasperCross;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;
import com.codecool.dungeoncrawl.logic.levels.YellowDoor;
import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map2.txt");
//        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
        Scanner scanner = new Scanner(is);
        int mapWidth = scanner.nextInt();
        int mapHeight = scanner.nextInt();


        int displayWidth = 25;
        int diplayHeight = 20;

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(mapWidth, mapHeight, displayWidth, diplayHeight, CellType.EMPTY);

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

}
