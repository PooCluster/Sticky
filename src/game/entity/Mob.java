package game.entity;

import game.Sprite;
import game.level.Level;
import java.util.Random;

public class Mob {

    protected boolean falling = false;
    protected boolean jumping = false;
    protected boolean collision = true;
    
    protected int x;
    protected int y;
    
    protected Level level;
    protected final Random random = new Random();
    
    protected Sprite sprite;
    
    protected int ID;

    public Mob() {
        initialize(game.Update.level);
    }
    
    public void initialize(Level level) {
        this.level = level;
    }
    
    protected void move(int x, int y) {
        //if (collision) {
            if (!collision(x, 0)) {
                this.x += x;
            }
            if (y < 0) {
                if (!collision(0, y)) {
                    this.y += y;
                    falling = true;
                }
            } else if (!collision(0, y)) {
                this.y += y;
                falling = true;
            } else {
                falling = false;
            }
        //}
    }
    
    public int getXPos() {
        return this.x;
    }
    
    public int getYPos() {
        return this.y;
    }
    
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    protected boolean collision(int x, int y) {
        for(int i = 0; i < 4; i++) { //There are four sides.
           int x1 = ((this.x + x) + i % 2 * 10 - 13) / 16;
            int y1 = ((this.y + y) + i / 2 * 16 - 17) / 16;
            if(level.getTile(x1, y1).isSolid()) {
                return true;
            }
        }
        return false;
    }
    
}