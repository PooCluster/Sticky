package game.entity;

import game.Render;
import game.Sprite;
import static game.Sprite.flower;
import game.Textures;

public class Flower extends Mob {
    
    private int animation = 0;
    private final int color;
    
    public Flower(int x, int y) {
        this.x = x;
        this.y = y;
        color = random.nextInt(4);
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                flower[i][j] = new Sprite(16, 15 - i, j + 12, Textures.textures);
            }
        }
    }
    
    public void update() {
        if (animation <= 60) {
            animation++;
        } else {
            animation = 0;
        }
    }
    
    public void render(int xCamera, int yCamera) {
        if (x < xCamera + 250 && x > xCamera - 250 && y < yCamera + 150 && y > yCamera - 150) {
            animation();
            Render.renderRegular(16, x - 16, y - 16, sprite);
        }
    }
    
    public void render(int xCamera, int yCamera, int xCamera2, int yCamera2, int distance) {
        if (x < xCamera + 250 && x > xCamera - 250 && y < yCamera + 150 && y > yCamera - 150) {
            animation();
            Render.renderFirstCamera(distance < 0, 16, x - 16, y - 16, sprite);
        }
        if (x < xCamera2 + 250 && x > xCamera2 - 250 && y < yCamera2 + 150 && y > yCamera2 - 150) {
            animation();
            Render.renderSecondCamera(distance > 0, 16, x - 16, y - 16, sprite);
        }
    }
    
    private void animation() {
        for(int i = 0; i < 4; i++) {
            if (animation <= 15  + 15 * i) {
                sprite = flower[color][i];
                break;
            }
        }
    }
    
}