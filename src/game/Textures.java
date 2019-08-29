package game;

import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

public class Textures {
    
    public int[] pixels;
    public final int size;
    
    public static Textures textures = new Textures("resources/textures/Level.png", 256);
    public static Textures mobs = new Textures("resources/textures/Mobs.png", 256);
    public static Textures boss = new Textures("resources/textures/Boss.png", 512);
    public static Textures text = new Textures("resources/textures/Text.png", 128);
    public static Textures smallText = new Textures("resources/textures/Small Text.png", 64);
    public static Textures map = new Textures("resources/textures/Map.png", 128);
    
    public Textures(String name, int size) {
        this.size = size;
        pixels = new int[this.size * this.size];
        loadImage(name);
    }
    
    private void loadImage(String name) {
        try {
            BufferedImage image = ImageIO.read(Main.class.getResource(name));
            int x = image.getWidth();
            int y = image.getHeight();
            image.getRGB(0, 0, x, y, pixels, 0, x); //Start of x, Start of y, width, height, pixels is where to store, 0 is offset, x is width scan size
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
}