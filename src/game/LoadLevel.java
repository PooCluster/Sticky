package game;

import game.entity.Background;
import game.entity.Cloud;
import game.entity.Coin;
import game.entity.FakeDirt;
import game.entity.FakeGrassDirtRightWall;
import game.entity.Flower;
import game.entity.enemy.Joe;
import game.entity.enemy.Pedaller;
import game.entity.Text;
import game.level.Level;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;
import static game.Sprite.daySky;
import static game.Sprite.nightSky;
import game.entity.MovingPlatform;
import java.io.IOException;
import java.util.Calendar;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class LoadLevel {

    public LoadLevel(int name) throws FileNotFoundException, LineUnavailableException, UnsupportedAudioFileException, IOException {
        Update.level = new Level("resources/levels/" + name + "/" + name + ".png");
        //loadLevel("resources/levels/" + name + "/" + name + ".txt");
    }

    //public static void loadLevel(String filePath) throws FileNotFoundException, LineUnavailableException, UnsupportedAudioFileException, IOException {
    public static void loadLevel(int name) throws FileNotFoundException, LineUnavailableException, UnsupportedAudioFileException, IOException {
        Update.level = new Level("resources/levels/" + name + "/" + name + ".png");
        Update.xMax = (Update.level.getWidth() * 16) - 224;
        Update.yMax = (Update.level.getHeight() * 16) - 116;
        Scanner scan = new Scanner(new InputStreamReader(Main.class.getResourceAsStream("resources/levels/" + name + "/" + name + ".txt")));
        String key = scan.next();
        if (key.equals("music:")) {
            SoundEngine.clear();
            SoundEngine.loadMusic(scan.next());
            key = scan.next();
        }
        if(key.equals("playerPos:")) {
            int x = scan.nextInt();
            int y = scan.nextInt();
            scan.nextLine();
            for (int i = 0; i < Update.player.size(); i++) {
                Update.player.get(i).setPos(x, y);
                Update.player.get(i).setIdle();
                Update.player.get(i).setVelocity(0.0);
                Update.player.get(i).initialize(Update.level);
            }
            if (Update.player.size() == 2) {
                if (Update.player.get(1).isDead()) {
                    Update.player.get(1).setHealth(1);
                }
            }
            key = scan.next();
        }
        if(key.equals("joe:")) {
            Update.joe.clear();
            while (scan.hasNextInt()) {
                int x = scan.nextInt();
                int y = scan.nextInt();
                Update.joe.add(new Joe(x, y));
            }
            key = scan.next();
        }
        if(key.equals("pedaller:")) {
            Update.pedaller.clear();
            while (scan.hasNextInt()) {
                int x = scan.nextInt();
                int y = scan.nextInt();
                Update.pedaller.add(new Pedaller(x, y));
            }
            key = scan.next();
        }
        if(key.equals("flower:")) {
            Update.flower.clear();
            while (scan.hasNextInt()) {
                int x = scan.nextInt();
                int y = scan.nextInt();
                Update.flower.add(new Flower(x, y));
            }
            key = scan.next();
        }
        if(key.equals("coin:")) {
            Update.coin.clear();
            while (scan.hasNextInt()) {
                int x = scan.nextInt();
                int y = scan.nextInt();
                Update.coin.add(new Coin(x, y));
            }
            key = scan.next();
        }
        if (key.equals("text:")) {
            Update.text.clear();
            while (scan.hasNextInt()) {
                int x = scan.nextInt();
                int y = scan.nextInt();
                int z = scan.nextInt();
                Update.text.add(new Text(x, y, z));
            }
            key = scan.next();
        }
        if(key.equals("fakeDirt:")) {
            Update.fakeDirt.clear();
            while (scan.hasNextInt()) {
                int x = scan.nextInt();
                int y = scan.nextInt();
                Update.fakeDirt.add(new FakeDirt(x, y));
            }
            key = scan.next();
        }
        if(key.equals("fakeRightDirt:")) {
            Update.fakeGrassDirtRightWall.clear();
            while (scan.hasNextInt()) {
                int x = scan.nextInt();
                int y = scan.nextInt();
                Update.fakeGrassDirtRightWall.add(new FakeGrassDirtRightWall(x, y));
            }
            key = scan.next();
        }
        if(key.equals("score:")) {
            String a = scan.next();
            if(a.equals("tempScore")) {
                Update.initialScore = Update.score;
            } else if(a.equals("0")) {
                Update.score = 0;
            }
            key = scan.next();
        }
        if(key.equals("movingPlatform:")) {
            Update.movingPlatform.clear();
            while (scan.hasNext()) {
                int x = scan.nextInt();
                int y = scan.nextInt();
                int distance = scan.nextInt();
                key = scan.next();
                boolean horizontal = false;
                if (key.equals("true")) {
                    horizontal = true;
                }
                key = scan.next();
                boolean vertical = false;
                if (key.equals("true")) {
                    vertical = true;
                }
                int speed = scan.nextInt();
                String c = scan.next(); //"move"/"fall"
                String d = scan.next(); //"dirt"/"stone"
                scan.nextLine();
                Update.movingPlatform.add(new MovingPlatform(x, y, distance, horizontal, vertical, speed, c, d));
            }
            Update.movingPlatform.add(new MovingPlatform(0, 0, 0, false, false, 1, "move", "dirt"));
        }
        int numberOfClouds = Update.level.getWidth() / 20;
        Update.cloud.clear();
        int heightDifference = (int)((double)Update.level.getWidth() * 20 / 112);
        for (int i = 0; i < numberOfClouds; i++) {
            Random random = new Random();
            int height = random.nextInt(heightDifference);
            int x = ((Update.level.getWidth() * 16) / numberOfClouds) * i;
            int y = (int)((double)(Update.level.getHeight() * 16) * 30 / 56) + height;
            Update.cloud.add(new Cloud(x, y));
        }
        Update.background = new Background[Update.level.getWidth() * Update.level.getHeight()];
        Update.dayTime = !(Update.time.get(Calendar.HOUR_OF_DAY) >= Update.NIGHT_TIME || Update.time.get(Calendar.HOUR_OF_DAY) < 6);
        if (Update.dayTime) {
            for(int i = 0; i < Update.level.getHeight(); i++) {
                for(int j = 0; j < Update.level.getWidth(); j++) {
                    Update.background[j + i * Update.level.getWidth()] = new Background((j * 16) - ((Update.level.getWidth() / 4) * 16), (i * 16), Update.player.get(0).getXPos(), Update.player.get(0).getYPos(), daySky);
                }
            }
        } else if (!Update.dayTime) {
            for(int i = 0; i < Update.level.getHeight(); i++) {
                for(int j = 0; j < Update.level.getWidth(); j++) {
                    Update.background[j + i * Update.level.getWidth()] = new Background((j * 16) - ((Update.level.getWidth() / 4) * 16), (i * 16), Update.player.get(0).getXPos(), Update.player.get(0).getYPos(), nightSky);
                }
            }
        }
    }
    
}
    
    //IGNORE. I'll figure this out after IA. I was trying to have it so that the files are only read once and 
    //each variable will be stored into arrays when can be accessed by the Game class depending on what level
    //it currently is.
    /*
    private void loadLevels() {
        try {
            for(int m = 0; m < Game.numberOfLevels + 2; m++) {
                if(m == 0) {
                    level[m] = new Level("/resources/textures/Levels/Survival.png");
                    filePath = "LevelSurvival.txt";
                }
                else if(m == 1) {
                    level[m] = new Level("/resources/textures/Levels/Menu.png");
                    filePath = "LevelMenu.txt";
                }
                else if(m == 2) {
                    level[m] = new Level("/resources/textures/Levels/Intro.png");
                    filePath = "LevelStory.txt";
                }
                else if(m == 3) {
                    level[m] = new Level("/resources/textures/Levels/Level1.png");
                    filePath = "Level1.txt";
                }
                else if(m == 4) {
                    level[m] = new Level("/resources/textures/Levels/Level2.png");
                    filePath = "Level2.txt";
                }
                File file = new File(filePath);
                Scanner scan = new Scanner(file);
                String key = scan.next();
                if(key.equals("playerPos:")) {
                    int x = scan.nextInt();
                    int y = scan.nextInt();
                    scan.nextLine();
                    player[m] = new Player(x, y, Game.keyboard);
                    player[m].initialize(level[m]);
                    key = scan.next();
                }
                if(key.equals("joe:")) {
                    numberOfJoes[m] = scan.nextInt();
                    joe = new Joe[Game.numberOfLevels + 2][numberOfJoes[m]];
                    for(int i = 0; i < numberOfJoes[m]; i++) {
                        int x = scan.nextInt();
                        int y = scan.nextInt();
                        scan.nextLine();
                        joe[m][i] = new Joe(x, y);
                        if(m == 1) {
                            System.out.println("Level: " + m + ", " + joe[m][i].getXPos() + " " + joe[m][i].getYPos());
                        }
                        joe[m][i].initialize(level[m]);
                    }
                    key = scan.next();
                }
                if(key.equals("pedaller:")) {
                    numberOfPedallers[m] = scan.nextInt();
                    pedaller = new Pedaller[Game.numberOfLevels + 2][numberOfPedallers[m]];
                    for(int i = 0; i < numberOfPedallers[m]; i++) {
                        int x = scan.nextInt();
                        int y = scan.nextInt();
                        scan.nextLine();
                        pedaller[m][i] = new Pedaller(x, y);
                        pedaller[m][i].initialize(level[m]);
                    }
                    key = scan.next();
                }
                if(key.equals("cloud:")) {
                    numberOfClouds[m] = scan.nextInt();
                    cloud = new Cloud[Game.numberOfLevels + 2][numberOfClouds[m]];
                    int heightDifference = 26;
                    key = scan.next();
                    if(key.equals("height")) {
                        heightDifference = scan.nextInt();
                        key = scan.nextLine();
                    }
                    key = scan.next();
                    for(int i = 0; i < numberOfClouds[m]; i++) {
                        Random random = new Random();
                        int height = random.nextInt(heightDifference);
                        int x = ((level[m].getWidth() * 16) / numberOfClouds[m]) * i;
                        int y = scan.nextInt() + height;
                        scan.nextLine();
                        cloud[m][i] = new Cloud(x, y);
                    }
                    key = scan.next();
                }
                if(key.equals("flower:")) {
                    numberOfFlowers[m] = scan.nextInt();
                    flower = new Flower[Game.numberOfLevels + 2][numberOfFlowers[m]];
                    for(int i = 0; i < numberOfFlowers[m]; i++) {
                        int x = scan.nextInt();
                        int y = scan.nextInt();
                        scan.nextLine();
                        flower[m][i] = new Flower(x, y);
                    }
                    key = scan.next();
                }
                if(key.equals("coin:")) {
                    numberOfCoins[m] = scan.nextInt();
                    coin = new Coin[Game.numberOfLevels + 2][numberOfCoins[m]];
                    for(int i = 0; i < numberOfCoins[m]; i++) {
                        int x = scan.nextInt();
                        int y = scan.nextInt();
                        scan.nextLine();
                        coin[m][i] = new Coin(x, y);
                    }
                    key = scan.next();
                }
                if(key.equals("numberOfDirt:")) {
                    numberOfDirtColumn[m] = scan.nextInt();
                    numberOfDirtRow[m] = scan.nextInt();
                    fakeDirt = new FakeDirt[Game.numberOfLevels + 2][numberOfDirtColumn[m]][numberOfDirtRow[m]];
                    for(int i = 0; i < numberOfDirtColumn[m]; i++) {
                        for(int j = 0; j < numberOfDirtRow[m]; j++) {
                            fakeDirt[m][i][j] = new FakeDirt(592 + (16 * i), 240 + (16 * j));
                        }
                    }
                    key = scan.next();
                }
                if(key.equals("numberOfRightWalls:")) {
                    numberOfRightWalls[m] = scan.nextInt();
                    fakeGrassDirtRightWall = new FakeGrassDirtRightWall[Game.numberOfLevels + 2][numberOfRightWalls[m]];
                    for(int i = 0; i < numberOfRightWalls[m]; i++) {
                        fakeGrassDirtRightWall[m][i] = new FakeGrassDirtRightWall(624, 240 + (16 * i));
                    }
                    key = scan.next();
                }
                if(key.equals("score:")) {
                    String a = scan.next();
                    if(a.equals("initialScore")) {
                        Game.initialScore = Game.score;
                    }
                    else if(a.equals("0")) {
                        Game.score = 0;
                    }
                }
            }
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void loadLevel(int m) {
        Game.level = level[m];
        Game.player.setPos(player[m].getXPos(), player[m].getYPos());
        Game.player.initialize(level[m]);
        Game.numberOfJoes = numberOfJoes[m];
        Game.joe = new Joe[numberOfJoes[m]];
        for(int i = 0; i < numberOfJoes[m]; i++) {
            Game.joe[i] = new Joe(joe[m][i].getXPos(), joe[m][i].getYPos());
            Game.joe[i].initialize(level[m]);
        }
        Game.numberOfPedallers = numberOfPedallers[m];
        Game.pedaller = new Pedaller[numberOfPedallers[m]];
        for(int i = 0; i < numberOfPedallers[m]; i++) {
            Game.pedaller[i] = new Pedaller(pedaller[m][i].getXPos(), pedaller[m][i].getYPos());
            Game.pedaller[i].initialize(level[m]);
        }
        Game.numberOfClouds = numberOfClouds[m];
        Game.cloud = new Cloud[numberOfClouds[m]];
        for(int i = 0; i < Game.numberOfClouds; i++) {
            Game.cloud[i] = new Cloud(cloud[m][i].getXPos(), cloud[m][i].getYPos());
        }
        Game.numberOfFlowers = numberOfFlowers[m];
        Game.flower = new Flower[numberOfFlowers[m]];
        for(int i = 0; i < numberOfFlowers[m]; i++) {
            Game.flower[i] = new Flower(flower[m][i].getXPos(), flower[m][i].getYPos());
        }
        Game.numberOfCoins = numberOfCoins[m];
        Game.coin = new Coin[numberOfCoins[m]];
        for(int i = 0; i < numberOfCoins[m]; i++) {
            Game.coin[i] = new Coin(coin[m][i].getXPos(), coin[m][i].getYPos());
        }
        Game.numberOfDirtColumn = numberOfDirtColumn[m];
        Game.numberOfDirtRow = numberOfDirtRow[m];
        Game.fakeDirt = new FakeDirt[numberOfDirtColumn[m]][numberOfDirtRow[m]];
        for(int i = 0; i < numberOfDirtColumn[m]; i++) {
            for(int j = 0; j < numberOfDirtRow[m]; j++) {
                Game.fakeDirt[i][j] = new FakeDirt(fakeDirt[m][i][j].getXPos(), fakeDirt[m][i][j].getYPos());
            }
        }
        Game.numberOfRightWalls = numberOfRightWalls[m];
        Game.fakeGrassDirtRightWall = new FakeGrassDirtRightWall[numberOfRightWalls[m]];
        for(int i = 0; i < numberOfRightWalls[m]; i++) {
            Game.fakeGrassDirtRightWall[i] = new FakeGrassDirtRightWall(fakeGrassDirtRightWall[i].getXPos(), fakeGrassDirtRightWall[i].getYPos());
        }
        Game.background = new Background[Game.level.getHeight()][Game.level.getWidth()];
        for(int i = 0; i < Game.level.getHeight(); i++) {
            for(int j = 0; j < Game.level.getWidth(); j++) {
                Game.background[i][j] = new Background(Game.player.getXPos() - (Game.level.getWidth() * 16) + (32 * j), 
                                                  Game.player.getYPos() - (Game.level.getHeight() * 16) + (32 * i), sky);
            }
        }
        if(m == 0) {
            survivalLevelAttributes();
        }
        else if(m == 1) {
            menuLevelAttributes();
        }
        else if(m == 2) {
            storyLevelAttributes();
        }
        else if(m == 3) {
            firstLevelAttributes();
        }
        else if(m == 4) {
            secondLevelAttributes();
        }
    }

        
        
        Game.level = level[m];
        Game.player.setPos(player[m].getXPos(), player[m].getYPos());
        Game.player.initialize(level[m]);
        Game.numberOfJoes = numberOfJoes[m];
        Game.joe = new Joe[numberOfJoes[m]];
        for(int i = 0; i < numberOfJoes[m]; i++) {
            Game.joe[i] = new Joe(0,0);
            if(numberOfJoes[m] != 0) {
                System.out.println(numberOfJoes[m]);
                System.out.println(joe[m][i].getXPos());
                Game.joe[i].setPos(joe[m][i].getXPos(), joe[m][i].getYPos());
                Game.joe[i].initialize(level[m]);
            }
        }
        Game.numberOfPedallers = numberOfPedallers[m];
        Game.pedaller = new Pedaller[numberOfPedallers[m]];
        for(int i = 0; i < numberOfPedallers[m]; i++) {
            Game.pedaller[i] = new Pedaller(0, 0);
            Game.pedaller[i] = pedaller[m][i];
            Game.pedaller[i].initialize(level[m]);
        }
        Game.numberOfClouds = numberOfClouds[m];
        Game.cloud = new Cloud[numberOfClouds[m]];
        for(int i = 0; i < numberOfClouds[m]; i++) {
            Game.cloud[i] = new Cloud(0, 0);
            Game.cloud[i] = cloud[m][i];
        }
        Game.numberOfFlowers = numberOfFlowers[m];
        Game.flower = new Flower[numberOfFlowers[m]];
        for(int i = 0; i < numberOfFlowers[m]; i++) {
            Game.flower[i] = new Flower(0, 0);
            Game.flower[i] = flower[m][i];
        }
        Game.numberOfCoins = numberOfCoins[m];
        Game.coin = new Coin[numberOfCoins[m]];
        for(int i = 0; i < numberOfCoins[m]; i++) {
            Game.coin[i] = new Coin(0, 0);
            Game.coin[i] = coin[m][i];
        }
        Game.numberOfDirtColumn = numberOfDirtColumn[m];
        Game.numberOfDirtRow = numberOfDirtRow[m];
        Game.fakeDirt = new FakeDirt[numberOfDirtColumn[m]][numberOfDirtRow[m]];
        for(int i = 0; i < numberOfDirtColumn[m]; i++) {
            for(int j = 0; j < numberOfDirtRow[m]; j++) {
                Game.fakeDirt[i][i] = new FakeDirt(0, 0);
                Game.fakeDirt[i][j] = fakeDirt[m][i][j];
            }
        }
        Game.numberOfRightWalls = numberOfRightWalls[m];
        Game.fakeGrassDirtRightWall = new FakeGrassDirtRightWall[numberOfRightWalls[m]];
        for(int i = 0; i < numberOfRightWalls[m]; i++) {
            Game.fakeGrassDirtRightWall[i] = new FakeGrassDirtRightWall(0, 0);
            Game.fakeGrassDirtRightWall[i] = fakeGrassDirtRightWall[m][i];
        }
        Game.background = new Background[level[m].getHeight()][level[m].getWidth()];
        for(int i = 0; i < level[m].getHeight(); i++) {
            for(int j = 0; j < level[m].getWidth(); j++) {
                Game.background[i][j] = new Background(player[m].getXPos() - (level[m].getWidth() * 16) + (32 * j), 
                                                          player[m].getYPos() - (level[m].getHeight() * 16) + (32 * i), sky);
            }
        }
        for(int i = 0; i < 4; i++) {
            Game.heart[i] = new Heart(0, 0);
        }
        
        if(m == 0) { //Survival
            survivalLevelAttributes();
        }
        else if(m == 1) { //Menu
            menuLevelAttributes();
        }
        else if(m == 2) { //Story
            storyLevelAttributes();
        }
        else if(m == 3) { //Level1
            firstLevelAttributes();
        }
        else if(m == 4) { //Level2
            secondLevelAttributes();
        }

        Game.levelLoaded = true;
        */
    
    
