package game.entity.hud;

import game.Render;
import game.Sprite;
import game.entity.Mob;

public class Heart extends Mob {
    
    private final int xOffset = 180;
    private final int yOffset = 95;
    private final int ID;
    
    public Heart(int index, int ID) { //Sets heart relative to player.
        this.x = 4 + (index * 12);
        this.y = 1 + (ID * 12);
        this.ID = ID;
        sprite = Sprite.heart;
    }
    
    //Method not even relevant.
    public void update(int xPlayer, int yPlayer) { //Moves with the player.
        //this.x = xPlayer - xOffset;
        //this.y = yPlayer - yOffset + (ID * 12);
    }
    
    public void render() {
        Render.renderHUD(16, x, y, sprite);
    }
    
}
