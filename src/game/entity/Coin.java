package game.entity;

import game.Render;
import game.Sprite;
import static game.Sprite.coin;
import game.Textures;

public class Coin extends Mob {
    
    private int timer = 0;
    private boolean isDead = false;
    
    public Coin(int x, int y) {
        this.x = x;
        this.y = y;
        for (int i = 0; i < 4; i++) {
            coin[i] = new Sprite(16, 0, 11 + i, Textures.mobs);
        }
    }
    
    public boolean isDead() {
        return isDead;
    }
    
    public void update() {
        timer++;
        if (timer >= 20) {
            timer = 0;
        }
    }
    
    public boolean isHit(int xPlayer, int yPlayer) {
        if ((this.x >= xPlayer - 10 && this.x <= xPlayer + 10) && (this.y > yPlayer - 12 && this.y < yPlayer + 12)) {
            isDead = true;
        }
        return isDead;
    }
    
    public void render(int xCamera, int yCamera) {
        for (int i = 0; i < 4; i++) {
            if (timer <= 5 + 5 * i) {
                sprite = coin[i];
                break;
            }
        }
        if (x < xCamera + 250 && x > xCamera - 250 && y < yCamera + 150 && y > yCamera - 150) {
            Render.renderRegular(16, this.x - 16, this.y - 16, sprite);
        }
    }
    
    public void render(int xCamera, int yCamera, int xCamera2, int yCamera2, int distance) {
        for (int i = 0; i < 4; i++) {
            if (timer <= 5 + 5 * i) {
                sprite = coin[i];
                break;
            }
        }
        if (x < xCamera + 250 && x > xCamera - 250 && y < yCamera + 150 && y > yCamera - 150) {
            Render.renderFirstCamera(distance < 0, 16, x - 16, y - 16, sprite);
        }
        if (x < xCamera2 + 250 && x > xCamera2 - 250 && y < yCamera2 + 150 && y > yCamera2 - 150) {
            Render.renderSecondCamera(distance > 0, 16, x - 16, y - 16, sprite);
        }
    }
    
}