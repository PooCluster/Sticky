package game.entity;

import game.Render;
import game.Sprite;
import game.Textures;

public class Cloud extends Mob {

    private int counter = 0;
    private final int MAX_DEATH = 35;
    private int dyingCounter = 0;
    private boolean death = false;
    private final int speed;
    
    public Cloud(int x, int y) {
        this.x = x;
        this.y = y;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                Sprite.cloud[j + i * 4] = new Sprite(32, j, i + 6, Textures.textures);
            }
        }
        sprite = Sprite.cloud[0];
        speed = random.nextInt(3);
    }
    
    public void setPos(int x) {
        this.x = x;
    }
    
    public void setIdle() {
        death = false;
        sprite = Sprite.cloud[0];
    }
    
    public boolean isHit(int xBall, int yBall) {
        boolean hit = false;
        if((this.x >= xBall - 28 && this.x <= xBall + 20) && (this.y > yBall - 23 && this.y < yBall + 14)) {
            hit = true;
            dyingCounter++;
        }
        return hit;
    }
    
    public boolean isDying() {
        if (dyingCounter != 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isDead() {
        return death;
    }
    
    public void update() {
        if(speed == 0 && counter % 2 == 0) { //First speed. Fastest.
            this.x++;
        }
        if(speed == 1 && counter % 4 == 0) { //Second speed. Moderate.
            this.x++;
        }
        if(speed == 2 && counter % 8 == 0) { //Third speed. Slowest.
            this.x++;
        }
        if(counter >= 60) {
            counter = 0;
        }
        if (dyingCounter != 0) {
            dyingCounter++;
            if (dyingCounter == MAX_DEATH) {
                dyingCounter = 0;
                death = true;
            }
        }
        
        counter++;
    }
    
    public void render(int xCamera, int yCamera) {
        if (dyingCounter != 0) {
            dyingAnimation();
        }
        if (x > xCamera - 250 && x < xCamera + 250 && y > yCamera - 150 && y < yCamera + 150) {
            Render.renderRegular(32, x - 16, y - 16, sprite);
        }
    }
    
    public void render(int xCamera, int yCamera, int xCamera2, int yCamera2, int distance) {
        if (dyingCounter != 0) {
            dyingAnimation();
        }
        if (x < xCamera + 250 && x > xCamera - 250 && y < yCamera + 150 && y > yCamera - 150) {
            Render.renderFirstCamera(distance < 0, 32, x - 16, y - 16, sprite);
        }
        if (x < xCamera2 + 250 && x > xCamera2 - 250 && y < yCamera2 + 150 && y > yCamera2 - 150) {
            Render.renderSecondCamera(distance > 0, 32, x - 16, y - 16, sprite);
        }
    }
    
    private void dyingAnimation() {
        for (int i = 0; i < 7; i++) {
            if (dyingCounter <= 5 + (5 * i)) {
                sprite = Sprite.cloud[i + 1];
                break;
            }
        }
    }
    
}