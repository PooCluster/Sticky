package game.logic;

import game.logic.button.Button;
import game.Main;
import game.logic.button.Continue;
import game.logic.button.Controls;
import game.logic.button.Exit;
import game.logic.button.Load;
import game.logic.button.LoadGame;
import game.logic.button.MusicOnOff;
import game.logic.button.MusicVolume;
import game.logic.button.controls.Name;
import game.logic.button.NewGame;
import game.logic.button.No;
import game.logic.button.PlayerOne;
import game.logic.button.PlayerTwo;
import game.logic.button.PressEnterToSelect;
import game.logic.button.Quit;
import game.logic.button.Restart;
import game.logic.button.SFXOnOff;
import game.logic.button.SFXVolume;
import game.logic.button.Save;
import game.logic.button.SaveGame;
import game.logic.button.Settings;
import game.logic.button.SingleMultiPlayer;
import game.logic.button.Yes;
import game.logic.button.controls.ChangeControls;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Menu {
    
    private static ArrayList<Button> buttons = new ArrayList<>();
    public static Stack<Integer> history = new Stack<>();
    private static int type;
    
    public Menu(int type, int page) {
        history.clear();
        buttons.clear();
        history.push(page);
        this.type = type;
        Scanner scan = new Scanner(new InputStreamReader(Main.class.getResourceAsStream("resources/menu/" + this.type + "/" + history.peek() + ".txt"))); //0, because that's the first page.
        while (scan.hasNext()) {
            String information = scan.nextLine();
            Scanner parse = new Scanner(information);
            parse.next();
            parse.nextInt();
            parse.nextInt();
            String commandWord = parse.next();
            addButtons(commandWord, information);
        }
    }
    
    public void pushLayer(int page) {
        history.push(page);
        loadPage();
    }
    
    public void removeLayer() {
        history.pop();
        if (!history.isEmpty()) loadPage();
    }
    
    private void loadPage() {
        buttons.clear();
        Scanner scan = new Scanner(new InputStreamReader(Main.class.getResourceAsStream("resources/menu/" + type + "/" + history.peek() + ".txt"))); //0, because that's the first page.
        while (scan.hasNext()) {
            String information = scan.nextLine();
            Scanner parse = new Scanner(information);
            parse.next();
            parse.nextInt();
            parse.nextInt();
            String commandWord = parse.next();
            addButtons(commandWord, information);
        }
    }
    
    private void addButtons(String commandWord, String information) {
        switch (commandWord) {
            case "[CONTINUE]":
                buttons.add(new Continue(information, type));
                break;
            case "[RESTART]":
                buttons.add(new Restart(information, type));
                break;
            case "[SETTINGS]":
                buttons.add(new Settings(information, type));
                break;
            case "_MUSIC[ON][OFF]":
                buttons.add(new MusicOnOff(information, type));
                break;
            case "_MUSIC[-][+]":
                buttons.add(new MusicVolume(information, type));
                break;
            case "_SFX__[ON][OFF]":
                buttons.add(new SFXOnOff(information, type));
                break;
            case "_SFX__[-][+]":
                buttons.add(new SFXVolume(information, type));
                break;
            case "[CONTROLS]":
                buttons.add(new Controls(information, type));
                break;
            case "[PLAYER_ONE]":
                buttons.add(new PlayerOne(information, type));
                break;
            case "[PLAYER_TWO]":
                buttons.add(new PlayerTwo(information, type));
                break;
            case "_UP___[ONE]":
                buttons.add(new ChangeControls(information, type, 0, 0));
                break;
            case "_DOWN_[ONE]":
                buttons.add(new ChangeControls(information, type, 1, 0));
                break;
            case "_LEFT_[ONE]":
                buttons.add(new ChangeControls(information, type, 2, 0));
                break;
            case "_RIGHT[ONE]":
                buttons.add(new ChangeControls(information, type, 3, 0));
                break;
            case "_SHOOT[ONE]":
                buttons.add(new ChangeControls(information, type, 4, 0));
                break;
            case "_FAST_[ONE]":
                buttons.add(new ChangeControls(information, type, 5, 0));
                break;
            case "_UP___[TWO]":
                buttons.add(new ChangeControls(information, type, 0, 1));
                break;
            case "_DOWN_[TWO]":
                buttons.add(new ChangeControls(information, type, 1, 1));
                break;
            case "_LEFT_[TWO]":
                buttons.add(new ChangeControls(information, type, 2, 1));
                break;
            case "_RIGHT[TWO]":
                buttons.add(new ChangeControls(information, type, 3, 1));
                break;
            case "_SHOOT[TWO]":
                buttons.add(new ChangeControls(information, type, 4, 1));
                break;
            case "_FAST_[TWO]":
                buttons.add(new ChangeControls(information, type, 5, 1));
                break;
            case "[EXIT]":
                buttons.add(new Exit(information, type));
                break;
            case "[SINGLEMULTI]":
                buttons.add(new SingleMultiPlayer(information, type));
                break;
            case "[NEW_GAME]":
                buttons.add(new NewGame(information, type));
                break;
            case "[LOAD_GAME]":
                buttons.add(new LoadGame(information, type));
                break;
            case "[SAVE_GAME]":
                buttons.add(new SaveGame(information, type));
                break;
            case "[PRESS_ENTER_TO_SELECT]":
                buttons.add(new PressEnterToSelect(information, type));
                break;
            case "[QUIT]":
                buttons.add(new Quit(information, type));
                break;
            case "[LOAD1]":
                buttons.add(new Load(information, type));
                break;
            case "[LOAD2]":
                buttons.add(new Load(information, type));
                break;
            case "[LOAD3]":
                buttons.add(new Load(information, type));
                break;
            case "[SAVE1]":
                buttons.add(new Save(information, type));
                break;
            case "[SAVE2]":
                buttons.add(new Save(information, type));
                break;
            case "[SAVE3]":
                buttons.add(new Save(information, type));
                break;
            case "[YES]":
                buttons.add(new Yes(information, type));
                break;
            case "[NO]":
                buttons.add(new No(information, type));
                break;
            case "[NAME]":
                buttons.add(new Name(information, type));
                break;
            default:
                buttons.add(new Button(information, type));
                break;
        }
    }
    
    public void update() {
        for (int i = 0; i < buttons.size(); i++) buttons.get(i).update();
    }
    
    public void render() {
        for (int i = 0; i < buttons.size(); i++) buttons.get(i).render();
    }
    
}
