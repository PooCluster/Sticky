package game.logic.button;

public class Quit extends Button {
    
    public Quit(String information, int type) {
        super(information, type);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            game.Main.running = false;
        }
    }
    
}
