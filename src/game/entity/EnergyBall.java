package game.entity;

import game.Render;
import game.SoundEngine;
import game.Sprite;
import static game.Sprite.attack;
import static game.Sprite.attackDeath;
import static game.Sprite.attackDeathLeft;
import static game.Sprite.attackLeft;
import game.Textures;
import static game.Update.DISTANCE_LIMIT;

public class EnergyBall extends Mob {
    
    private int animation = 0;
    private final boolean isLeft;
    private boolean hit = false;
    private boolean dead = false;
    private int distance = 0;
    private final int maxDistance = 100;
    private int dyingAnimation = 0;
    private boolean isDying = false;
    private int slowDown = 0;
    
    private int ID;
    private double dx;
    
    
    public EnergyBall(int xPlayer, int yPlayer, double dx, boolean isLeft, int ID) {
        this.ID = ID;
        if(!isLeft) {
            this.x = xPlayer + 12;
        }
        else if(isLeft) {
            this.x = xPlayer - 12;
        }
        this.y = yPlayer;
        this.dx = dx;
        this.isLeft = isLeft;
        for(int i = 0; i < 3; i++) {
            attack[i] = new Sprite(16, 1, 13 + i, Textures.mobs);
        }
        for(int i = 0; i < 3; i++) {
            attackLeft[i] = new Sprite(16, 2, 13 + i, Textures.mobs);
        }
        for(int i = 0; i < 10; i++) {
            attackDeath[i] = new Sprite (16, 1, 12 - i, Textures.mobs);
        }
        for(int i = 0; i < 10; i++) {
            attackDeathLeft[i] = new Sprite (16, 2, 12 - i, Textures.mobs);
        }
        SoundEngine.startBall1(ID);
    }
    
    public void move() {
        if(animation >= 60) {
            animation = 0;
        }
        else {
            animation++;
        }
        if(!isLeft && !hit) {
            x += 3 + dx;
            distance += 3;
        }
        else if(isLeft && !hit) {
            x -= 3 - dx;
            distance += 3;
        }
        else if(!isLeft && hit) {
            if(slowDown % 2 == 0) {
                x++;
            }
            slowDown++;
        }
        else if(isLeft && hit) {
            if(slowDown % 2 == 0) {
                x--;
            }
            slowDown++;
        }
        if(distance >= maxDistance) {
            hit = true;
        }
        
        if(hit) {
            dyingAnimation++;
            isDying = true;
            SoundEngine.startBall2(ID);
        }
        if(dyingAnimation >= 30) {
            dead = true;
        }
    }
    
    public boolean isID(int ID) {
        return this.ID == ID;
    }
    
    public boolean isDying() {
        return isDying;
    }
    
    public boolean isHit() {
        return hit;
    }
    
    public boolean isDead() {
        if (dead) SoundEngine.stopBall1(ID);
        return dead;
    }
    
    public void update(int xEnemy, int yEnemy) {
        if((this.x >= xEnemy - 12 && this.x <= xEnemy + 12) && (this.y > yEnemy - 12 && this.y < yEnemy + 12)) {
            hit = true;
        }
    }
    
    public void render(boolean playerDead, int distance) {
        if(!isLeft && dyingAnimation == 0) {
            rightAnimation();
        }
        else if(isLeft && dyingAnimation == 0) {
            leftAnimation();
        }
        else if(dyingAnimation != 0 && !isLeft) {
            dyingAnimation();
        }
        else if(dyingAnimation != 0 && isLeft) {
            dyingLeftAnimation();
        }
        if (Math.abs(distance) <= DISTANCE_LIMIT || playerDead) {
            Render.renderRegular(16, x - 16, y - 16, sprite);
        } else {
            Render.renderFirstCamera(distance < 0, 16, x - 16, y - 16, sprite);
            Render.renderSecondCamera(distance > 0, 16, x - 16, y - 16, sprite);
        }
    }
    
    private void rightAnimation() {
        if(animation <= 30) {
            for(int i = 0; i < 3; i++) {
                if(animation <= 10 + 10 * i) {
                    sprite = attack[i];
                    break;
                }
            }
        }
        else {
            for(int i = 0; i < 3; i++) {
                if(animation <= 40 + 10 * i) {
                    sprite = attack[i];
                    break;
                }
            }
        }
    }
    
    private void leftAnimation() {
        if(animation <= 30) {
            for(int i = 0; i < 3; i++) {
                if(animation <= 10 + 10 * i) {
                    sprite = attackLeft[i];
                    break;
                }
            }
        }
        else {
            for(int i = 0; i < 3; i++) {
                if(animation <= 40 + 10 * i) {
                    sprite = attackLeft[i];
                    break;
                }
            }
        }
    }
    
    private void dyingAnimation() {
        for(int i = 0; i < 10; i++) {
            if(dyingAnimation <= 3 + 3 * i) {
                sprite = attackDeath[i];
                break;
            }
        }
    }
    
    private void dyingLeftAnimation() {
        for(int i = 0; i < 10; i++) {
            if(dyingAnimation <= 3 + 3 * i) {
                sprite = attackDeathLeft[i];
                break;
            }
        }
    }
    
}
