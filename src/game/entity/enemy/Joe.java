package game.entity.enemy;

import game.Main;
import game.Render;
import game.SoundEngine;
import game.Sprite;
import static game.Sprite.joeDeath;
import static game.Sprite.joeDown;
import static game.Sprite.joeDownLeft;
import static game.Sprite.joeUp;
import static game.Sprite.joeUpLeft;
import game.Textures;
import static game.Update.DISTANCE_LIMIT;
import static game.Update.distance;
import static game.Update.xCamera;
import static game.Update.xCamera2;
import static game.Update.yCamera;
import static game.Update.yCamera2;
import game.entity.Mob;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Joe extends Mob {

    private int jumpingAnimation = 0;
    private int fallingAnimation = 0;
    private int animation = 0;
    private int counter = 0;
    private int dyingCounter = 0;
    private int blinkCounter = 0;
    private int blinkCounterAnimation = 0;
    
    private double jumpSpeed = 4;
    private double currentJumpSpeed = jumpSpeed;
    private double maxFallSpeed = 5;
    private double currentFallSpeed = 3;
    
    private boolean left = false;
    
    private int health = 2;
    private boolean hit = false;
    private boolean dying = false;
    private boolean death = false;
    private boolean deathHasPlayed = false;
    
    private boolean canJump = true;
    
    private Clip[] clip = new Clip[7];
    private AudioInputStream[] input = new AudioInputStream[7];
    private FloatControl[] gainControl = new FloatControl[7];
    
    public Joe(int x, int y) throws LineUnavailableException, UnsupportedAudioFileException, IOException {        
        this.x = x;
        this.y = y;
        this.ID = ID;
        for(int i = 0; i < 5; i++) {
            joeUp[i] =  new Sprite(16, 13, 5 + i, Textures.mobs);
        }
        for(int i = 0; i < 2; i++) {
            joeDown[i] =  new Sprite(16, 13, 10 + i, Textures.mobs);
        }
        for(int i = 0; i < 5; i++) {
            joeUpLeft[i] =  new Sprite(16, 12, 5 + i, Textures.mobs);
        }
        for(int i = 0; i < 2; i++) {
            joeDownLeft[i] =  new Sprite(16, 12, 10 + i, Textures.mobs);
        }
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 6; j++) {
                joeDeath[j + i * 6] = new Sprite(16, 10 + j, 12 + i, Textures.mobs);
            }
        }
        loadSounds();
        //SoundEngine.addJoe();
        /*
        
        */
    }
    
    private void loadSounds() {
        try {
            for(int i = 0; i < 6; i++) {
                clip[i] = AudioSystem.getClip();
                input[i] = AudioSystem.getAudioInputStream(Main.class.getResource("resources/audio/sfx/SlimeyJoe/Death" + (i + 1) + ".wav"));
                clip[i].open(input[i]);
                gainControl[i] = (FloatControl)clip[i].getControl(FloatControl.Type.MASTER_GAIN);
                gainControl[i].setValue(SoundEngine.getSFXVolume());
                clip[i].stop();
                clip[i].setFramePosition(0);
            }
            clip[6] = AudioSystem.getClip();
            input[6] = AudioSystem.getAudioInputStream(Main.class.getResource("resources/audio/sfx/SlimeyJoe/Jump.wav"));
            clip[6].open(input[6]);
            gainControl[6] = (FloatControl)clip[6].getControl(FloatControl.Type.MASTER_GAIN);
            gainControl[6].setValue(SoundEngine.getSFXVolume());
            clip[6].stop();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
    
    private void playSound(int i) {
        gainControl[i] = (FloatControl)clip[i].getControl(FloatControl.Type.MASTER_GAIN);
        gainControl[i].setValue(SoundEngine.getSFXVolume());
        clip[i].start();
    }
    
    private void resetSound(int i) {
        if (clip[i].getFramePosition() == clip[i].getFrameLength()) {
            clip[i].stop();
            clip[i].setFramePosition(0);
        }
    }
     
    public boolean isHit(int xBall, int yBall) {
        return hit = (this.x >= xBall - 12 && this.x <= xBall + 12) && (this.y > yBall - 12 && this.y < yBall + 12);
    }
    
    public boolean isDead(int i) {
        //if (death) SoundEngine.removeJoe(i);
        return death;
    }
    
    public boolean isDying() {
        return dying;
    }
    
    public void update(int i, boolean playerDead) {
        int x = 0;
        int y = 0;
        if (hit) {
            health--;
            hit = false;
            blinkCounter = 1;
            if(health < 0) {
                health = 0;
            }
        }
        if (animation != 60) {
            animation++;
        } else {
            animation = 0;
        }
        if (blinkCounter != 0) {
            blinkCounterAnimation++;
        } else {
            blinkCounterAnimation = 0;
        }
        if (blinkCounterAnimation == 60) {
            blinkCounter = 0;
        }
        if (dying && jumping) {
            falling = true;
            jumping = false;
        }
        if (canJump && !dying && !death) {
            jumping = true;
            if (Math.abs(distance) <= DISTANCE_LIMIT || playerDead) {
                if (this.x > xCamera - 216 && this.x < xCamera + 216 && this.y > yCamera - 150 && this.y < yCamera + 150 && SoundEngine.isSFXPlaying()) {
                    //SoundEngine.joeJump(i);
                    playSound(6);
                }
            } else if (distance > DISTANCE_LIMIT) {
                if (this.x > xCamera && this.x < xCamera + 216 && this.y > yCamera - 150 && this.y < yCamera + 150 && SoundEngine.isSFXPlaying()) {
                    //SoundEngine.joeJump(i);
                    playSound(6);
                }
                if (this.x > xCamera2 - 216 && this.x < xCamera2 && this.y > yCamera2 - 150 && this.y < yCamera2 + 150 && SoundEngine.isSFXPlaying()) {
                    //SoundEngine.joeJump(i);
                    playSound(6);
                }
            } else {
                if (this.x > xCamera - 216 && this.x < xCamera && this.y > yCamera - 150 && this.y < yCamera + 150 && SoundEngine.isSFXPlaying()) {
                    //SoundEngine.joeJump(i);
                    playSound(6);
                }
                if (this.x > xCamera2 && this.x < xCamera2 + 216 && this.y > yCamera2 - 150 && this.y < yCamera2 + 150 && SoundEngine.isSFXPlaying()) {
                    //SoundEngine.joeJump(i);
                    playSound(6);
                }
            }
        } else {
            resetSound(6);
        }
        
        /* else if (clip[6].getFramePosition() == clip[6].getFrameLength()) {
            clip[6].stop();
            clip[6].setFramePosition(0);
        }*/
        if (jumping && !dying) {
            canJump = false;
            y -= currentJumpSpeed;
            currentJumpSpeed -= 0.1;
            jumpingAnimation++;
            if (currentJumpSpeed <= 3) {
                currentJumpSpeed = jumpSpeed;
                jumping = false;
                falling = true;
            }
        }
        if (falling && !jumping) {
            fallingAnimation++;
            y += currentFallSpeed;
            if (currentFallSpeed < maxFallSpeed) {
                currentFallSpeed += 0.1;
            }
        }
        if (!falling && !jumping) {
            currentFallSpeed = 0.1;
            jumpingAnimation = 0;
            fallingAnimation = 0;
            canJump = true;
        }
        if (left && !dying) {
            if (counter % 2 == 0) {
                x--;
                counter++;
            } else {
                counter = 0;
            }
            
        }
        if (!left && !dying) {
            if (counter % 2 == 0) {
                x++;
                counter++;
            } else {
                counter = 0;
            }
        }
        
        if (collision(x, 0) && left) {
            left = false;
        } else if (collision(x, 0) && !left) {
            left = true;
        }
        move(x, y);
        if (health == 0) {
            dying = true;
            if (dyingCounter == 0 && SoundEngine.isSFXPlaying()) {
                //SoundEngine.joeDie(i);
                Random random = new Random();
                int a = random.nextInt(6);
                playSound(a);
            }
            dyingCounter++;
            if (!deathHasPlayed && SoundEngine.isSFXPlaying()) {
                deathHasPlayed = true;
                
                /*
                gainControl[a] = (FloatControl)clip[a].getControl(FloatControl.Type.MASTER_GAIN);
                gainControl[a].setValue(SoundEngine.getSFXVolume());
                clip[a].start();
                */
            }
            /*
            if(!deathHasPlayed && Game.sfxPlaying) {
                Random random = new Random();
                int a = random.nextInt(6);
                gainControl[a] = (FloatControl)clip[a].getControl(FloatControl.Type.MASTER_GAIN);
                gainControl[a].setValue(Game.sfxVolume);
                clip[a].start();
                deathHasPlayed = true;
            }
            */
        }
        if (dyingCounter >= 60) {
            dyingCounter = 0;
            dying = false;
            death = true;
        }
    }
    
    public void render(int xCamera, int yCamera) {
        if(!left && (jumping || canJump) && !dying) { //Jump-Right.
            jumpAnimation();
        } else if(falling && !left && !dying) { //Fall-Right.
            fallAnimation();
        } else if(left && (jumping || canJump) && !dying) { //Jump-Left
            jumpLeftAnimation();
        } else if(falling && left && !dying) { //Fall-Left.
            fallLeftAnimation();
        } else if(dying) {
            dyingAnimation();
        }
        if (blinkCounterAnimation % 4 == 0 && blinkCounter != 0 && !dying) {
            sprite = new Sprite(16, 0, 0, Textures.mobs);
        }
        if (x < xCamera + 250 && x > xCamera - 250 && y < yCamera + 150 && y > yCamera - 150) {
            Render.renderRegular(16, x - 16, y - 16, sprite);
        }
    }
    
    public void render(int xCamera, int yCamera, int xCamera2, int yCamera2, int distance) {
        if(!left && (jumping || canJump) && !dying) { //Jump-Right.
            jumpAnimation();
        } else if(falling && !left && !dying) { //Fall-Right.
            fallAnimation();
        } else if(left && (jumping || canJump) && !dying) { //Jump-Left
            jumpLeftAnimation();
        } else if(falling && left && !dying) { //Fall-Left.
            fallLeftAnimation();
        } else if(dying) {
            dyingAnimation();
        }
        if (blinkCounterAnimation % 4 == 0 && blinkCounter != 0 && !dying) {
            sprite = new Sprite(16, 0, 0, Textures.mobs);
        }
        if (x < xCamera + 250 && x > xCamera - 250 && y < yCamera + 150 && y > yCamera - 150) {
            Render.renderFirstCamera(distance < 0, 16, x - 16, y - 16, sprite);
        }
        if (x < xCamera2 + 250 && x > xCamera2 - 250 && y < yCamera2 + 150 && y > yCamera2 - 150) {
            Render.renderSecondCamera(distance > 0, 16, x - 16, y - 16, sprite);
        }
    }
    
    private void jumpAnimation() {
        for(int i = 0; i < 5; i++) {
            if(jumpingAnimation <= 2 + (2 * i)) {
                sprite = joeUp[i];
                break;
            }
        }
    }
    
    private void fallAnimation() {
        if(fallingAnimation <= 15) {
            sprite = joeDown[0];
        }
        else {
            sprite = joeDown[1];
        }
    }
    
    private void jumpLeftAnimation() {
        for(int i = 0; i < 5; i++) {
            if(jumpingAnimation <= 2 + (2 * i)) {
                sprite = joeUpLeft[i];
                break;
            }
        }
    }
    
    private void fallLeftAnimation() {
        if(fallingAnimation <= 15) {
            sprite = joeDownLeft[0];
        }
        else {
            sprite = joeDownLeft[1];
        }
    }
    
    private void dyingAnimation() {
        for(int i = 0; i < 18; i++) {
            if(dyingCounter <= 3 + (3 * i)) {
                sprite = joeDeath[i];
                break;
            }
        }
    }
    
}
