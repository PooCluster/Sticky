package game.logic.button;

import game.LoadLevel;
import game.Update;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PressEnterToSelect extends Button {
    
    public PressEnterToSelect(String information, int type) {
        super(information, type);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            if (Update.mapPlayer.isOnLevel()) {
                Update.currentLevel = Update.map.whichLevel();
                try {
                    //Update.locations = new game.LoadLevel(Update.currentLevel);
                    LoadLevel.loadLevel(Update.currentLevel);
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                    e.printStackTrace();
                }
                game.logic.GameStateManager.state.pop();
                game.logic.GameStateManager.menu = null;
            }
        }
    }
    
}
