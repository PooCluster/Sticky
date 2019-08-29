package game.logic.button.controls;

import java.awt.event.KeyEvent;

public class Name extends BlinkingButton {
    
    private String name = "[NAME]";
    
    public Name(String information, int type) {
        super(information, type);
        createText(name);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            counter++;
            if (counter >= 120) counter = 0;
            if (((game.input.Keyboard.currentKey >= KeyEvent.VK_A && game.input.Keyboard.currentKey <= KeyEvent.VK_Z) || game.input.Keyboard.currentKey == KeyEvent.VK_SPACE) && game.input.Keyboard.currentKey != -1) {
                if (name.equals("[NAME]")) {
                    name = "[" + (char)game.input.Keyboard.currentKey + "]";
                } else {
                    name = name.substring(0, name.length() - 1) + (char)game.input.Keyboard.currentKey + "]";
                }
                createText(name);
                game.input.Keyboard.currentKey = -1;
            }
            if (game.input.Keyboard.currentKey == KeyEvent.VK_BACK_SPACE && name.length() > 2 && !name.equals("[NAME]") && game.input.Keyboard.currentKey != -1) {
                name = name.substring(0, name.length() - 2) + "]";
                createText(name);
                game.input.Keyboard.currentKey = -1;
            }
            if (name.length() == 2) {
                createText(name = "[NAME]");
            }
            if (game.input.Keyboard.currentKey == KeyEvent.VK_ENTER) {
                name = name.substring(1, name.length() - 1);
                String newName = "";
                for (int i = 0; i < name.length(); i++) newName += (int)name.charAt(i) + " ";
                name = name.substring(0, name.length() - 1); //Takes out extra space. No extras, pls.
                game.StickyData.names[game.Update.gameSlot] = game.Update.currentName = newName;
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
                        game.StickyData.controls[game.Update.gameSlot][i][j] = game.input.Keyboard.controls[i][j];
                    }
                }
                game.StickyData.save();
                while (game.logic.GameStateManager.menu.history.peek() != 2) game.logic.GameStateManager.menu.removeLayer();
            }
        } else { counter = 0; }
    }
    
    @Override
    public void render() {
        for (int i = 0; i < descriptor.size(); i++) descriptor.get(i).render();
        for (int i = 0; i < commands.size(); i++) {
            for (int j = 0; j < commands.get(i).size(); j++) {
                if ((i != action || j < commands.get(i).size() - 1) && !name.equals("[NAME]")) { //Not the certain phrase that needs to blink.
                    commands.get(i).get(j).render();
                } else if (counter <= 60) {
                    commands.get(i).get(j).render();
                }
            }
        }
        if (createOutline != -1 && !mouseInitial) {
            for (int i = 0; i < outlines.get(createOutline)[0].size(); i++) outlines.get(createOutline)[0].get(i).render();
        } else if (saveIndex != -1 && mouseInitial) { //When mouse clicked.
            for (int i = 0; i < outlines.get(createOutline)[1].size(); i++) outlines.get(createOutline)[1].get(i).render();
        }
    }
    
}
