package game;

import game.entity.Background;
import game.entity.Cloud;
import game.entity.Coin;
import game.entity.EnergyBall;
import game.entity.FakeDirt;
import game.entity.FakeGrassDirtRightWall;
import game.entity.Flower;
import game.entity.Logo;
import game.entity.MovingPlatform;
import game.entity.Player;
import game.entity.ScoreCounter;
import game.entity.Text;
import game.entity.enemy.Joe;
import game.entity.enemy.Pedaller;
import game.entity.hud.HUD;
import game.input.Keyboard;
import game.level.Level;
import game.level.map.Map;
import game.entity.MapPlayer;
import game.logic.GameStateManager;
import static game.logic.GameStateManager.playerDead;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Update {
    
    public static int xMax;
    public static final int xMin = 224;
    public static int yMax;
    public static int score = 0;
    public static int initialScore = score;
    public static int currentLevel = 0;
    public static Calendar time = Calendar.getInstance();
    public static boolean dayTime = true;
    public static final int MAX_HEALTH = 4;
    public static final int NIGHT_TIME = 18; //6PM.
    
    
    public static int xCamera = 0;
    public static int yCamera = 0;
    public static int xCamera2 = 0;
    public static int yCamera2 = 0;
    public static int distance = 0;
    public static final int DISTANCE_LIMIT = 200;
    
    
    public static ArrayList<Joe> joe = new ArrayList<>();
    public static ArrayList<Pedaller> pedaller = new ArrayList<>();
    public static ArrayList<Cloud> cloud = new ArrayList<>();
    public static ArrayList<Flower> flower = new ArrayList<>();
    public static ArrayList<Text> text = new ArrayList<>();
    public static ArrayList<FakeGrassDirtRightWall> fakeGrassDirtRightWall = new ArrayList<>();
    public static ArrayList<FakeDirt> fakeDirt = new ArrayList<>();
    public static ArrayList<MovingPlatform> movingPlatform = new ArrayList<>();
    public static ArrayList<Coin> coin = new ArrayList<>();
    public static ArrayList<Player> player = new ArrayList<>();
    public static ArrayList<EnergyBall> ball = new ArrayList<>();
    public static Background[] background;
    public static ArrayList<ScoreCounter> scoreCounter = new ArrayList<>();
    
    //public static LoadLevel locations;
    public static Level level;
    public static HUD hud = new HUD();
    public static Logo logo = new Logo(14, 16);
    
    public static int currentMap = 1;
    public static Map map;
    public static LoadMap loadMap;
    public static MapPlayer mapPlayer;
    
    public static Set<Integer> levelsCompleted = new HashSet<>();
        
    public static int[] initialHealth = new int[2];
    
    public static int gameSlot = 0;
    public static String currentName = "78 69 87";
    
    public static void levelUpdate() {
        //Updates.
        for (int i = 0; i < player.size(); i++) {
            player.get(i).update();
            if (player.get(i).getXPos() >= (level.getWidth() * 16) - 16) {
                for (int j = 0; j < player.size(); j++) {
                    if (player.get(j).getHealth() == 0) player.get(j).setHealth(1);
                    initialHealth[j] = player.get(j).getHealth();
                }
                initialScore = score;
                GameStateManager.state.push(1);
                levelsCompleted.add(currentLevel);
                loadMap = new LoadMap(1);
            }
        }
        cameraLogic();
        for (int i = 0; i < joe.size(); i++) {
            joe.get(i).update(i, playerDead); //Always update, otherwise, looks unrealistic.
            if (joe.get(i).isDead(i)) {
                scoreCounter.add(new ScoreCounter(joe.get(i).getXPos(), joe.get(i).getYPos(), 1, true));
                joe.remove(i);
            }
        }
        for (int i = 0; i < pedaller.size(); i++) {
            if (pedaller.get(i).getXPos() > xCamera - 300 && pedaller.get(i).getXPos() < xCamera + 300) pedaller.get(i).update();
            if (pedaller.get(i).isDead()) {
                scoreCounter.add(new ScoreCounter(pedaller.get(i).getXPos(), pedaller.get(i).getYPos(), 2, true));
                pedaller.remove(i);
            }
        }
        for (int i = 0; i < cloud.size(); i++) {
            cloud.get(i).update();
            if (cloud.get(i).isDead() || (cloud.get(i).getXPos() > (level.getWidth() * 16) + 32)) {
                cloud.get(i).setIdle();
                cloud.get(i).setPos(-16);
            }
        }
        for (int i = 0; i < scoreCounter.size(); i++) {
            scoreCounter.get(i).update();
            if (scoreCounter.get(i).isDead()) scoreCounter.remove(i);
        }
        time = Calendar.getInstance();
        if ((time.get(Calendar.HOUR_OF_DAY) >= NIGHT_TIME || time.get(Calendar.HOUR_OF_DAY) < NIGHT_TIME - 12) && dayTime) { //Changing into night.
            OUTER:
            for (int i = 0; i < level.getHeight(); i++) {
                for (int j = 0; j < level.getWidth(); j++) {
                    if (background[j + i * level.getWidth()].getSprite() == Sprite.daySky) {
                        background[j + i * level.getWidth()].changeNight();
                        break OUTER;
                    }
                }
            }
        } else if ((time.get(Calendar.HOUR_OF_DAY) < NIGHT_TIME && time.get(Calendar.HOUR_OF_DAY) >= NIGHT_TIME - 12) && !dayTime) { //Changing into day.
            OUTER:
            for (int i = 0; i < level.getHeight(); i++) {
                for (int j = 0; j < level.getWidth(); j++) {
                    if (background[j + i * level.getWidth()].getSprite() == Sprite.nightSky) {
                        background[j + i * level.getWidth()].changeDay();
                        break OUTER;
                    }
                }
            }
        }
        for (int i = 0; i < level.getHeight(); i++) 
            for (int j = 0; j < level.getWidth(); j++)
                background[j + i * level.getWidth()].update(xCamera, yCamera, xCamera2, yCamera2);
        for (int i = 0; i < coin.size(); i++) coin.get(i).update();
        for (int i = 0; i < movingPlatform.size(); i++) movingPlatform.get(i).update();
        for (int i = 0; i < flower.size(); i++) 
            for (int j = 0; j < player.size(); j++)
                if (flower.get(i).getXPos() < player.get(j).getXPos() + 250 && flower.get(i).getXPos() > player.get(j).getXPos() - 250)
                    flower.get(i).update();
        //Interactions.
        for (int i = 0; i < player.size(); i++) {
            if (player.get(i).getYPos() > (level.getHeight() * 16) + 32) player.get(i).setHealth(0);
            for (int j = 0; j < joe.size(); j++)
                if (!joe.get(j).isDying())
                    if (player.get(i).isHit(joe.get(j).getXPos(), joe.get(j).getYPos())) 
                        scoreCounter.add(new ScoreCounter(player.get(i).getXPos(), player.get(i).getYPos(), 1, false));
            for (int j = 0; j < pedaller.size(); j++)
                if (!pedaller.get(j).isDying() && pedaller.get(j).isHittable())
                    if (player.get(i).isHit(pedaller.get(j).getXPos(), pedaller.get(j).getYPos()))
                        scoreCounter.add(new ScoreCounter(player.get(i).getXPos(), player.get(i).getYPos(), 1, false));
            for (int j = 0 ; j < coin.size(); j++) {
                if (coin.get(j).isHit(player.get(i).getXPos(), player.get(i).getYPos())) {
                    coin.remove(j);
                    scoreCounter.add(new ScoreCounter(player.get(i).getXPos(), player.get(i).getYPos(), 1, true));
                }
            }
            if (Keyboard.key(Keyboard.controls[4][i]) && player.get(i).getHealth() != 0) {
                boolean exists = false;
                for (int j = 0; j < ball.size(); j++) {
                    if (ball.get(j).isID(i)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    ball.add(new EnergyBall(player.get(i).getXPos(), player.get(i).getYPos(), player.get(i).getVelocity(), player.get(i).isLeft(), i));
                }
            }
        }
        for (int i = 0; i < ball.size(); i++) {
            ball.get(i).move();
            for (int j = 0; j < joe.size(); j++) {
                if (!ball.get(i).isDying() && !joe.get(j).isDying()) {
                    ball.get(i).update(joe.get(j).getXPos(), joe.get(j).getYPos());
                    joe.get(j).isHit(ball.get(i).getXPos(), ball.get(i).getYPos());
                }
            }
            for (int j = 0; j < pedaller.size(); j++) {
                if (!ball.get(i).isDying() && !pedaller.get(j).isDying() && pedaller.get(j).isHittable()) {
                    ball.get(i).update(pedaller.get(j).getXPos(), pedaller.get(j).getYPos());
                    pedaller.get(j).isHit(ball.get(i).getXPos(), ball.get(i).getYPos());
                }
            }
            for (int j = 0; j < cloud.size(); j++)
                if (!ball.get(i).isDying() && !cloud.get(j).isDying())
                    cloud.get(j).isHit(ball.get(i).getXPos(), ball.get(i).getYPos());
            if (ball.get(i).isDead()) ball.remove(i);
        }
        hud.update();
    }
    
    public static void mapLevelSelectionUpdate() {
        mapPlayer.update();
        if (Keyboard.key(KeyEvent.VK_ENTER) && mapPlayer.isOnLevel()) {
            currentLevel = map.whichLevel();
            try {
                //locations = new LoadLevel(currentLevel);
                LoadLevel.loadLevel(currentLevel);
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
            GameStateManager.state.pop();
            GameStateManager.menu = null;
        }
    }
    
    public static void titleScreenUpdate() {
        levelUpdate();
        //Nothing to add for now.
    }
    
    public static void survivalUpdate() {
        levelUpdate();
        //Survival Logic.
        
    }
    
    //Other non-main udpate methods
    
    private static void cameraLogic() {
        if (player.size() == 2) {
            distance = player.get(0).getXPos() - player.get(1).getXPos(); //positive means player 1 is on the right.
            if (Math.abs(distance) > DISTANCE_LIMIT && !player.get(0).isDead() && !player.get(1).isDead()) {
                xCamera = Camera.xCameraSplit(player.get(0).getXPos(), xMin, xMax, distance);
                xCamera2 = Camera.xCameraSplit2(player.get(1).getXPos(), xMin, xMax, distance);
                yCamera = Camera.yCamera(player.get(0).getYPos(), yMax);
                yCamera2 = Camera.yCamera(player.get(1).getYPos(), yMax);
            } else {
                if (!player.get(0).isDead() && !player.get(1).isDead()) {
                    xCamera = Camera.xCamera(player.get(0).getXPos(), player.get(1).getXPos(), xMin, xMax);
                    yCamera = Camera.yCamera(player.get(0).getYPos(), player.get(1).getYPos(), yMax);
                } else if (player.get(0).isDead() && !player.get(1).isDead()) {
                    xCamera = Camera.xCamera(player.get(1).getXPos(), xMin, xMax);
                    yCamera = Camera.yCamera(player.get(1).getYPos(), yMax);
                } else if (player.get(1).isDead() && !player.get(0).isDead()) {
                    xCamera = Camera.xCamera(player.get(0).getXPos(), xMin, xMax);
                    yCamera = Camera.yCamera(player.get(0).getYPos(), yMax);
                }
            }
        } else {
            xCamera = Camera.xCamera(player.get(0).getXPos(), xMin, xMax);
            yCamera = Camera.yCamera(player.get(0).getYPos(), yMax);
            distance = 0;
        }
    }
    
}
