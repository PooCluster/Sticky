package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEngine {

    private static Clip[] buttonReferenceClip = new Clip[2];
    private static AudioInputStream[] buttonReferenceInput = new AudioInputStream[2];
    private static FloatControl[] buttonReferenceGainControl = new FloatControl[2];
    
    private static Clip[] joeReferenceClip = new Clip[7];
    private static AudioInputStream[] joeReferenceInput = new AudioInputStream[7];
    private static FloatControl[] joeReferenceGainControl = new FloatControl[7];
    
    public static void loadReferences() {
        try {
            //Ball doesn't need reference, since it's always the same amount.
            for (int i = 0; i < 2; i++) { //Do not load ball when ball object is created, because it would lag the game from loading the audio.
                for (int j = 0; j < 2; j++) {
                    BALL_CLIP[j][i] = AudioSystem.getClip();
                    BALL_INPUT[j][i] = AudioSystem.getAudioInputStream(Main.class.getResource("resources/audio/sfx/Ball/" + (i + 1) + ".wav")); //First number represents ball, second represents sound.
                    BALL_CLIP[j][i].open(BALL_INPUT[j][i]);
                    BALL_GAIN_CONTROL[j][i] = (FloatControl)BALL_CLIP[j][i].getControl(FloatControl.Type.MASTER_GAIN);
                    BALL_CLIP[j][i].stop();
                }
            }
            
            
            //Button Sounds
            for (int i = 0; i < buttonReferenceClip.length; i++) {
                buttonReferenceClip[i] = AudioSystem.getClip();
                buttonReferenceInput[i] = AudioSystem.getAudioInputStream(Main.class.getResource("resources/audio/sfx/Button/" + (i + 1) + ".wav"));
                buttonReferenceClip[i].open(buttonReferenceInput[i]);
                buttonReferenceGainControl[i] = (FloatControl)buttonReferenceClip[i].getControl(FloatControl.Type.MASTER_GAIN);
                buttonReferenceGainControl[i].setValue(sfxVolume);
                buttonReferenceClip[i].stop();
            }
            //Joe Sounds
            for (int i = 0; i < joeReferenceClip.length - 1; i++) {
                joeReferenceClip[i] = AudioSystem.getClip();
                joeReferenceInput[i] = AudioSystem.getAudioInputStream(Main.class.getResource("resources/audio/sfx/SlimeyJoe/Death" + (i + 1) + ".wav"));
                joeReferenceClip[i].open(joeReferenceInput[i]);
                joeReferenceGainControl[i] = (FloatControl)joeReferenceClip[i].getControl(FloatControl.Type.MASTER_GAIN);
                joeReferenceGainControl[i].setValue(sfxVolume);
                joeReferenceClip[i].stop();
            }
            joeReferenceClip[6] = AudioSystem.getClip();
            joeReferenceInput[6] = AudioSystem.getAudioInputStream(Main.class.getResource("resources/audio/sfx/SlimeyJoe/Jump.wav"));
            joeReferenceClip[6].open(joeReferenceInput[6]);
            joeReferenceGainControl[6] = (FloatControl)joeReferenceClip[6].getControl(FloatControl.Type.MASTER_GAIN);
            joeReferenceGainControl[6].setValue(sfxVolume);
            joeReferenceClip[6].stop();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }        
        
    }
    
    public static void clear() {
        JOE_CLIP.clear();
        JOE_INPUT.clear();
        JOE_GAIN_CONTROL.clear();
        if (musicClip != null) musicClip.stop();
    }
    
    
    private static final ArrayList<Clip[]> JOE_CLIP = new ArrayList<>();
    private static final ArrayList<AudioInputStream[]> JOE_INPUT = new ArrayList<>();
    private static final ArrayList<FloatControl[]> JOE_GAIN_CONTROL = new ArrayList<>();
    
    
    
    public static void joeJump(int i) {
        if (sfxPlaying) {
            JOE_CLIP.get(i)[1].stop();
            JOE_GAIN_CONTROL.get(i)[1] = (FloatControl)JOE_CLIP.get(i)[1].getControl(FloatControl.Type.MASTER_GAIN);
            JOE_GAIN_CONTROL.get(i)[1].setValue(sfxVolume);
            JOE_CLIP.get(i)[1].setFramePosition(0);
            JOE_CLIP.get(i)[1].start();
        }
    }
    
    public static void joeDie(int i) {
        if (!JOE_CLIP.get(i)[0].isActive() && sfxPlaying) {
            JOE_CLIP.get(i)[0].stop();
            JOE_GAIN_CONTROL.get(i)[0].setValue(sfxVolume);
            JOE_CLIP.get(i)[0].setFramePosition(0);
            JOE_CLIP.get(i)[0].start();
            JOE_CLIP.get(i)[0].loop(0);
        }
    }
    
    public static void removeJoe(int i) {
        JOE_CLIP.remove(i);
        JOE_INPUT.remove(i);
        JOE_GAIN_CONTROL.remove(i);
    }
    
    public static void addJoe() {
        JOE_CLIP.add(new Clip[2]); //3, just so the jump audio has two files to play from
        JOE_INPUT.add(new AudioInputStream[2]);
        JOE_GAIN_CONTROL.add(new FloatControl[2]);
        int death = (new Random()).nextInt(6);
        JOE_CLIP.get(JOE_CLIP.size() - 1)[0] = joeReferenceClip[death];
        JOE_INPUT.get(JOE_CLIP.size() - 1)[0] = joeReferenceInput[death];
        //joeClip.get(i)[0].open(joeInput.get(i)[0]);
        JOE_GAIN_CONTROL.get(JOE_CLIP.size() - 1)[0] = joeReferenceGainControl[death];
        JOE_GAIN_CONTROL.get(JOE_CLIP.size() - 1)[0].setValue(sfxVolume);
        JOE_CLIP.get(JOE_CLIP.size() - 1)[0].stop();
        JOE_CLIP.get(JOE_CLIP.size() - 1)[1] = joeReferenceClip[6];
        JOE_INPUT.get(JOE_CLIP.size() - 1)[1] = joeReferenceInput[6];
        //joeClip.get(i)[1].open(joeInput.get(i)[1]);
        JOE_GAIN_CONTROL.get(JOE_CLIP.size() - 1)[1] = joeReferenceGainControl[6];
        JOE_GAIN_CONTROL.get(JOE_CLIP.size() - 1)[1].setValue(sfxVolume);
        JOE_CLIP.get(JOE_CLIP.size() - 1)[1].stop();
    }
    
    //General
    private static float musicVolume = 0;
    private static float sfxVolume = 0;
    
    private static boolean musicPlaying = true;
    private static boolean sfxPlaying = true;
    
    public static float getMusicVolume() {
        return musicVolume;
    }
    
    public static float getSFXVolume() {
        return sfxVolume;
    }
    
    public static boolean isMusicPlaying() {
        return musicPlaying;
    }
    
    public static boolean isSFXPlaying() {
        return sfxPlaying;
    }
    
    public static void setMusicVolume(float volume) {
        musicVolume = volume;
        if (musicVolume < -80.0f) musicVolume = -80.0f;
        if (musicVolume > 0) musicVolume = 0;
        musicFrame = musicClip.getFramePosition();
        musicClip.stop();
        musicGainControl.setValue(musicVolume);
        musicClip.setFramePosition(musicFrame);
        musicClip.start();
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public static void setSFXVolume(float volume) {
        sfxVolume = volume;
        if (sfxVolume < -80.0f) sfxVolume = -80.0f;
        if (sfxVolume > 0) sfxVolume = 0;
    }
    
    public static void playMusic() {
        musicPlaying = true;
        musicGainControl.setValue(musicVolume);
        musicClip.setFramePosition(musicFrame);
        musicClip.start();
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public static void pauseMusic() {
        musicPlaying = false;
        musicFrame = musicClip.getFramePosition();
        musicClip.stop();
    }
    
    public static void loadMusic(String songPath) {
        try {
            musicClip = AudioSystem.getClip();
            musicInput = AudioSystem.getAudioInputStream(Main.class.getResource(songPath));
            musicClip.open(musicInput);
            musicGainControl = (FloatControl)musicClip.getControl(FloatControl.Type.MASTER_GAIN);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            setMusicVolume(musicVolume);
            if (!musicPlaying) {
                musicClip.getFramePosition();
                musicClip.stop();
            }
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
    
    public static void sfxToggle(boolean on) {
        sfxPlaying = on;
    }
    
    private static Clip musicClip;
    private static AudioInputStream musicInput;
    private static FloatControl musicGainControl;
    private static int musicFrame;
    
    //Button
    public static void startClick1() {
        if (sfxPlaying) {
            buttonReferenceGainControl[0].setValue(sfxVolume);
            buttonReferenceClip[0].stop();
            buttonReferenceClip[0].setFramePosition(0);
            buttonReferenceClip[0].start();
            buttonReferenceClip[0].loop(0);
        }
    }
    
    public static void startClick2() {
        if (sfxPlaying) {
            buttonReferenceGainControl[1].setValue(sfxVolume);
            buttonReferenceClip[1].stop();
            buttonReferenceClip[1].setFramePosition(0);
            buttonReferenceClip[1].start();
            buttonReferenceClip[1].loop(0);
        }
    }
    
    //Ball
    private static final Clip[][] BALL_CLIP = new Clip[2][2];
    private static final AudioInputStream[][] BALL_INPUT = new AudioInputStream[2][2];
    private static final FloatControl[][] BALL_GAIN_CONTROL = new FloatControl[2][2];
    
    public static void startBall1(int i) { //i is which player
        if (sfxPlaying) {
            BALL_CLIP[i][1].stop(); //Just in case.
            BALL_GAIN_CONTROL[i][0].setValue(sfxVolume);
            BALL_CLIP[i][0].setFramePosition(0);
            
            
            BALL_CLIP[i][0].start();
            BALL_CLIP[i][0].loop(0);
        }
        
    }
    
    public static void startBall2(int i) {
        if (!BALL_CLIP[i][1].isActive() && sfxPlaying) {
            BALL_GAIN_CONTROL[i][1].setValue(sfxVolume);
            BALL_CLIP[i][1].setFramePosition(0);
            BALL_CLIP[i][1].start();
            BALL_CLIP[i][1].loop(0);
        }
    }
    
    public static void stopBall1(int i) {
        BALL_CLIP[i][0].stop();
    }
    
    public static void stopBall2(int i) {
        BALL_CLIP[i][1].stop();
    }
    
}
