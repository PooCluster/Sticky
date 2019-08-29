package game.entity.enemy;

import game.Render;
import game.Sprite;
import game.entity.Mob;

public class Boss extends Mob {
    
    //private Sprite sprite;
    
    public Boss(int x, int y) {
        this.x = x;
        this.y = y;
        sprite = Sprite.boss;
    }
    
    public void render(Render screen) {
        screen.renderRegular(64, this.x - 32, this.y - 32, sprite);
    }
    
}