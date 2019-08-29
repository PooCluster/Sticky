package game.entity;

import game.Render;
import game.Sprite;

public class Background extends Mob {
    
    //private Sprite sprite;
    private double x;
    private double y;
    private double xInitial;
    private double yInitial;
    private double xDistance;
    private double yDistance;
    private double xDistance2;
    private double yDistance2;
    
    public Background(int x, int y, int xInitial, int yInitial, Sprite sprite) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.xInitial = xInitial;
        this.yInitial = yInitial;
    }
    
    public void update(int xPlayer, int yPlayer, int xPlayer2, int yPlayer2) {
        xDistance = ((double)xPlayer - xInitial) / 2;
        yDistance = ((double)yPlayer - yInitial) / 2;
        xDistance2 = ((double)xPlayer2 - xInitial) / 2;
        yDistance2 = ((double)yPlayer2 - yInitial) / 2;
        
    }

    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public void changeNight() {
        sprite = Sprite.nightSky;
    }
    
    public void changeDay() {
        sprite = Sprite.daySky;
    }
    
    public Sprite getSprite() {
        return sprite;
    }
    
    public void render(int xCamera, int yCamera) {
        if (x + xDistance < xCamera + 250 && x + xDistance > xCamera - 250 && y + yDistance < yCamera + 150 && y + yDistance > yCamera - 150)
            Render.renderRegular(16, (int)this.x - 16 + (int)xDistance, (int)this.y - 16 + (int)yDistance, sprite);
    }
    
    public void render(int xCamera, int yCamera, int xCamera2, int yCamera2, int distance) {
        if (x + xDistance < xCamera + 250 && x + xDistance > xCamera - 250 && y + yDistance < yCamera + 150 && y + yDistance > yCamera - 150) {
            Render.renderFirstCamera(distance < 0, 16, (int)x - 16 + (int)xDistance, (int)y - 16 + (int)yDistance, sprite);
        }
        if (x + xDistance2 < xCamera2 + 250 && x + xDistance2 > xCamera2 - 250 && y + yDistance2 < yCamera2 + 150 && y + yDistance2 > yCamera2 - 150) {
            Render.renderSecondCamera(distance > 0, 16, (int)x - 16 + (int)xDistance2, (int)y - 16 + (int)yDistance2, sprite);
        }
    }
    
}
