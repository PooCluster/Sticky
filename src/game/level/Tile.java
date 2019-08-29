package game.level;

import game.Render;
import game.Sprite;

public class Tile { 
    
    public int x;
    public int y;
    public Sprite sprite;
    
    //Overworld Tiles.
    public static Tile grass = new Solid(Sprite.grass);
    public static Tile dirt = new Solid(Sprite.dirt);
    public static Tile grassdirt = new Solid(Sprite.grassdirt);
    public static Tile grassdirtLeft = new Solid(Sprite.grassdirtLeft);
    public static Tile grassdirtRight = new Solid(Sprite.grassdirtRight);
    public static Tile grassdirtLeftCorner = new Solid(Sprite.grassdirtLeftCorner);
    public static Tile grassdirtRightCorner = new Solid(Sprite.grassdirtRightCorner);
    public static Tile grassdirtRightWall = new Solid(Sprite.grassdirtRightWall);
    public static Tile grassdirtLeftWall = new Solid(Sprite.grassdirtLeftWall);
    public static Tile grassdirtCeiling = new Solid(Sprite.grassdirtCeiling);
    public static Tile grassdirtCeilingLeft = new Solid(Sprite.grassdirtCeilingLeft);
    public static Tile grassdirtCeilingRight = new Solid(Sprite.grassdirtCeilingRight);
    public static Tile grassdirtCeilingLeftCorner = new Solid(Sprite.grassdirtCeilingLeftCorner);
    public static Tile grassdirtCeilingRightCorner = new Solid(Sprite.grassdirtCeilingRightCorner);
    public static Tile grassdirtBackground = new NotSolid(Sprite.grassdirtBackground);
    
    //Underworld Tiles.
    public static Tile stone = new Solid(Sprite.stone1);
    public static Tile stoneBackground = new NotSolid(Sprite.stoneBackground1);
        
    //Tiles
    public static Tile mapGround = new Solid(Sprite.mapGround);
    public static Tile horizontalPathBlocked = new Solid(Sprite.horizontalPathBlocked);
    public static Tile verticalPathBlocked = new Solid(Sprite.verticalPathBlocked);
    public static Tile horizontalPath = new NotSolid(Sprite.horizontalPath);
    public static Tile verticalPath = new NotSolid(Sprite.verticalPath);
    public static Tile mapLevelIncomplete = new NotSolid(Sprite.mapLevelIncomplete);
    public static Tile mapLevelComplete = new NotSolid(Sprite.mapLevelComplete);
    
    
    //Last resort.
    public static Tile fill = new NotSolid(Sprite.fill);
    
    public Tile(Sprite sprite) {
        this.sprite = sprite;
    }
    
    public void render(int x, int y) {
        
    }
    
    public void renderLeft(int distance, int x, int y) {
        Render.renderTileLeft(distance, x * 16, y * 16, this);
    }
    
    public void renderRight(int distance, int x, int y) {
        Render.renderTileRight(distance, x * 16, y * 16, this);
    }
    
    public boolean isSolid() {
        return false;
    }
    
}