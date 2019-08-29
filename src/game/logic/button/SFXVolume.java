package game.logic.button;

import game.SoundEngine;

public class SFXVolume extends Button {
    
    public SFXVolume(String information, int type) {
        super(information, type);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            if (SoundEngine.isSFXPlaying()) {
                switch (action) {
                    case 0: //-
                        SoundEngine.setSFXVolume(SoundEngine.getSFXVolume() - 10.0f);
                        //game.Game.sfxVolume -= 10.0f;
                        //if (game.Game.sfxVolume < -80.0f) game.Game.sfxVolume = -80.0f;
                        break;
                    case 1: //+
                        SoundEngine.setSFXVolume(SoundEngine.getSFXVolume() + 10.0f);
                        //game.Game.sfxVolume += 10.0f;
                        //if (game.Game.sfxVolume > 0) game.Game.sfxVolume = 0;
                        break;
                }
            }            
            action = -1;
        }
    }
    
}
