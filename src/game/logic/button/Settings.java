package game.logic.button;


public class Settings extends Button {

    public Settings(String information, int type) {
        super(information, type);
    }

    @Override
    public void update() {
        super.update();
        if (action != -1) {
            game.logic.GameStateManager.menu.pushLayer(target);
        }
    }
    
}
