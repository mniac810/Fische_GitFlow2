package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean spacePressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_SPACE|| code == KeyEvent.VK_ENTER){
            spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code =e.getKeyCode();

        if(code == KeyEvent.VK_SPACE|| code == KeyEvent.VK_ENTER){
            spacePressed = false;
        }
    }
}
