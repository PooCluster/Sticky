package game.entity.hud;

import game.Render;
import game.Sprite;
import static game.Sprite.text;
import game.Textures;
import game.entity.Mob;

public class ScoreText extends Mob {

    public ScoreText(int index, int number) {
        this.x = 386 - (10 * index);
        this.y = 1;
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 8; j++){
                text[j + i * 8] = new Sprite(16, j, i, Textures.text);
            }
        }
        text[56] = new Sprite(16, 0, 7, Textures.text);
        sprite = text[number];
    }

    public void render() {
        Render.renderHUD(16, x, y, sprite);
    }
    
}
