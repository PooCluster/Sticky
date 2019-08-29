package game.logic.button;

import game.SoundEngine;

public class SFXOnOff extends Button {
    
    public SFXOnOff(String information, int type) {
        super(information, type);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            switch (action) {
                case 0: //ON
                    if (!SoundEngine.isSFXPlaying()) SoundEngine.sfxToggle(true);
                    break;
                case 1: //OFF
                    if (SoundEngine.isSFXPlaying()) SoundEngine.sfxToggle(false);
                    break;
            }
            action = -1;
        }
    }
    
}
