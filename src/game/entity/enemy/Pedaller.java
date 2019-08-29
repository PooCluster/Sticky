package game.entity.enemy;

import game.Render;
import game.Sprite;
import static game.Sprite.pedallerDown;
import static game.Sprite.pedallerDying;
import static game.Sprite.pedallerSuspend;
import static game.Sprite.pedallerUp;
import game.Textures;
import game.entity.Mob;

public class Pedaller extends Mob {
    
    private int health = 3;
    
    private int jumpingAnimation = 0;
    private int animation = 0;
    private int dyingAnimation = 0;
    private int blinkCounter = 0;
    
    //private boolean jumping = false;
    //private boolean falling = false;
    private boolean suspend = false;
    private boolean hit = false;
    private boolean dying = false;
    private boolean death = false;
    private boolean isHittable = true;
    private boolean alternate = false;
    
    private double speed = 4;
    
    private int yPermanent;
    
    public Pedaller(int x, int y) {
        this.x = x;
        this.y = y;
        yPermanent = this.y;
        for(int i = 0; i < 10; i++) {
            pedallerUp[i] = new Sprite(16, 3, 6 + i, Textures.mobs);
        }
        for(int i = 0; i < 10; i++) {
            pedallerDown[i] = new Sprite(16, 4, 6 + i, Textures.mobs);
        }
        for(int i = 0; i < 5; i++) {
            pedallerSuspend[i] = new Sprite(16, 5, 11 + i, Textures.mobs);
        }
        for(int i = 0; i < 11; i++) {
            pedallerDying[i] = new Sprite(16, 7, 4 + i, Textures.mobs);
        }
        for(int i = 0; i < 11; i++) {
            pedallerDying[11 + i] = new Sprite(16, 8, 4 + i, Textures.mobs);
        }
        //Declare stuff here. For loops.
    }
    
    public boolean isDead() {
        return death;
    }
    
    public boolean isDying() {
        return dying;
    }

    public boolean isHittable() {
        return isHittable;
    }
    
    public boolean isHit(int xBall, int yBall) {
        if((this.x >= xBall - 12 && this.x <= xBall + 12) && (this.y > yBall - 12 && this.y < yBall + 12)) {
            hit = true;
        }
        else {
            hit = false;
        }
        return hit;
    }

    public void update() {
        if(hit) {
            health--;
            hit = false;
            blinkCounter++;
            if(health < 0) {
                health = 0;
            }
        }
        if (blinkCounter != 0) {
            blinkCounter++;
            if (blinkCounter >= 120) blinkCounter = 0;
        }
        if (health == 0) {
            dying = true;
        }
        if (dying) {
            dyingAnimation++;
            if(dyingAnimation >= 66) {
                death = true;
            }
        }
        if (dying && (jumping || suspend)) {
            falling = true;
            jumping = false;
            suspend = false;
        }
        if (dying) {
            if(this.y < yPermanent) {
                this.y++;
            }
        }
        if(!jumping && !falling) {
            animation++;
        }
        if(animation >= 300 && !dying) {
            jumping = true;
            animation = 0;
        }
        if(jumping && !dying) {
            if(jumpingAnimation >= 14) {
                this.y--;
            }
            jumpingAnimation++;
            if(jumpingAnimation >= 50) {
                jumpingAnimation = 0;
                jumping = false;
                suspend = true;
            }
            isHittable = true;
        }
        if(suspend && !dying) {
            jumpingAnimation++;
            if(jumpingAnimation >= 60) {
                jumpingAnimation = 0;
                suspend = false;
                falling = true;
            }
        }
        if(falling && !jumping && !dying) {
            if(jumpingAnimation <= 35) {
                this.y++;
            }
            jumpingAnimation++;
            if(jumpingAnimation >= 50) {
                jumpingAnimation = 0;
                falling = false;
            }
        }
        if(!falling && !jumping && !suspend && !dying) {
            this.y = yPermanent;
            isHittable = false;
        }
    }
    
    public void render(int xCamera, int yCamera) {
        if(jumping && !dying) {
            up();
        } else if(suspend && !dying) {
            suspend();
        } else if(falling && !dying) {
            down();
        } else if(dying) {
            dying();
        } else {
            sprite = new Sprite(16, 0, 0, Textures.mobs);
        }
        if (blinkCounter % 4 == 0 && blinkCounter != 0 && !dying) {
            sprite = new Sprite(16, 0, 0, Textures.mobs);
        }
        if (x < xCamera + 250 && x > xCamera - 250 && y < yCamera + 150 && y > yCamera - 150) {
            Render.renderRegular(16, x - 16, y - 16, sprite);
        }
    }
    
    public void render(int xCamera, int yCamera, int xCamera2, int yCamera2, int distance) {
        if(jumping && !dying) {
            up();
        } else if(suspend && !dying) {
            suspend();
        } else if(falling && !dying) {
            down();
        } else if(dying) {
            dying();
        } else {
            sprite = new Sprite(16, 0, 0, Textures.mobs);
        }
        if (blinkCounter % 4 == 0 && blinkCounter != 0 && !dying) {
            sprite = new Sprite(16, 0, 0, Textures.mobs);
        }
        if (x < xCamera + 250 && x > xCamera - 250 && y < yCamera + 150 && y > yCamera - 150) {
            Render.renderFirstCamera(distance < 0, 16, x - 16, y - 16, sprite);
        }
        if (x < xCamera2 + 250 && x > xCamera2 - 250 && y < yCamera2 + 150 && y > yCamera2 - 150) {
            Render.renderSecondCamera(distance > 0, 16, x - 16, y - 16, sprite);
        }
    }
    
    private void up() {
        if(jumpingAnimation <= 19) {
            for(int i = 0; i < 8; i++) {
                if(jumpingAnimation <= 2 * i) {
                    sprite = pedallerUp[i];
                    break;
                }
            }
        }
        else if(!alternate && jumpingAnimation % 4 == 0) {
            sprite = pedallerUp[8];
            alternate = true;
        }
        else if(alternate && jumpingAnimation % 4 == 0) {
            sprite = pedallerUp[9];
            alternate = false;
        }
    }
    
    private void suspend() {
        for(int i = 1; i < 5; i++) {
            if(jumpingAnimation <= 12 * i) {
                sprite = pedallerSuspend[i];
                break;
            }
        }
    }
    
    private void down() {
        if(jumpingAnimation <= 28) {
            if(alternate && jumpingAnimation % 4 == 0) {
                sprite = pedallerDown[0];
            }
            else if(!alternate && jumpingAnimation % 4 == 0) {
                sprite = pedallerDown[1];
            }
        }
        else {
            for(int i = 1; i < 10; i++) {
                if(jumpingAnimation <= 30 + (2 * i)) {
                    sprite = pedallerDown[i];
                    break;
                }
            }
        }
    }
    
    private void dying() {
        for(int i = 1; i < 23; i++) {
            if(dyingAnimation <= 3 * i) {
                sprite = pedallerDying[i - 1];
                break;
            }
        }
    }
    
}