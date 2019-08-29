package game.entity;

import game.Render;
import game.Sprite;
import game.Textures;
import static game.Update.player;
import game.input.Keyboard;
import java.util.Random;

public class MovingPlatform extends Mob {

    private final int MAX_DISTANCE;
    private boolean isLeft = false;
    private int distance = 0;
    private final boolean horizontal;
    private final boolean vertical;
    private final int[] xOffset = new int[2];
    //private int xOffset = 0;
    private int speed;
    private int counter;
    private String type;
    private int yPermanent;
    private double fall = 0.1;
    private boolean[] playerOn = new boolean[2];
    
    public MovingPlatform(int x, int y, int distance, boolean horizontal, boolean vertical, int speed, String type, String sprite) {
        this.horizontal = horizontal;
        this.vertical = vertical;
        MAX_DISTANCE = distance;
        this.x = x;
        this.y = yPermanent = y;
        if (speed == 1) {
            this.speed = 2;
        } else if (speed == 2) {
            this.speed = 1;
        }
        this.type = type;
        if (sprite.equals("dirt")) {
            this.sprite = Sprite.movingPlatformDirt;
        } else if (sprite.equals("stone")) {
            Random random = new Random();
            int a = random.nextInt(4);
            for (int i = 0; i < 4; i++) {
                Sprite.movingPlatformStone[i] = new Sprite(16, 2 + i, 1, Textures.textures);
            }
            this.sprite = Sprite.movingPlatformStone[a];
        }
    }
    
    private void movePlayer() {
        for (int i = 0; i < player.size(); i++) {
            if (xOffset[i] <= 16 && xOffset[i] >= -16
                && player.get(i).getYPos() <= y - 15 && player.get(i).getYPos() > y - 20) {
                if (!Keyboard.key(Keyboard.controls[0][i]) || player.get(i).isFalling()) {
                    player.get(i).setPos(x + xOffset[i], y - 16);
                    player.get(i).setCollision(true);
                    playerOn[i] = true; 
                    player.get(i).setIdle();
                }
            } else {
                playerOn[i] = false;
            }
        }
    }
    
    public void update() {
        for (int i = 0; i < player.size(); i++) {
            if (!playerOn[i]) {
                xOffset[i] = player.get(i).getXPos() - x;
            } else if (!player.get(i).isDead()) {
                xOffset[i] += player.get(i).xMove();
            }
        }
        if (type.equals("move")) {
            if (counter % speed == 0) {
                distance++;
            }
            if (distance == MAX_DISTANCE) {
                distance = 0;
                if (isLeft) {
                    isLeft = false;
                } else {
                    isLeft = true;
                }
            }
            if (horizontal && counter % speed == 0) {
                if (isLeft) {
                    x++;
                } else {
                    x--;
                }
            }
            if (vertical && counter % speed == 0) {
                if (isLeft) {
                    y++;
                } else {
                    y--;
                }
            }
            counter++;
        } else if (type.equals("fall")) {
            for (int i = 0; i < player.size(); i++) {
                if (playerOn[i] && counter == 0) {
                    counter++;
                }
            }
            if (counter != 0) {
                counter++;
            }
            if (y > level.getHeight() * 16 + 32) {
                counter = 0;
                fall = 0.1;
                
            }
            if (counter == 0) {
                if (y != yPermanent) {
                    y--;
                }
            }
            if (counter >= 30) {
                y += fall;
                if (fall < 4) {
                   fall += 0.1; 
                }
            }
        }
        movePlayer();
    }
    
    public void render(int xCamera, int yCamera) {
        if (x < xCamera + 250 && x > xCamera - 250 && y < yCamera + 150 && y > yCamera - 150) {
            Render.renderRegular(16, x - 16, y - 16, sprite);
        }
    }
    
    public void render(int xCamera, int yCamera, int xCamera2, int yCamera2, int distance) {
        if (x < xCamera + 250 && x > xCamera - 250 && y < yCamera + 150 && y > yCamera - 150) {
            Render.renderFirstCamera(distance < 0, 16, x - 16, y - 16, sprite);
        }
        if (x < xCamera2 + 250 && x > xCamera2 - 250 && y < yCamera2 + 150 && y > yCamera2 - 150) {
            Render.renderSecondCamera(distance > 0, 16, x - 16, y - 16, sprite);
        }
    }
    
}