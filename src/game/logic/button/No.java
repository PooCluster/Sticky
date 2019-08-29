package game.logic.button;

public class No extends Button {
    
    public No(String information, int type) {
        super(information, type);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            game.logic.GameStateManager.menu.removeLayer();
        }
    }
    
}
