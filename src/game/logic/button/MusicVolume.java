package game.logic.button;

import game.SoundEngine;

public class MusicVolume extends Button {

    public MusicVolume(String information, int type) {
        super(information, type);
    }

    @Override
    public void update() {
        super.update();
        if (action != -1) {
            if (SoundEngine.isMusicPlaying()) {
                switch (action) {
                    case 0: //-
                        SoundEngine.setMusicVolume(SoundEngine.getMusicVolume() - 10.0f);
                        //game.Game.volume -= 10.0f;
                        // (game.Game.volume < -80.0f) game.Game.volume = -80.0f;
                        break;
                    case 1: //+
                        SoundEngine.setMusicVolume(SoundEngine.getMusicVolume() + 10.0f);
                        //game.Game.volume += 10.0f;
                        //if (game.Game.volume > 0) game.Game.volume = 0;
                        break;
                }
                //This part is done so there is no delay in changing volume.
            }
            action = -1;
            
        }
    }
    
}
