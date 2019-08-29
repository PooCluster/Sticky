package game;

import game.logic.GameStateManager;
import game.input.Keyboard;
import game.input.Mouse;
import game.entity.Player;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class Main extends Canvas {
    
    public final static int width = 400;
    public final static int height = width / 16 * 9;
    public static double upScale = 3.0;
    public static double upScaleY;
    private static final String title = "Dank Beta v. 1.1";
    public static boolean running = true;
    
    public static JFrame frame;
    public static BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    public static int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    
    public static void main(String[] args) throws InterruptedException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        Main g = new Main();
        settings(g);
        g.gameLoop();
    }
    
    public Main() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        setPreferredSize(new Dimension(width * (int)upScale, height * (int)upScale));
        Render.width = width;
        Render.height = height;
        Render.pixels = new int[width * height];
        frame = new JFrame();
        Keyboard.controls[0][0] = KeyEvent.VK_W;
        Keyboard.controls[1][0] = KeyEvent.VK_S;
        Keyboard.controls[2][0] = KeyEvent.VK_A;
        Keyboard.controls[3][0] = KeyEvent.VK_D;
        Keyboard.controls[4][0] = KeyEvent.VK_E;
        Keyboard.controls[5][0] = KeyEvent.VK_SHIFT;
        Keyboard.controls[0][1] = KeyEvent.VK_UP;
        Keyboard.controls[1][1] = KeyEvent.VK_DOWN;
        Keyboard.controls[2][1] = KeyEvent.VK_LEFT;
        Keyboard.controls[3][1] = KeyEvent.VK_RIGHT;
        Keyboard.controls[4][1] = KeyEvent.VK_PERIOD;
        Keyboard.controls[5][1] = KeyEvent.VK_COMMA;
        addKeyListener(new Keyboard());
        addMouseListener(new Mouse());
        addMouseMotionListener(new Mouse());
        SoundEngine.loadReferences();
        StickyData.load();
        for (int i = 0; i < 3; i++) GameStateManager.state.push(i);
        for (int i = 0; i < Update.initialHealth.length; i++) Update.initialHealth[i] = 4;
        Update.player.add(new Player(432, 416, Update.player.size()));
        //Update.locations = new LoadLevel(Update.currentLevel);
        LoadLevel.loadLevel(Update.currentLevel);
        GameStateManager.loadVideo(0);
    }
    
    public static void settings(Main g) {
        frame.setTitle(title);
        frame.add(g);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        g.requestFocus();
    }
    
    public void update() throws FileNotFoundException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        upScale = getWidth() / 400.0; //400 is the original size of the game.
        upScaleY = getHeight() / (400.0 / 16.0 * 9.0);
        GameStateManager.update();
    }

    public void render() {
        BufferStrategy buffer = getBufferStrategy();
        if (buffer == null) {
            createBufferStrategy(3);
            return;
        }
        Render.clear();
        GameStateManager.render();
        Graphics graphics = buffer.getDrawGraphics();
        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        buffer.show();
    }
    
    public void gameLoop() throws InterruptedException, FileNotFoundException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        int fpsCounter = 0; 
        long time = System.currentTimeMillis();
        long previousTime = System.nanoTime();
        double nseconds = 1000000000.0 / 60.0;
        double difference = 0;
        while (running) {
            //Thread.sleep(10);
            long currentTime = System.nanoTime();
            difference += (currentTime - previousTime) / nseconds;
            previousTime = currentTime;
            while (difference >= 1) {
                update();
                //render();
                //fpsCounter++;
                difference--;
            }
            render();
            fpsCounter++;
            if (System.currentTimeMillis() - time > 1000) {
                time += 1000;
                frame.setTitle(title + " | " + fpsCounter + " FPS");
                fpsCounter = 0;
            }
        }
        System.exit(0);
    }

}