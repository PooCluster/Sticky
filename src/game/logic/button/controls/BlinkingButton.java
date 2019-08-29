package game.logic.button.controls;

import game.Render;
import game.logic.button.Button;

public class BlinkingButton extends Button {
    
    protected int counter;
    
    public BlinkingButton(String information, int type) {
        super(information, type);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            counter++;
            if (counter >= 120) counter = 0;
        } else { counter = 0; }
        //Action will be dealt with by sub classes.
    }
    
    @Override
    public void render() {
        for (int i = 0; i < descriptor.size(); i++) descriptor.get(i).render();
        for (int i = 0; i < commands.size(); i++) {
            for (int j = 0; j < commands.get(i).size(); j++) {
                if (i != action) { //Not the certain phrase that needs to blink.
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
