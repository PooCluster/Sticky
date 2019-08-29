package game.logic;

import game.Render;
import game.Sprite;
import static game.Sprite.text;
import game.Textures;

public class MenuText {
    
    private int x;
    private int y;
    private int z;

    public MenuText(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        for (int i = 0; i < 7; i++) {
            for(int j = 0; j < 8; j++){
                text[j + i * 8] = new Sprite(16, j, i, Textures.text);
            }
        }
        text[56] = new Sprite(16, 0, 7, Textures.text);
    }
    
    public void render() {
        Render.renderHUD(16, x, y, text[z]);
    }
    
}
