package game.level;

import game.Main;
import game.Render;
import static game.Render.xMove;
import static game.Render.xMove2;
import game.Sprite;
import game.Textures;
import static game.Update.DISTANCE_LIMIT;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Level {
    
    private int width;
    private int height;
    private int[] initialTile;
    private Tile[] tile;
    
    public Level(String name) {
        try {
            BufferedImage image = ImageIO.read(Main.class.getResource(name));
            width = image.getWidth();
            height = image.getHeight();
            initialTile = new int[width * height];
            image.getRGB(0, 0, width, height, initialTile, 0, width);
            tile = new Tile[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    tile[j + i * width] = getTile(j, i);
                }
            }
        } catch(IOException e) {
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
        //Render.xMove = xMove;
        //Render.yMove = yMove;
        int x1 = (xMove) / 16;
        int x2 = (xMove + Render.width + 16) / 16;
        int y1 = (yMove) / 16;
        int y2 = (yMove + Render.height + 16) / 16;
        for (int i = y1; i < y2; i++)
            for (int j = x1; j < x2; j++)
                if (j > 0 && j < width && i > 0 && i < height)
                    if (tile[j + i * width] != Tile.fill) tile[j + i * width].render(j, i);
    }
    
    public void render(int xMove, int yMove, int xMove2, int yMove2, int distance) {
        
        int x1 = (xMove2) / 16;
        int x2 = (xMove2 + Render.width + 16) / 16;
        int y1 = (yMove2) / 16;
        int y2 = (yMove2 + Render.height + 16) / 16;

        int x3 = (xMove) / 16;
        int x4 = (xMove + Render.width + 16) / 16;
        int y3 = (yMove) / 16;
        int y4 = (yMove + Render.height + 16) / 16;
        if (distance > DISTANCE_LIMIT) { //Player 1 on the right.
            for (int i = y1; i < y2; i++)
                for (int j = x1; j < x2; j++)
                    if (j > 0 && j < width && i > 0 && i < height)
                        if (tile[j + i * width] != Tile.fill) tile[j + i * width].renderLeft(distance, j, i);
            
            for (int i = y3; i < y4; i++)
                for (int j = x3; j < x4; j++)
                    if (j > 0 && j < width && i > 0 && i < height)
                        if (tile[j + i * width] != Tile.fill) tile[j + i * width].renderRight(distance, j, i);
           
            
            
        } else { //Player 1 on the left.
            for (int i = y1; i < y2; i++) //Player 1
                for (int j = x1; j < x2; j++)
                    if (j > 0 && j < width && i > 0 && i < height)
                        if (tile[j + i * width] != Tile.fill) tile[j + i * width].renderRight(distance, j, i);
            
            for (int i = y3; i < y4; i++) //Player 2
                for (int j = x3; j < x4; j++)
                    if (j > 0 && j < width && i > 0 && i < height)
                        if (tile[j + i * width] != Tile.fill) tile[j + i * width].renderLeft(distance, j, i);
            
            
        }
    }
    
    //Grass = ff00ff00
    //Dirt = ffff0000
    //GrassDirt = ffffff00
    //GrassDirtLeft = ff00cd00
    //GrassDirtRight = ff00b200
    //GrassDirtLeftCorner = ff00a700
    //GrassDirtRightCorner = ff008f00
    //GrassDirtRightWall = ff4a3d1a
    //GrassDirtLeftWall = ffa58400
    //GrassDirtCeiling = ff878700
    //GrassDirtCeilingRight = ff875D71
    //GrassDirtCeilingLeft = ff829B4B
    //GrassDirtCeilingLeftCorner = ff82824B
    //GrassDirtCeilingRightCorner = ff878771
    //GrassDirtBackground = ff630000
    //Stone = ff404040
    //StoneBackground = 0xff000000
    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)  return Tile.fill;
        if (initialTile[x + y * width] == 0xff00ff00) return Tile.grass;
        if (initialTile[x + y * width] == 0xffff0000) return Tile.dirt;
        if (initialTile[x + y * width] == 0xffffff00) return Tile.grassdirt;
        if (initialTile[x + y * width] == 0xff00cd00) return Tile.grassdirtLeft;
        if (initialTile[x + y * width] == 0xff00b200) return Tile.grassdirtRight;
        if (initialTile[x + y * width] == 0xff00a700) return Tile.grassdirtLeftCorner;
        if (initialTile[x + y * width] == 0xff008f00) return Tile.grassdirtRightCorner;
        if (initialTile[x + y * width] == 0xff4a3d1a) return Tile.grassdirtRightWall;
        if (initialTile[x + y * width] == 0xffa58400) return Tile.grassdirtLeftWall;
        if (initialTile[x + y * width] == 0xff878700) return Tile.grassdirtCeiling;
        if (initialTile[x + y * width] == 0xff829B4B) return Tile.grassdirtCeilingLeft;
        if (initialTile[x + y * width] == 0xff875D71) return Tile.grassdirtCeilingRight;
        if (initialTile[x + y * width] == 0xff82824B) return Tile.grassdirtCeilingLeftCorner;
        if (initialTile[x + y * width] == 0xff878771) return Tile.grassdirtCeilingRightCorner;
        if (initialTile[x + y * width] == 0xff630000) return Tile.grassdirtBackground;
        if (initialTile[x + y * width] == 0xff404040) {
            Random random = new Random();
            int a = random.nextInt(4);
            for (int i = 0; i < 4; i++) {
                Sprite.stone[i] = new Sprite(16, 1 + i, 2, Textures.textures);
            }
            Tile.stone = new Solid(Sprite.stone[a]);
            return Tile.stone;
        }
        if (initialTile[x + y * width] == 0xff000000) {
            Random random = new Random();
            int a = random.nextInt(4);
            for (int i = 0; i < 4; i++) {
                Sprite.stoneBackground[i] = new Sprite(16, 5 + i, 2, Textures.textures);
            }
            Tile.stoneBackground = new NotSolid(Sprite.stoneBackground[a]);
            return Tile.stoneBackground;
        }
        return Tile.fill;
    }

}