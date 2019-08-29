package game.entity;

import game.Render;
import game.Sprite;
import game.Textures;
import static game.Update.DISTANCE_LIMIT;
import java.util.ArrayList;

public class ScoreCounter extends Mob {
    
    private int counter;
    private final ArrayList<Sprite> sprite = new ArrayList<>(); //+/- | Score
    
    public ScoreCounter(int x, int y, int scoreChange, boolean positive) {
        this.x = x;
        this.y = y;
        //index 26 - 36 is 0 - 9
        Sprite[] numbers = new Sprite[10];
        for (int i = 0; i < 6; i++) numbers[i] = new Sprite(8, 2 + i, 3, Textures.smallText);
        for (int i = 0; i < 4; i++) numbers[i + 6] = new Sprite(8, i, 4, Textures.smallText);
        if (positive) {
            sprite.add(new Sprite(8, 4, 4, Textures.smallText));
            game.Update.score += scoreChange;
        } else {
            sprite.add(new Sprite(8, 5, 4, Textures.smallText));
        }
        //Eventually put these with the positive and negative stuff, because color will change.
        int number = scoreChange;
        while (number != 0) {
            sprite.add(1, numbers[(number % 10)]);
            number /= 10;
        }
        counter = 0;
    }
    
    public boolean isDead() {
        return (counter == 100);
    }
    
    int opacity = 0xff000000;
    
    public void update() {
        counter++;
        if (counter % 6 == 0) opacity -= 0x11000000;
        if (counter % 2 == 0) y--;
        setPos(x, y);
    }
    
    public void render(int xCamera, int xCamera2, boolean playerDead, int distance) {
        int xTranslate = x - ((sprite.size() * 8) / 2);
        if (Math.abs(distance) <= DISTANCE_LIMIT || playerDead) {
            for (int i = 0; i < sprite.size(); i++) Render.renderRegular(8, xTranslate + (8 * i) - 8, y - 16, sprite.get(i));
        } else {
            for (int i = 0; i < sprite.size(); i++) Render.renderFirstCamera(distance < 0, 8, xTranslate + (8 * i) - 8, y - 16, sprite.get(i));
            for (int i = 0; i < sprite.size(); i++) Render.renderSecondCamera(distance > 0, 8, xTranslate + (8 * i) - 8, y - 16, sprite.get(i));
        }
    }
    
}
