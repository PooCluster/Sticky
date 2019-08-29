package game.logic.button.controls;

import game.StickyData;
import game.input.Keyboard;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class ChangeControls extends BlinkingButton {
    
    private String information;
    private int control;
    private int player;
    
    public ChangeControls(String information, int type, int control, int player) {
        super(information, type);
        this.information = information;
        this.control = control;
        this.player = player;
        changeText(information);
    }
    
    private void changeText(String information) {
        commands.clear();
        descriptor.clear();
        outlines.clear();
        beginnings.clear();
        endings.clear();
        String newInformation = "";
        for (int i = 0; i < information.length(); i++) {
            if (information.charAt(i) == '[') {
                break;
            } else {
                newInformation += information.charAt(i);
            }
        }
        if ((char)Keyboard.controls[control][player] >= 'A' && (char)Keyboard.controls[control][player] <= 'Z') {
            newInformation += "[" + (char)Keyboard.controls[control][player] + "]";
        } else if ((char)Keyboard.controls[control][player] >= '0' && (char)Keyboard.controls[control][player] <= '9') {
            newInformation += "[" + (char)Keyboard.controls[control][player] + "]";
        } else {
            if ((char)Keyboard.controls[control][player] == KeyEvent.VK_SPACE);
            switch ((char)Keyboard.controls[control][player]) {
                case KeyEvent.VK_SPACE:
                    newInformation += "[SPACE]";
                    break;
                case KeyEvent.VK_CONTROL:
                    newInformation += "[CTRL]";
                    break;
                case KeyEvent.VK_SHIFT:
                    newInformation += "[SHFT]";
                    break;
                case KeyEvent.VK_UP:
                    newInformation += "[UP]";
                    break;
                case KeyEvent.VK_DOWN:
                    newInformation += "[DOWN]";
                    break;
                case KeyEvent.VK_LEFT:
                    newInformation += "[LEFT]";
                    break;
                case KeyEvent.VK_RIGHT:
                    newInformation += "[RIGHT]";
                    break;
                case KeyEvent.VK_COMMA:
                    newInformation += "[,]";
                    break;
                case KeyEvent.VK_PERIOD:
                    newInformation += "[.]";
                    break;
                case KeyEvent.VK_EQUALS:
                    newInformation += "[=]";
                    break;
                case KeyEvent.VK_SLASH:
                    newInformation += "[/]";
                    break;
                case 222: //222 for KeyEvent, 39 for char+
                    newInformation += "[']";
                    break;
                    
                
            }
        }
        Scanner scan = new Scanner(newInformation);
        scan.nextInt();
        scan.nextInt();
        scan.nextInt();
        String commandWord = scan.next();
        createText(commandWord);
    }
    
    @Override
    public void update() {
        super.update();
        if (action != -1) {
            if (Keyboard.currentKey != -1 && Keyboard.currentKey != KeyEvent.VK_ESCAPE) {
                Keyboard.controls[control][player] = Keyboard.currentKey;
                changeText(information);
                action = -1;
            }
        }
    }

}
