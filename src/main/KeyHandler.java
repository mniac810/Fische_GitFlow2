package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean spacePressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_SPACE) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                    gp.counter = 5;
                }
                if (gp.ui.commandNum == 1) {
                    // add later
                }
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }
        // PLAY STATE
        if(code == KeyEvent.VK_SPACE && gp.counter==0){
            spacePressed = true;
        }
        if(code == KeyEvent.VK_P){
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionsState;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code =e.getKeyCode();

        if(code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
    }
    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }
}
