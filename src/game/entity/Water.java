package game.entity;

import game.Render;

public class Water extends Mob {
    
    public Water(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void render(int xCamera, int yCamera, Render screen) {
        if (x < xCamera + 250 && x > xCamera - 250 && y < yCamera + 150 && y > yCamera - 150) {
            screen.renderRegular(16, x - 16, y - 16, sprite);
        }
    }
    
}
