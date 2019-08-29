package game.input;

import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.KEY_LAST;
import java.awt.event.KeyListener;
public class Keyboard implements KeyListener {

    public static boolean restart;
    
    public boolean nextLevel;
    public boolean previousLevel;
    public boolean load;
    public boolean save;
    public boolean pause;
    public boolean home;
    
    public static int controls[][] = new int[6][2];
    /*
    0 up
    1 down
    2 left
    3 right
    4 shoot
    5 fast
    */
    
    private static final boolean[] keys = new boolean[KEY_LAST];
    
    public Keyboard() {
        /*
        try {
            Scanner scan = new Scanner(new File("StickyData.txt"));
            scan.nextLine();
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 6; j++) {
                    controls[j][i] = scan.nextInt();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        */
    }
    
    public static boolean key(int index) {
        return keys[index];
    }
    
    @Override
    public void keyTyped(KeyEvent ke) { //Abstract part of the class.
    }
    
    
    public static int currentKey = -1; //For type, figuring out which key is most recently pressed.
    
    @Override
    public void keyPressed(KeyEvent ke) {
        keys[ke.getKeyCode()] = true;
        currentKey = ke.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        keys[ke.getKeyCode()] = false;
        currentKey = -1;
    }
    
}