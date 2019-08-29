package game.logic.button;

public class PlayerTwo extends Button {
    
    public PlayerTwo(String information, int type) {
        super(information, type);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            game.logic.GameStateManager.menu.history.pop();
            game.logic.GameStateManager.menu.pushLayer(target);
        }
    }
    
}
