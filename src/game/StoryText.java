package game;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class StoryText {
    
    private String text;
    
    public StoryText(String name) {
        Scanner read = new Scanner(Main.class.getResourceAsStream(name));
        text = read.nextLine();
    }
    
    public void save(int health, int level, int score, int levelsWon) {
        try {
            PrintStream write = new PrintStream("LoadSave.txt");
            write.println(health + " " + level + " " + score + " " + levelsWon);
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public String getString() {
        return this.text;
    }
    
}