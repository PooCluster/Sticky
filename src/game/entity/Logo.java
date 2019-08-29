package game.entity;

import game.Render;
import game.Sprite;
import static game.Sprite.logo;
import game.Textures;

public class Logo extends Mob {

    public Logo(int x, int y) {
        this.x = x;
        this.y = y;
        for (int i = 0; i < logo.length; i++) logo[i] = new Sprite(32, i, 5, Textures.textures);
    }
    
    public void render() {
        for (int i = 0; i < logo.length; i++) Render.renderHUD(32, x + (i * 32), y, logo[i]);
    }
    
}
