package game.logic.button;

import game.LoadLevel;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Restart extends Button {
    
    public Restart(String information, int type) {
        super(information, type);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            for (int i = 0; i < game.Update.player.size(); i++) game.Update.player.get(i).setHealth(game.Update.initialHealth[i]);
            game.Update.score = game.Update.initialScore;
            try {
                //game.Update.locations = new LoadLevel(game.Update.currentLevel);
                LoadLevel.loadLevel(game.Update.currentLevel);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
            game.logic.GameStateManager.menu = null;
        }
    }
    
}