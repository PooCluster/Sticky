package game.logic.button;

import java.util.Scanner;

public class Save extends Button {
    
    private int gameSlot;
    
    public Save(String information, int type) {
        super(information, type);
        Scanner scan = new Scanner(information);
        for (int i = 0; i < 3; i++) scan.nextInt();
        switch (scan.next()) {
            case "[SAVE1]":
                gameSlot = 0;
                break;
            case "[SAVE2]":
                gameSlot = 1;
                break;
            case "[SAVE3]":
                gameSlot = 2;
                break;
        }
        scan = new Scanner(game.StickyData.names[gameSlot]);
        String name = "[";
        while (scan.hasNextInt()) name += (char)scan.nextInt();
        name += "]";
        createText(name);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            game.Update.gameSlot = gameSlot;
            if (game.Update.currentName.equals("78 69 87")) { //The player is new.
                if (game.Update.currentName.equals(game.StickyData.names[gameSlot])) { //If both are new.
                    game.logic.GameStateManager.menu.pushLayer(4);
                } else {
                    game.logic.GameStateManager.menu.pushLayer(target); //Check for override.
                }
            } else if (!game.StickyData.names[gameSlot].equals(game.Update.currentName) && !game.StickyData.names[gameSlot].equals("78 69 87")) { //Aj vs. Ja and the save is not new.
                game.logic.GameStateManager.menu.pushLayer(target); //Check for override.
            } else { //Otherwise, just save.
                game.StickyData.names[gameSlot] = game.Update.currentName;
                game.StickyData.currentLevel[gameSlot] = game.Update.currentLevel;
                game.StickyData.initialHealth[gameSlot][0] = game.Update.initialHealth[0];
                game.StickyData.initialHealth[gameSlot][1] = game.Update.initialHealth[1];
                game.StickyData.score[gameSlot] = game.Update.score;
                if (game.Update.levelsCompleted.isEmpty()) {
                    game.StickyData.levels[gameSlot] = "-1";
                } else {
                    game.StickyData.levels[gameSlot] = game.Update.levelsCompleted.toString().substring(1, game.Update.levelsCompleted.toString().length() - 1).replaceAll(",", "");
                }
                
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 2; j++) {
                        game.StickyData.controls[game.Update.gameSlot][i][j] = game.input.Keyboard.controls[i][j];
                    }
                }
                game.StickyData.save();
                game.logic.GameStateManager.menu = null;
            }
            
            
            /*
            
            if (!game.StickyData.names[gameSlot].equals("78 69 87")) {
                if (!game.StickyData.names[gameSlot].equals(game.Update.currentName)) {
                    game.logic.GameStateManager.menu.pushLayer(target); //Check for override.
                } else {
                    //Save the ish.
                    game.StickyData.names[game.Update.gameSlot] = game.Update.currentName;
                    game.StickyData.currentLevel[game.Update.gameSlot] = game.Update.currentLevel;
                    game.StickyData.initialHealth[game.Update.gameSlot][0] = game.Update.initialHealth[0];
                    game.StickyData.initialHealth[game.Update.gameSlot][1] = game.Update.initialHealth[1];
                    game.StickyData.score[game.Update.gameSlot] = game.Update.score;
                    game.StickyData.levels[game.Update.gameSlot] = game.Update.levelsCompleted.toString().substring(1, game.Update.levelsCompleted.toString().length() - 1).replaceAll(",", "");
                    for (int i = 0; i < 6; i++) {
                        for (int j = 0; j < 2; j++) {
                            game.StickyData.controls[game.Update.gameSlot][i][j] = game.input.Keyboard.controls[i][j];
                        }
                    }
                    game.StickyData.save();
                    game.logic.GameStateManager.menu = null;
                }
            } else {
                game.logic.GameStateManager.menu.pushLayer(4);
            }
            */
        }
    }
    
}
