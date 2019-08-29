package game.entity;

import game.Render;
import game.Sprite;
import static game.Sprite.text;
import game.Textures;

public class Text extends Mob {
    
    private int i;
    
    public Text(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.i = z;
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 8; j++){
                text[j + i * 8] = new Sprite(16, j, i, Textures.text);
            }
        }
        text[56] = new Sprite(16, 0, 7, Textures.text);
        sprite = text[i];
    }
    
    public static Text character(int x, int y, char a) {
        Text text = new Text(x, y, 0);
        for (int i = 0; i < 26; i++) {
            if (a == 'A' + i) {
                text = new Text(x, y, i);
            }
        }
        for (int i = 0; i < 10; i++) {
            if (a == '0' + i) {
                text = new Text(x, y, 26 + i);
            }
        }
        switch (a) {
            case '"':
                text = new Text(x, y, 36);
                break;
            case ',':
                text = new Text(x, y, 37);
                break;
            case '.':
                text = new Text(x, y, 38);
                break;
            case 39:
                text = new Text(x, y, 39);
                break;
            case '?':
                text = new Text(x, y, 40);
                break;
            case '!':
                text = new Text(x, y, 41);
                break;
            case '+':
                text = new Text(x, y, 48);
                break;
            case '-':
                text = new Text(x, y, 49);
                break;
            case '*':
                text = new Text(x, y, 50);
                break;
            case '/':
                text = new Text(x, y, 51);
                break;
            case '=':
                text = new Text(x, y, 52);
                break;
            case '%':
                text = new Text(x, y, 53);
                break;
            case '(':
                text = new Text(x, y, 54);
                break;
            case ')':
                text = new Text(x, y, 55);
                break;
            case '_':
                text = new Text(x, y, 56);
                break;
            default:
                break;
        }
        return text;
    }
    
    public static Text[] outline(int x, int y, int length) { //x y are pos, length is the length of the word
        Text[] text = new Text[2 * (length + 1)];
        text[0] = new Text(x - 8, y - 8, 42);
        for(int i = 0; i < length - 1; i++) {
            text[1 + i] = new Text(x + 8 + (16 * i), y - 8, 43);
        }
        text[length] = new Text(x - 8 + (16 * length), y - 8, 44);
        text[length + 1] = new Text(x - 8, y + 8, 45);
        for(int i = 0; i < length - 1; i++) {
            text[(length + 2) + i] = new Text(x + 8 + (16 * i), y + 8, 46);
        }
        text[2 * (length + 1) - 1] = new Text(x - 8 + (16 * length), y + 8, 47);
        return text;
    }
    
    public void update(int x, int y) {
        this.x = x;
        this.y = y;
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