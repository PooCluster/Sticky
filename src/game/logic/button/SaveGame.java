package game.logic.button;

public class SaveGame extends Button {

    public SaveGame(String information, int type) {
        super(information, type);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            game.StickyData.load();
            game.logic.GameStateManager.menu.pushLayer(target);
        }
    }
    
}
