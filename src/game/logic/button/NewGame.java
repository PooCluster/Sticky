package game.logic.button;

public class NewGame extends Button {
    
    public NewGame(String information, int type) {
        super(information, type);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            game.logic.GameStateManager.state.pop();
            game.logic.GameStateManager.menu = null;
            for (int i = 0; i < game.Update.player.size(); i++) game.Update.player.get(i).setHealth(4);
            for (int i = 0; i < 2; i++) game.Update.initialHealth[i] = 4;
            game.Update.currentName = "78 69 87";
            game.Update.gameSlot = -1;
            game.Update.currentLevel = 1;
            game.Update.levelsCompleted.clear();
            game.Update.score = 0;
            game.Update.initialScore = 0;
            game.Update.loadMap = new game.LoadMap(1);
        }
    }
    
}