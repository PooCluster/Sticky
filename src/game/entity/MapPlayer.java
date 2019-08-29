package game.entity;

import game.Render;
import game.Sprite;
import game.Update;
import game.input.Keyboard;
import game.level.Tile;

public class MapPlayer extends Mob {
    
    private int counter;
    private int direction;
    private boolean isOnLevel;
    /*
    0 - up
    1 - down
    2 - left
    3 - right
    */
    
    public MapPlayer(int x, int y) {
        this.x = x;
        this.y = y;
        sprite = Sprite.mapPlayerRight;
    }
    
    public void update() {
        if (counter == 0) {
            if (isOnLevel = Update.map.getTile(((this.x - 16) / 16), ((this.y - 16) / 16)) == Tile.mapLevelComplete ||
                    Update.map.getTile(((this.x - 16) / 16), ((this.y - 16) / 16)) == Tile.mapLevelIncomplete) {
                Update.map.setPlayer((this.x - 16) / 16, (this.y - 16) / 16);
            }
            if (Keyboard.key(Keyboard.controls[0][0]) && !collision(0, -1)) { //P1UP
                counter++;
                y--;
                direction = 0;
            } else if (Keyboard.key(Keyboard.controls[1][0]) && !collision(0, 1)) { //P1DOWN
                counter++;
                y++;
                direction = 1;
            } else if (Keyboard.key(Keyboard.controls[2][0])) { //P1LEFT
                sprite = Sprite.mapPlayerLeft;
                if (!collision(-1, 0)) {
                    counter++;
                    x--;
                    direction = 2;
                }
            } else if (Keyboard.key(Keyboard.controls[3][0])) { //P1RIGHT
                sprite = Sprite.mapPlayerRight;
                if (!collision(1, 0)) {
                    counter++;
                    x++;
                    direction = 3;
                }
            }
        } else {
            counter++;
            if (counter >= 16) counter = 0;
            switch (direction) {
                case 0:
                    y--;
                    break;
                case 1:
                    y++;
                    break;
                case 2:
                    x--;
                    break;
                case 3:
                    x++;
                    break;
            }
        }
    }
    
    public boolean isOnLevel() {
        return isOnLevel;
    }
    
    @Override
    public boolean collision(int x, int y) {
        return (Update.map.getTile(((this.x - 16) / 16) + x, ((this.y - 16) / 16) + y).isSolid());
    }
    
    public void render() {
        Render.renderRegular(16, x - 16, y - 16, sprite);
    }
    
}
