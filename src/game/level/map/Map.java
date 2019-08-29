package game.level.map;

import game.entity.MapPlayer;
import game.Main;
import game.Render;
import game.Update;
import game.level.Tile;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Map {
    
    private int width;
    private int height;
    private int[] tiles;
    private Tile[] tile;
    
    private int playerX;
    private int playerY;
    
    public Map(String name) {
        levelX.clear();
        levelY.clear();
        try {
            BufferedImage image = ImageIO.read(Main.class.getResource(name));
            int x = width = image.getWidth();
            int y = height = image.getHeight();
            tiles = new int[x * y];
            image.getRGB(0, 0, x, y, tiles, 0, x);
            tile = new Tile[x * y];
            for (int i = 0; i < y; i++)
                for (int j = 0; j < x; j++)
                    tile[j + i * y] = loadTiles(j, i);
            findCoordinates();
            Update.mapPlayer = new MapPlayer(levelX.get(Update.currentLevel - 1) * 16 + 16, levelY.get(Update.currentLevel - 1) * 16 + 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void render(int xMove, int yMove) {
        Render.xMove = xMove;
        Render.yMove = yMove;
        int x1 = xMove / 16;
        int x2 = (xMove + Render.width + 16) / 16;
        int y1 = yMove / 16;
        int y2 = (yMove + Render.height + 16) / 16;
        for (int i = y1; i < y2; i++) {
            for (int j = x1; j < x2; j++) {
                if (j > 0 && j < width && i > 0 && i < height)
                    if (tiles[j + i * width] != 0xffdc22e6) tile[j + i * width].render(j, i);
            }
        }
    }
    
    
    //0xffff0000 first level within in this map
    //0xff0000ff every level following
    
    private final static ArrayList<Integer> levelX = new ArrayList<>();
    private final static ArrayList<Integer> levelY = new ArrayList<>();
    
    public void findCoordinates() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (tiles[j + i * width] == 0xffff0000) {
                    levelX.add(j);
                    levelY.add(i);
                    if (Update.levelsCompleted.contains(1)) tile[j + i * width] = Tile.mapLevelComplete;
                    break;
                }
            }
        }
        
        
        if (tiles[levelX.get(0) + (levelY.get(0) - 1) * width] == 0xff00a700) {
            followPath(levelX.get(0), levelY.get(0) - 1, 0, 1, Update.levelsCompleted.contains(1));
        } else if (tiles[levelX.get(0) + (levelY.get(0) + 1) * width] == 0xff00a700) {
            followPath(levelX.get(0), levelY.get(0) + 1, 1, 1, Update.levelsCompleted.contains(1));
        } else if (tiles[(levelX.get(0) - 1) + levelY.get(0) * width] == 0xff00ff00) {
            followPath(levelX.get(0) - 1, levelY.get(0), 2, 1, Update.levelsCompleted.contains(1));
        } else if (tiles[(levelX.get(0) + 1) + levelY.get(0) * width] == 0xff00ff00) {
            followPath(levelX.get(0) + 1, levelY.get(0), 3, 1, Update.levelsCompleted.contains(1));
        }
    }
    
    public void followPath(int x, int y, int direction, int level, boolean change) {
        if (tile[x + y * width] == Tile.verticalPathBlocked && change) {
            tile[x + y * width] = Tile.verticalPath;
        } else if (tile[x + y * width] == Tile.horizontalPathBlocked && change) {
            tile[x + y * width] = Tile.horizontalPath;
        } else if (tile[x + y * width] == Tile.mapLevelIncomplete && change) {
            tile[x + y * width] = Tile.mapLevelComplete;
        }
        
        for (int i = 0; i < 4; i++) { //Go through all the directions so I don't multiply the code x4!
            //i does not equal opposite, otherwise, this method would backtrack.
            //direction equals i, so it only does the if statement once, and to verify i
            if (tiles[x + (y - 1) * width] == 0xff00a700 && i != 1 && direction == i) { //Paths.
                followPath(x, y - 1, 0, level, Update.levelsCompleted.contains(level));
            } else if (tiles[x + (y + 1) * width] == 0xff00a700 && i != 0 && direction == i) {
                followPath(x, y + 1, 1, level, Update.levelsCompleted.contains(level));
            } else if (tiles[(x - 1) + y * width] == 0xff00ff00 && i != 3 && direction == i) {
                followPath(x - 1, y, 2, level, Update.levelsCompleted.contains(level));
            } else if (tiles[(x + 1) + y * width] == 0xff00ff00 && i != 2 && direction == i) {
                followPath(x + 1, y, 3, level, Update.levelsCompleted.contains(level));
            } else if (tiles[x + (y - 1) * width] == 0xff0000ff && i != 1 && direction == i) { //Levels.
                levelX.add(x);
                levelY.add(y - 1);
                followPath(x, y - 1, 0, level + 1, Update.levelsCompleted.contains(level + 1));
            } else if (tiles[x + (y + 1) * width] == 0xff0000ff && i != 0 && direction == i) {
                levelX.add(x);
                levelY.add(y + 1);
                followPath(x, y + 1, 1, level + 1, Update.levelsCompleted.contains(level + 1));
            } else if (tiles[(x - 1) + y * width] == 0xff0000ff && i != 3 && direction == i) {
                levelX.add(x - 1);
                levelY.add(y);
                followPath(x - 1, y, 2, level + 1, Update.levelsCompleted.contains(level + 1));
            } else if (tiles[(x + 1) + y * width] == 0xff0000ff && i != 2 && direction == i) {
                levelX.add(x + 1);
                levelY.add(y);
                followPath(x + 1, y, 3, level + 1, Update.levelsCompleted.contains(level + 1));
            }
        }
    }
    
    private Tile loadTiles(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y > height) return Tile.fill;
        if (tiles[x + y * width] == 0xff000000) return Tile.mapGround;
        if (tiles[x + y * width] == 0xff00ff00) return Tile.horizontalPathBlocked;
        if (tiles[x + y * width] == 0xff00a700) return Tile.verticalPathBlocked;
        if (tiles[x + y * width] == 0xffff0000) return Tile.mapLevelIncomplete;
        if (tiles[x + y * width] == 0xff0000ff) return Tile.mapLevelIncomplete;
        return Tile.fill;
    }
    
    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y > height) return Tile.fill;
        return tile[x + y * width];
    }
 
    
    public void setPlayer(int x, int y) {
        playerX = x;
        playerY = y;
    }
    
    public int whichLevel() {
        int level = 1;
        
        for (int i = 0; i < levelX.size(); i++) if (levelX.get(i) == playerX && levelY.get(i) == playerY) level = i + 1;
        return level;
    }
    
}