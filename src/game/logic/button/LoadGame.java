package game.logic.button;

public class LoadGame extends Button {
    
    public LoadGame(String information, int type) {
        super(information, type);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            /*
            game.StickyData.load();
            game.logic.GameStateManager.state.pop();
            game.logic.GameStateManager.menu = null;
            */
            game.StickyData.load();
            game.logic.GameStateManager.menu.pushLayer(target);
        }
    }
    
}
