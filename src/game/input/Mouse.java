package game.input;

import java.awt.event.MouseEvent;
import static java.awt.event.MouseEvent.MOUSE_LAST;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {
    
    private boolean[] keys = new boolean[MOUSE_LAST];
    
    public static boolean leftClick;
    public static int x;
    public static int y;

    @Override
    public void mouseClicked(MouseEvent me) {
        leftClick = keys[MouseEvent.BUTTON1];
    }

    @Override
    public void mousePressed(MouseEvent me) {
        keys[me.getButton()] = true;
        leftClick = keys[MouseEvent.BUTTON1];
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        keys[me.getButton()] = false;
        leftClick = keys[MouseEvent.BUTTON1];
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        x = me.getX();
        y = me.getY();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        x = me.getX();
        y = me.getY();
    }
    
}
