package game;

import game.input.Keyboard;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class StickyData {
    
    //P1HP P2HP Score Currentlevel//Figure out level shit later.
    //P1UP P1DOWN P1LEFT P1RIGHT P1SHOOT P1FAST
    //P2UP P2DOWN P2LEFT P2RIGHT P2SHOOT P2FAST
    //Levels Completed.
    
    public static final String[] names = new String[3];
    public static final int[][] initialHealth = new int[3][2];
    public static final int[] score = new int[3];
    public static final int[] currentLevel = new int[3];
    public static final int[][][] controls = new int[3][6][2];
    public static final String[] levels = new String[3];
        
    
    public static void load() {
        try {
            File data = new File("StickyData.txt");
            if (!data.exists()) {
                data.createNewFile();
                PrintStream write = new PrintStream(data);
                for (int i = 0; i < 3; i++) {
                    write.println("78 69 87"); //N E W
                    write.println("4 4 0 1"); //P1Health P2Health Score CurrentLevel
                    write.println(KeyEvent.VK_W + " " + KeyEvent.VK_S + " " +  KeyEvent.VK_A + 
                            " " + KeyEvent.VK_D + " " + KeyEvent.VK_E + " " + KeyEvent.VK_SHIFT);
                    write.println(KeyEvent.VK_UP + " " + KeyEvent.VK_DOWN + " " + KeyEvent.VK_LEFT + 
                            " " + KeyEvent.VK_RIGHT + " " + KeyEvent.VK_PERIOD + " " + KeyEvent.VK_COMMA);
                    write.println("-1"); //Levels completed.
                    write.println("");
                }
            }
            Scanner scan = new Scanner(data);
            for (int i = 0; i < 3; i++) {
                names[i] = scan.nextLine();
                initialHealth[i][0] = scan.nextInt();
                initialHealth[i][1] = scan.nextInt();
                score[i] = scan.nextInt();
                currentLevel[i] = scan.nextInt();
                for (int j = 0; j < 2; j++) {
                    controls[i][0][j] = scan.nextInt();
                    controls[i][1][j] = scan.nextInt();
                    controls[i][2][j] = scan.nextInt();
                    controls[i][3][j] = scan.nextInt();
                    controls[i][4][j] = scan.nextInt();
                    controls[i][5][j] = scan.nextInt();
                    scan.nextLine();
                }
                levels[i] = scan.nextLine();
                scan.nextLine();
            }
            /*
            player.get(0).setHealth(initialHealth[0] = scan.nextInt());
            initialHealth[1] = scan.nextInt();
            initialScore = score = scan.nextInt();
            currentLevel = scan.nextInt();
            for (int i = 0; i < 2; i++) {
                Keyboard.controls[0][i] = scan.nextInt();
                Keyboard.controls[1][i] = scan.nextInt();
                Keyboard.controls[2][i] = scan.nextInt();
                Keyboard.controls[3][i] = scan.nextInt();
                Keyboard.controls[4][i] = scan.nextInt();
                Keyboard.controls[5][i] = scan.nextInt();
                scan.nextLine();
            }
            String levels = scan.nextLine();
            Scanner scanLevels = new Scanner(levels);
            if (!levels.equals("-1")) while (scanLevels.hasNextInt()) Update.levelsCompleted.add(scanLevels.nextInt());
            Update.loadMap = new LoadMap(1);
            */
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void save() {
        try {
            File data = new File("StickyData.txt");
            if (!data.exists()) {
                data.createNewFile();
            }
            PrintStream write = new PrintStream(data);
            for (int i = 0; i < 3; i++) {
                write.println(names[i]);
                write.println(initialHealth[i][0] + " " + initialHealth[i][1] + " " + score[i] + " " + currentLevel[i]);
                for (int j = 0; j < 2; j++) {
                    for (int k = 0; k < 6; k++) {
                        write.print(controls[i][k][j] + " ");
                    }
                    write.println();
                }
                write.println(levels[i]);
                write.println();
                /*
                write.println("78 69 87"); //N E W
                write.println("4 4 0 1"); //P1Health P2Health Score CurrentLevel
                write.println(KeyEvent.VK_W + " " + KeyEvent.VK_S + " " +  KeyEvent.VK_A + 
                        " " + KeyEvent.VK_D + " " + KeyEvent.VK_E + " " + KeyEvent.VK_SHIFT);
                write.println(KeyEvent.VK_UP + " " + KeyEvent.VK_DOWN + " " + KeyEvent.VK_LEFT + 
                        " " + KeyEvent.VK_RIGHT + " " + KeyEvent.VK_PERIOD + " " + KeyEvent.VK_COMMA);
                write.println("-1"); //Levels completed.
                write.println("");
                */
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
