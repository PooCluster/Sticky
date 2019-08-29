package game.logic.button;

public class SingleMultiPlayer extends Button {
    
    public SingleMultiPlayer(String information, int type) {
        super(information, type);
        if (game.Update.player.size() == 1) {
            createText("[SINGLEPLAYER]");
        } else {
            createText("[MULTIPLAYER]");
        }
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            if (game.Update.player.size() == 1) {
                createText("[MULTIPLAYER]");
                game.Update.player.add(new game.entity.Player(game.Update.player.get(0).getXPos(), game.Update.player.get(0).getYPos(), 1));
                game.Update.player.get(1).setHealth(game.Update.initialHealth[1]);
            } else {
                createText("[SINGLEPLAYER]");
                game.Update.player.remove(1);
            }
            action = -1;
        }
    }
    
}
