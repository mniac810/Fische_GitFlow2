package main;

import entity.EntityHandler;

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
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_SPACE) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.chooseState;
                    gp.counter = 3;
                }
                if (gp.ui.commandNum == 1) {
                    // add later
                }
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }
        //CHOOSE PLAYER STATE
        else if(gp.gameState == gp.chooseState){
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                gp.ui.choosePlayer.playerChoice--;
                if(gp.ui.choosePlayer.playerChoice==0){
                    gp.ui.choosePlayer.playerChoice = 2;
                }
            }
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                gp.ui.choosePlayer.playerChoice++;
                if(gp.ui.choosePlayer.playerChoice==3){
                    gp.ui.choosePlayer.playerChoice = 1;
                }
            }
            //SELECT THE PLAYER
            if(code == KeyEvent.VK_SPACE){
                gp.gameState = gp.playState;
                gp.counter = 3;
            }
        }
        // PLAY STATE
        else if (gp.gameState == gp.playState) {
            if(code == KeyEvent.VK_SPACE && gp.counter==0){
                spacePressed = true;
            }
            if(code == KeyEvent.VK_P){
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.optionsState;
            }
        }
        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }
        // END STATE
        else if (gp.gameState == gp.endState){
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_SPACE) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.chooseState;
                    gp.newGame();
                    gp.counter = 3;
                }
                if (gp.ui.commandNum == 1) {
                    gp.gameState = gp.titleState;
                    gp.newGame();
                }
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
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
