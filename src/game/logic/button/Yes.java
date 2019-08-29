package game.logic.button;

import game.input.Keyboard;

public class Yes extends Button {
    
    public Yes(String information, int type) {
        super(information, type);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            if (game.Update.currentName.equals("78 69 87")) { //If the name is NEW, then pull up naming service.
                game.logic.GameStateManager.menu.pushLayer(target);
            } else {
                //Override data.
                game.StickyData.names[game.Update.gameSlot] = game.Update.currentName;
                game.StickyData.currentLevel[game.Update.gameSlot] = game.Update.currentLevel;
                game.StickyData.initialHealth[game.Update.gameSlot][0] = game.Update.initialHealth[0];
                game.StickyData.initialHealth[game.Update.gameSlot][1] = game.Update.initialHealth[1];
                game.StickyData.score[game.Update.gameSlot] = game.Update.score;
                if (!game.Update.levelsCompleted.isEmpty()) {
                    game.StickyData.levels[game.Update.gameSlot] = game.Update.levelsCompleted.toString().substring(1, game.Update.levelsCompleted.toString().length() - 1).replaceAll(",", "");
                } else {
                    game.StickyData.levels[game.Update.gameSlot] = "-1";
                }
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 2; j++) {
                        game.StickyData.controls[game.Update.gameSlot][i][j] = Keyboard.controls[i][j];
                    }
                }
                game.StickyData.save();
                game.logic.GameStateManager.menu = null;
            }
            
        }
    }
    
}
