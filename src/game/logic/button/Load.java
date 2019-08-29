package game.logic.button;

import game.LoadMap;
import java.util.Scanner;

public class Load extends Button {
    
    private int gameSlot = 0;
    
    public Load(String information, int type) {
        super(information, type);
        Scanner scan = new Scanner(information);
        for (int i = 0; i < 3; i++) scan.nextInt();
        switch (scan.next()) {
            case "[LOAD1]":
                gameSlot = 0;
                break;
            case "[LOAD2]":
                gameSlot = 1;
                break;
            case "[LOAD3]":
                gameSlot = 2;
                break;
        }
        scan = new Scanner(game.Update.currentName = game.StickyData.names[gameSlot]);
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
            game.Update.currentName = game.StickyData.names[game.Update.gameSlot];
            game.Update.levelsCompleted.clear();
            game.Update.player.get(0).setHealth(game.Update.initialHealth[0] = game.StickyData.initialHealth[game.Update.gameSlot][0]);
            game.Update.initialHealth[1] = game.StickyData.initialHealth[game.Update.gameSlot][1];
            game.Update.initialScore = game.Update.score = game.StickyData.score[game.Update.gameSlot];
            game.Update.currentLevel = game.StickyData.currentLevel[game.Update.gameSlot];
            for (int i = 0; i < 2; i++) {
                game.input.Keyboard.controls[0][i] = game.StickyData.controls[game.Update.gameSlot][0][i];
                game.input.Keyboard.controls[1][i] = game.StickyData.controls[game.Update.gameSlot][1][i];
                game.input.Keyboard.controls[2][i] = game.StickyData.controls[game.Update.gameSlot][2][i];
                game.input.Keyboard.controls[3][i] = game.StickyData.controls[game.Update.gameSlot][3][i];
                game.input.Keyboard.controls[4][i] = game.StickyData.controls[game.Update.gameSlot][4][i];
                game.input.Keyboard.controls[5][i] = game.StickyData.controls[game.Update.gameSlot][5][i];
            }
            String levels = game.StickyData.levels[game.Update.gameSlot];
            Scanner scan = new Scanner(levels);
            if (!levels.equals("-1")) while (scan.hasNextInt()) game.Update.levelsCompleted.add(scan.nextInt());
            game.Update.loadMap = new LoadMap(1);
            game.logic.GameStateManager.state.pop();
            game.logic.GameStateManager.menu = null;
        }
    }
    
}
