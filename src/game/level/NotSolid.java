package game.level;

import game.Sprite;
import game.Render;

public class NotSolid extends Tile {
    
    public NotSolid(Sprite sprite) {
        super(sprite);
    }
    
    @Override
    public void render(int x, int y) {
        Render.renderTile(x * 16, y * 16, this);
    }
    
    @Override
    public boolean isSolid() {
        return false;
    }
    
}