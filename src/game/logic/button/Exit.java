package game.logic.button;

import game.LoadLevel;
import game.logic.GameStateManager;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Exit extends Button {
    
    public Exit(String information, int type) {
        super(information, type);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            switch (type) {
                case 0:
                    game.Update.score = game.Update.initialScore;
                    game.Update.player.get(0).setHealth(game.Update.initialHealth[0]);
                    if (game.Update.player.size() == 2) game.Update.player.get(1).setHealth(game.Update.initialHealth[1]);
                    game.Update.loadMap = new game.LoadMap(1);
                    GameStateManager.state.push(GameStateManager.state.peek() + 1);
                    break;
                case 1:
                    game.Update.currentLevel = 0;
                    try {
                        //game.Update.locations = new LoadLevel(game.Update.currentLevel);
                        LoadLevel.loadLevel(game.Update.currentLevel);
                    } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                        e.printStackTrace();
                    }
                    GameStateManager.state.push(GameStateManager.state.peek() + 1);
                    break;
                case 3:
                    GameStateManager.state.pop();
                    break;
            }
            GameStateManager.menu = null;
        }
    }
    
}
