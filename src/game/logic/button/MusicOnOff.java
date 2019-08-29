package game.logic.button;

import game.SoundEngine;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicOnOff extends Button {

    public MusicOnOff(String information, int type) {
        super(information, type);
    }

    @Override
    public void update() {
        super.update();
        if (action != -1) {
            switch (action) {
                case 0: //ON
                    if (!SoundEngine.isMusicPlaying()) {
                        SoundEngine.playMusic();
                        /*
                        try {
                            game.Game.clip = AudioSystem.getClip();
                            game.Game.input = AudioSystem.getAudioInputStream(game.Game.class.getResource("resources/audio/music/Song3.wav"));
                            game.Game.clip.open(game.Game.input);
                            game.Game.clip.setFramePosition(game.Game.framePosition);
                            game.Game.gainControl = (FloatControl)game.Game.clip.getControl(FloatControl.Type.MASTER_GAIN);
                            game.Game.clip.loop(Clip.LOOP_CONTINUOUSLY);
                            game.Game.musicPlaying = true;
                        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                            e.printStackTrace();
                        }
                        */
                    }
                    break;
                case 1: //OFF
                    if (SoundEngine.isMusicPlaying()) {
                        SoundEngine.pauseMusic();
                        /*
                        game.Game.framePosition = game.Game.clip.getFramePosition();
                        game.Game.clip.stop();
                        game.Game.musicPlaying = false;
                        */
                    }
                    break;
            }
            action = -1;
        }
    }
    
}
