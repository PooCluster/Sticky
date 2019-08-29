package game.entity;

import game.input.Keyboard;
import game.Render;
import game.Sprite;
import static game.Sprite.playerCrouch;
import static game.Sprite.playerDown;
import static game.Sprite.playerDownLeft;
import static game.Sprite.playerDying;
import static game.Sprite.playerDyingLeft;
import static game.Sprite.playerIdle;
import static game.Sprite.playerIdleLeft;
import static game.Sprite.playerLeft;
import static game.Sprite.playerRight;
import static game.Sprite.playerUp;
import static game.Sprite.playerUpLeft;
import game.Textures;
import static game.Update.DISTANCE_LIMIT;

public class Player extends Mob {
    
    private final int ID;
    
    //Counters.
    private int jumpingAnimation = 0;
    private int fallingAnimation = 0;
    private int animation = 0;
    private int crouchingAnimation = 0;
    private int counter = 0;
    private int invincibilityCounter = 0;
    private int invincibilityAnimationCounter = 0;
    private int dyingCounter = 0;
    
    private final double jumpSpeed = 4.5;
    private double currentJumpSpeed = jumpSpeed;
    private final double fallSpeed = 5;
    private double currentFallSpeed = 3;
    
    private final int maxHealth = 4;
    private int currentHealth = maxHealth;
    
    private boolean canJump = false;
    private boolean isLeft = false;
    private boolean dying = false;
    private boolean leftLast = false;
    private boolean changedDirection = false;
    
    public Player(int x, int y, int ID) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        if (ID == 1) {
            animation = 30;
        }
        for(int i = 0; i < 12; i++) {
            playerIdle[i] = new Sprite(16, 15, i, Textures.mobs);
        }
        for(int i = 0; i < 12; i++) {
            playerIdleLeft[i] = new Sprite(16, 14, i, Textures.mobs);
        }
        for(int i = 0; i < 5; i++) {
            playerRight[i] = new Sprite(16, 13, i, Textures.mobs);
        }
        for(int i = 0; i < 5; i++) {
            playerLeft[i] = new Sprite(16, 12, i, Textures.mobs);
        }
        for(int i = 0; i < 5; i++) {
            playerUp[i] = new Sprite(16, 11, i, Textures.mobs);
        }
        for(int i = 0; i < 4; i++) {
            playerDown[i] = new Sprite(16, 9, i, Textures.mobs);
        }
        for(int i = 0; i < 5; i++) {
            playerUpLeft[i] = new Sprite(16, 10, i, Textures.mobs);
        }
        for(int i = 0; i < 4; i++) {
            playerDownLeft[i] = new Sprite(16, 8, i, Textures.mobs);
        }
        for(int i = 0; i < 4; i++) {
            playerCrouch[i] = new Sprite(16, 7, i, Textures.mobs);
        }
        for(int i = 0; i < 11; i++) {
            playerDying[i] = new Sprite(16, 6, i, Textures.mobs);
        }
        for(int i = 0; i < 11; i++) {
            playerDyingLeft[i] = new Sprite(16, 5, i, Textures.mobs);
        }
        sprite = playerIdle[0];
    }
    
    @Override
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
        //dyingCounter = 0;
        //dying = false;
    }
    
    public boolean isLeft() {
        return isLeft;
    }
    
    public void setDirection(boolean a) {
        isLeft = a;
    }
    
    public int getHealth() {
        return currentHealth;
    }
    
    public void setHealth(int a) {
        currentHealth = a;
        if (a != 0) {
            dyingCounter = 0;
            dying = false;
        } else {
            dyingCounter = 120;
            dying = true;
            
        }
    }
    
    public double getVelocity() {
        return dx;
    }
    
    public boolean isDead() {
        return dyingCounter >= 120;
    }
    
    public boolean isFalling() {
        return falling;
    }
    
    public boolean isJumping() {
        return jumping;
    }
    
    public boolean canJump() {
        return canJump;
    }
    
    public double jumpSpeed() {
        return currentJumpSpeed;
    }
    
    public double fallSpeed() {
        return currentFallSpeed;
    }
    
    public int jumpAnimation() {
        return jumpingAnimation;
    }
    
    public int fallAnimation() {
        return fallingAnimation;
    }
    
    public void setState(boolean canJump, boolean jumping, boolean falling, double jumpSpeed, double fallSpeed, int jumpingAnimation, int fallingAnimation) {
        this.canJump = canJump;
        this.falling = falling;
        this.jumping = jumping;
        currentJumpSpeed = jumpSpeed;
        currentFallSpeed = fallSpeed;
        this.jumpingAnimation = jumpingAnimation;
        this.fallingAnimation = fallingAnimation;
        
    }
    
    public void setCollision(boolean collision) {
        this.collision = collision;
    }
    
    public void setIdle() {
        canJump = true;
        jumping = false;
        falling = false;
        jumpingAnimation = 0;
        fallingAnimation = 0;
        currentJumpSpeed = jumpSpeed;
        currentFallSpeed = 3;
    }
    
    public boolean isHit(int xEnemy, int yEnemy) {
        if((this.x >= xEnemy - 12 && this.x <= xEnemy + 12) && (this.y > yEnemy - 12 && this.y < yEnemy + 12) && invincibilityCounter == 0 && !dying) {
            currentHealth--;
            invincibilityCounter = 2; //2 Seconds.
            return true;
        }
        return false;
    }
    
    private int xMove = 0;
    private double dx = 0.0;
    
    public int xMove() {
        return xMove;
    }
    
    public void setVelocity(double dx) {
        this.dx = dx;
    }
    
    public void update() {
        int x = 0;
        int y = 0;
        if (currentHealth == 0) {
            dying = true;
            if (dyingCounter < 120) dyingCounter++;
        } else {
            dying = false;
            dyingCounter = 0;
        }
        if (animation <= 60) {
            animation++;
        } else {
            animation = 0;
        }
        if (invincibilityCounter != 0) {
            invincibilityAnimationCounter++;
        } else {
            invincibilityAnimationCounter = 0;
        }
        if (invincibilityAnimationCounter == 60) {
            invincibilityAnimationCounter = 0;
            invincibilityCounter--;
        }
        if (Keyboard.key(Keyboard.controls[0][ID]) && canJump && !dying) {
            jumping = true;
            canJump = false;
        }
        if (jumping && !dying) {
            currentFallSpeed = 0.1;
            fallingAnimation = 0;
            y -= currentJumpSpeed;
            currentJumpSpeed -= 0.1;
            jumpingAnimation++;
            if (currentJumpSpeed <= 3) {
                currentJumpSpeed = jumpSpeed;
                jumping = false;
                falling = true;
            }
        }
        if (falling && !jumping) {
            fallingAnimation++;
            y += currentFallSpeed;
            if (currentFallSpeed < fallSpeed) {
                currentFallSpeed += 0.1;
            }
        }
        if (!falling && !jumping) {
            currentFallSpeed = 0.1;
            jumpingAnimation = 0;
            fallingAnimation = 0;
            canJump = true;
            y++;
        }
        if (dying && jumping) {
            falling = true;
            jumping = false;
        }
        if (!Keyboard.key(Keyboard.controls[0][ID]) && jumping) {
            jumping = false;
            falling = true;
            jumpingAnimation = 0;
            currentFallSpeed = 0.1;
            currentJumpSpeed = jumpSpeed;
            currentFallSpeed = 0.5;
        }
        //TEST CODE.
        if (Keyboard.key(Keyboard.controls[2][ID]) && !Keyboard.key(Keyboard.controls[3][ID]) && Keyboard.key(Keyboard.controls[5][ID]) && !dying) {
            if (dx > -3.0 && (collision(0, y) || collision)) dx -= 0.1;
        } else if (Keyboard.key(Keyboard.controls[3][ID]) && !Keyboard.key(Keyboard.controls[2][ID]) && Keyboard.key(Keyboard.controls[5][ID]) && !dying) {
            if (dx < 3.0 && (collision(0, y) || collision)) dx += 0.1;
        }
        if (Keyboard.key(Keyboard.controls[2][ID]) && !Keyboard.key(Keyboard.controls[5][ID]) && !Keyboard.key(Keyboard.controls[1][ID]) && !dying) {
            if (dx > -1.1) {
                //dx -= 0.1;
                dx = -1.1;
            } else if (dx < -1.1) {
                dx += 0.1;
            }
        }
        if (Keyboard.key(Keyboard.controls[3][ID]) && !Keyboard.key(Keyboard.controls[5][ID]) && !Keyboard.key(Keyboard.controls[1][ID]) && !dying) {
            if (dx < 1.1) {
                //dx += 0.1;
                dx = 1.1;
            } else if (dx > 1.1) {
                dx -= 0.1;
            }
        }
        if (Keyboard.key(Keyboard.controls[2][ID]) && Keyboard.key(Keyboard.controls[5][ID]) && !Keyboard.key(Keyboard.controls[1][ID]) && !dying);// x -= 2;
        if (Keyboard.key(Keyboard.controls[3][ID]) && Keyboard.key(Keyboard.controls[5][ID]) && !Keyboard.key(Keyboard.controls[1][ID]) && !dying);// x += 2;
        if (Keyboard.key(Keyboard.controls[2][ID]) && Keyboard.key(Keyboard.controls[1][ID]) && !Keyboard.key(Keyboard.controls[5][ID]) && !dying) {
            if (counter % 2 == 0) {
                dx = -1.1;
                counter++;
            } else {
                dx = 0.0;
                counter = 0;
            }
        }
        if (Keyboard.key(Keyboard.controls[3][ID]) && Keyboard.key(Keyboard.controls[1][ID]) && !Keyboard.key(Keyboard.controls[5][ID]) && !dying) {
            if (counter % 2 == 0) {
                dx = 1.1;
                counter++;
            } else {
                dx = 0.0;
                counter = 0;
            }
        }
        if ((!Keyboard.key(Keyboard.controls[2][ID]) && !Keyboard.key(Keyboard.controls[3][ID])) || dying) {
            if (dx > 0.0) {
                dx -= 0.1;
            } else if (dx < 0.0) {
                dx += 0.1;
            }
        }
        if (Keyboard.key(Keyboard.controls[2][ID]) && Keyboard.key(Keyboard.controls[3][ID]) && !dying) {
            if (dx > 0.0) {
                dx -= 0.2;
            } else if (dx < 0.0) {
                dx += 0.2;
            }
        }
        if (collision((int)dx, 0) && !dying) {
            if (dx > 0.0) {
                if (Keyboard.key(Keyboard.controls[3][ID])) {
                    dx = 1.1;
                } else if (Keyboard.key(Keyboard.controls[2][ID]) && !Keyboard.key(Keyboard.controls[3][ID])) {
                    dx = -0.1;
                }
            } else if (dx < 0.0) {
                if (Keyboard.key(Keyboard.controls[2][ID])) {
                    dx = -1.1;
                } else if (Keyboard.key(Keyboard.controls[3][ID]) && !Keyboard.key(Keyboard.controls[2][ID])) {
                    dx = 0.1;
                }
            }
        }
        if (Keyboard.key(Keyboard.controls[1][ID]) && !Keyboard.key(Keyboard.controls[3][ID]) && !Keyboard.key(Keyboard.controls[2][ID]) && !Keyboard.key(Keyboard.controls[0][ID]) && !dying) {
            crouchingAnimation++;
        } else if (!Keyboard.key(Keyboard.controls[1][ID]) && !dying) {
            crouchingAnimation = 0;
        }        
        if (Keyboard.key(Keyboard.controls[2][ID]) && !Keyboard.key(Keyboard.controls[3][ID]) && !dying) {
            isLeft = true;
            leftLast = true;
        } else if (!Keyboard.key(Keyboard.controls[2][ID]) && Keyboard.key(Keyboard.controls[3][ID]) && !dying) {
            isLeft = false;
            leftLast = false;
        }
        if (Keyboard.key(Keyboard.controls[2][ID]) && Keyboard.key(Keyboard.controls[3][ID]) && !dying) {
            if (!leftLast && !changedDirection) { //Left.
                changedDirection = true;
                isLeft = true;
            } else if (leftLast && !changedDirection) { //Right.
                changedDirection = true;
                isLeft = false;
            }
            x = 0;
        }
        if(!collision(0, y)) {
            falling = true;
        }
        xMove = x + (int)dx;
        move(xMove, y);
        collision = false;
    }
    
    public void render(boolean paused, boolean playerDead, int distance) {
        if (dying && !paused) {
            if (!isLeft) {
                dyingAnimation();
            } else {
                dyingLeftAnimation();
            }
        } else if (!dying && !paused) {
            if (Keyboard.key(Keyboard.controls[3][ID]) && Keyboard.key(Keyboard.controls[2][ID]) && !isLeft && !jumping && !falling) {
                idleAnimation();
            } else if (Keyboard.key(Keyboard.controls[3][ID]) && Keyboard.key(Keyboard.controls[2][ID]) && !jumping && !falling) {
                idleLeftAnimation();
            } else if (Keyboard.key(Keyboard.controls[3][ID]) && !jumping && canJump && !Keyboard.key(Keyboard.controls[5][ID])) { //Right-Ground.
                rightAnimation();
            } else if (Keyboard.key(Keyboard.controls[2][ID]) && !jumping && canJump && !Keyboard.key(Keyboard.controls[5][ID])) { //Left-Ground.
                leftAnimation();
            } else if (Keyboard.key(Keyboard.controls[3][ID]) && !jumping && canJump && Keyboard.key(Keyboard.controls[5][ID]) && !Keyboard.key(Keyboard.controls[1][ID])) { //Right-Ground Sprint.
                rightSprintAnimation();
            } else if (Keyboard.key(Keyboard.controls[2][ID]) && !jumping && canJump && Keyboard.key(Keyboard.controls[5][ID]) && !Keyboard.key(Keyboard.controls[1][ID])) { //Left-Ground Sprint.
                leftSprintAnimation();
            } else if (jumping && !isLeft) { //Right-Up.
                upAnimation();
            } else if (falling && !isLeft) { //Right-Down.
                downAnimation();
            } else if (jumping && isLeft) { //Left-Up.
                upLeftAnimation();
            } else if (falling && isLeft) { //Left-Down.
                downLeftAnimation();
            } else if (Keyboard.key(Keyboard.controls[1][ID]) && !Keyboard.key(Keyboard.controls[3][ID]) && !Keyboard.key(Keyboard.controls[2][ID]) && !Keyboard.key(Keyboard.controls[0][ID])) {
                crouchAnimation();
            } else if (!isLeft) { //Idle.
                idleAnimation();
            } else if (isLeft) { //Idle.
                idleLeftAnimation();
            }
            if (invincibilityAnimationCounter % 4 == 0 && invincibilityAnimationCounter != 0) { //Blinking
                sprite = new Sprite(16, 0, 0, Textures.mobs);     
            }
        }
        
        if (Math.abs(distance) <= DISTANCE_LIMIT || (playerDead)) {
            Render.renderRegular(16, x - 16, y - 16, sprite);
        } else {
            Render.renderFirstCamera(distance < 0, 16, x - 16, y - 16, sprite);
            Render.renderSecondCamera(distance > 0, 16, x - 16, y - 16, sprite);
        }
    }
    
    private void idleAnimation() {
        for(int i = 0; i < 12; i++) {
            if(animation <= 5 * i) {
                sprite = playerIdle[i];
                break;
            }
        }
    }
    
    private void idleLeftAnimation() {
        for(int i = 0; i < 12; i++) {
            if(animation <= 5 * i) {
                sprite = playerIdleLeft[i];
                break;
            }
        }
    }
    
    private void rightAnimation() {
        for(int i = 0; i < 5; i++) {
            if(animation <= 12 * i) {
                sprite = playerRight[i];
                break;
            }
        }
    }
    
    private void leftAnimation() {
        for(int i = 0; i < 5; i++) {
            if(animation <= 12 * i) {
                sprite = playerLeft[i];
                break;
            }
        }
    }
    
    private void rightSprintAnimation() {
        if(animation <= 30) {
            for(int i = 0; i < 5; i++) {
                if(animation <= 6 * i) {
                    sprite = playerRight[i];
                    break;
                }
            }
        }
        else {
            for(int i = 0; i < 5; i++) {
                if(animation <= 36 + (6 * i)) {
                    sprite = playerRight[i];
                    break;
                }
            }
        }
    }
    
    private void leftSprintAnimation() {
        if(animation <= 30) {
            for(int i = 0; i < 5; i++) {
                if(animation <= 6 * i) {
                    sprite = playerLeft[i];
                    break;
                }
            }
        }
        else {
            for(int i = 0; i < 5; i++) {
                if(animation <= 36 + (6 * i)) {
                    sprite = playerLeft[i];
                    break;
                }
            }
        }
    }
    
    private void upAnimation() {
        for(int i = 0; i < 4; i++) {
            if(jumpingAnimation < 2 + 2 * i) {
                sprite = playerUp[i];
                break;
            }
        }    
    }
    
    private void downAnimation() {
        for(int i = 0; i < 4; i++) {
            if(fallingAnimation < 7 + 7 * i) {
                sprite = playerDown[i];
                break;
            }
        }    
    }
    
    private void upLeftAnimation() {
        for(int i = 0; i < 5; i++) {
            if(jumpingAnimation < 2 + 2 * i) {
                sprite = playerUpLeft[i];
                break;
            }
        }
    }
    
    private void downLeftAnimation() {
        for(int i = 0; i < 4; i++) {
            if(fallingAnimation <= 7 + 7 * i) {
                sprite = playerDownLeft[i];
                break;
            }
        }  
    }
    
    private void crouchAnimation() {
        for(int i = 0; i < 4; i++) {
            if(crouchingAnimation <= 7 + 7 * i) {
                sprite = playerCrouch[i];
                break;
            }
        }
    }
    
    private void dyingAnimation() {
        for(int i = 0; i < 11; i++) {
            if(dyingCounter <= 10 + 10 * i) {
                sprite = playerDying[i];
                break;
            }
            else {
                sprite = playerDying[10];
            }
        }
    }
    
    private void dyingLeftAnimation() {
        for(int i = 0; i < 11; i++) {
            if(dyingCounter <= 10 + 10 * i) {
                sprite = playerDyingLeft[i];
                break;
            }
            else {
                sprite = playerDyingLeft[10];
            }
        }
    }
    
}