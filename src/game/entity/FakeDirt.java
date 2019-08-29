package game.entity;

import game.Render;
import game.Sprite;
import static game.Update.player;

public class FakeDirt extends Mob {
    
    private boolean collide;
    
    public FakeDirt(int x, int y) {
        this.x = x;
        this.y = y;
        sprite = Sprite.dirt;
    }
    
    public boolean isHit(int x, int y) {
        if (x <= this.x + 14 && x >= this.x - 14 && y >= this.y  - 16 && y <= this.y + 16) {
            collide = true;
        } else {
            collide = false;
        }
        return collide;
    }
    
    public void render(int xCamera, int yCamera) {
        if (x < xCamera + 250 && x > xCamera - 250 && y < yCamera + 150 && y > yCamera - 150) {
            boolean hit = false;
            for (int j = 0; j < player.size(); j++) {
                if (isHit(player.get(j).getXPos(), player.get(j).getYPos())) {
                    hit = true;
                    break;
                }
            }
            if (!hit) {
                Render.renderRegular(16, x - 16, y - 16, sprite);
            } else {
                //Render.renderFade(16, x - 16, y - 16, 100,0xff000000, sprite);
            }
        }
    }
    
    public void render(int xCamera, int yCamera, int xCamera2, int yCamera2, int distance) {
        boolean hit = false;
        for (int j = 0; j < player.size(); j++) {
            if (isHit(player.get(j).getXPos(), player.get(j).getYPos())) {
                hit = true;
                break;
            }
        }
        if (!hit) {
            if (x < xCamera + 250 && x > xCamera - 250 && y < yCamera + 150 && y > yCamera - 150) {
                Render.renderFirstCamera(distance < 0, 16, x - 16, y - 16, sprite);
            }
            if (x < xCamera2 + 250 && x > xCamera2 - 250 && y < yCamera2 + 150 && y > yCamera2 - 150) {
                Render.renderSecondCamera(distance > 0, 16, x - 16, y - 16, sprite);
            }
        }
    }
    
}
