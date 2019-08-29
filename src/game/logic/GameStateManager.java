package game.logic;

import game.Main;
import game.Render;
import game.Update;
import static game.Update.player;
import game.input.Keyboard;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import javax.imageio.ImageIO;

public class GameStateManager {
    
    /*
    Stack Layers
    2 - title screen
        -Maybe this is where the current menu can go.
    1 - map
        -Create a new menu
    0 - actual level
        -Create a new menu here
    
    Potential Interrupts.
    Videos
    Survival Mode (This mode is so minimal that it'll just be treated as an interrupt.
    */
    
    public static final Stack<Integer> state = new Stack<>();
    
    private static final Queue<BufferedImage> video = new LinkedList<>();
    private static int fps;
    private static int videoCounter;
    
    private static boolean survival;
    public static boolean videoPlaying;
    
    
    public static Menu menu;
    
    private static boolean menuInitial;
    public static boolean playersDead;
    public static boolean playerDead;
    
    public static void loadVideo(int index) {
        try {
            Scanner scan = new Scanner(Main.class.getResourceAsStream("resources/videos/" + index + "/Information.txt"));
            scan.next();
            int length = scan.nextInt();
            scan.next();
            fps = 60 / scan.nextInt(); //If 15, fps = 4. That way, ever 4/60th of a second, a new image is shown, meaning in a second, 15 images are shown.
            for (int i = 0; i < length; i++) {
                video.add(ImageIO.read(Main.class.getResource("resources/videos/" + index + "/" + i + ".png")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void video() {
        if (!video.isEmpty()) {
            if (videoCounter % fps == 0) {
               Main.image = video.remove();
            }
            videoCounter++;
            if (video.isEmpty()) {
                Main.image = new BufferedImage(Main.width, Main.height, BufferedImage.TYPE_INT_ARGB);
                Main.pixels = ((DataBufferInt)Main.image.getRaster().getDataBuffer()).getData();
                videoCounter = 0;
            }
        }
        videoPlaying = !video.isEmpty();
    }
    
    
    public static void update() {
        video();
        if (!videoPlaying && !survival) {
            //Menu Switch.
            playersDead = true;
            playerDead = false;
            for (int i = 0; i < player.size(); i++) {
                if (!player.get(i).isDead()) {
                    playersDead = false;
                }
                if (player.get(i).isDead()) {
                    playerDead = true;
                }
            }
            if (menu == null) {
                if (playersDead && state.peek() == 0) {
                    menu = new game.logic.Menu(state.peek(), 4);
                    game.SoundEngine.clear();
                    game.SoundEngine.loadMusic("resources/audio/music/SongMinor1.wav");
                }
                if (state.peek() == 2) menu = new game.logic.Menu(state.peek(), 0);
            }
            if (state.peek() == 1) {                
                if (menu == null && Update.mapPlayer.isOnLevel()) {
                    menu = new game.logic.Menu(state.peek(), 0);
                } else if (menu != null && !Update.mapPlayer.isOnLevel() && menu.history.size() == 1 && menu.history.peek() == 0) {
                    menu = null;
                }
            }                
            if (Keyboard.key(KeyEvent.VK_ESCAPE) && !menuInitial) menuInitial = true;
            if (state.peek() != 2 && state.peek() != 1) { //Cannot escape the menu at titlescreen or sometimes map!
                if (!Keyboard.key(KeyEvent.VK_ESCAPE) && menuInitial) {
                    if (menu == null) {
                        menu = new game.logic.Menu(state.peek(), 0);
                    } else {
                        if (!playersDead) { //Game Over
                            if (menu.history.size() == 1) {
                                menu = null;
                            } else {
                                menu.removeLayer();
                            }
                        }
                    }
                    menuInitial = false;
                }
            } else { //Title Screen and Map Selection
                //FIX THIS LATER.
                if (!Keyboard.key(KeyEvent.VK_ESCAPE) && menuInitial) {
                    if (state.peek() == 1) {
                        if (menu != null) {
                            if (menu.history.size() == 1) {
                                if (menu.history.peek() == 0) {
                                    menu.pushLayer(1);
                                } else if (menu.history.peek() == 1) {
                                    menu = null;
                                }
                            } else {
                                menu.removeLayer();
                            }
                        } else {
                            menu = new game.logic.Menu(state.peek(), 1);
                        }
                    }
                    if (state.peek() == 2) {
                        if (menu.history.size() > 1) menu.removeLayer();
                    }
                    menuInitial = false;
                }
                
            }
            if (menu != null) menu.update();
            if ((menu == null && !playersDead) || (menu != null && playersDead) || state.peek() == 2 || 
                    (state.peek() == 1 && menu.history.peek() == 0)) { //Basically pause.
                switch (state.peek()) {
                    case 0:
                        Update.levelUpdate();
                        break;
                    case 1:
                        Update.mapLevelSelectionUpdate();
                        break;
                    case 2:
                        Update.titleScreenUpdate(); //Updates no matter what, yo.
                        break;
                    case 3:
                        Update.survivalUpdate();
                        break;
                }
            }
        }
    }
    
    public static void render() {
        Render.xMove = Update.xCamera - Render.width / 2;
        Render.yMove = Update.yCamera - Render.height / 2;
        Render.xMove2 = Update.xCamera2 - Render.width / 2;
        Render.yMove2 = Update.yCamera2 - Render.height / 2;
        if (!videoPlaying) {
            switch (state.peek()) {
                case 0:
                    Render.levelRender();
                    break;
                case 1:
                    Render.mapLevelSelectionRender();
                    break;
                case 2:
                    Render.titleScreenRender();
                    break;
                case 3:
                    Render.survivalRender();
                    break;
            }
        }
        
    }
    
}