package game.logic.button;

public class Continue extends Button {

    public Continue(String information, int type) {
        super(information, type);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            game.logic.GameStateManager.menu = null;
        }
    }
    
}
