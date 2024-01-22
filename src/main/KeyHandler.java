package main;

import entity.EntityHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean spacePressed;
    public boolean enterPressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //OPTION STATE
        if(gp.optionStateOn){
            optionState(code);
        }
        // TITLE STATE
        else if (gp.gameState == gp.titleState) {
            titleState(code);
        }
        //CHOOSE PLAYER STATE
        else if(gp.gameState == gp.chooseState){
            choosePLayerState(code);
        }
        // PLAY STATE
        else if (gp.gameState == gp.playState) {
            playState(code);
        }
        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }
        // END STATE
        else if (gp.gameState == gp.endState){
            endState(code);
        }
    }

    void titleState(int code){
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
            gp.playSE(5);
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
            gp.playSE(5);
        }
        if (code == KeyEvent.VK_SPACE) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.chooseState;
                gp.playSE(1);
                gp.counter = 3;
            }
            if (gp.ui.commandNum == 1) {
                // add later
                gp.optionStateOn = true;
                gp.ui.previousState = gp.titleState;
            }
            if (gp.ui.commandNum == 2) {
                System.exit(0);
            }
        }
    }
    void choosePLayerState(int code){
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            gp.ui.choosePlayer.playerChoice--;
            if(gp.ui.choosePlayer.playerChoice==0){
                gp.ui.choosePlayer.playerChoice = 2;
            }
            gp.playSE(5);
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            gp.ui.choosePlayer.playerChoice++;
            if(gp.ui.choosePlayer.playerChoice==3){
                gp.ui.choosePlayer.playerChoice = 1;
            }
            gp.playSE(5);
        }
        //SELECT THE PLAYER
        if(code == KeyEvent.VK_SPACE){
            gp.gameState = gp.playState;
            gp.ui.commandNum = 0;
            gp.counter = 3;
            gp.playSE(1);
        }
    }
    void playState(int code){
        if(code == KeyEvent.VK_SPACE && gp.counter==0){
            spacePressed = true;
        }
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.pauseState;
            gp.playSE(5);
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.ui.previousState = gp.playState;
            gp.optionStateOn = true;
            gp.gameState = gp.pauseState;
            gp.playSE(5);
        }
    }
    void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
            gp.playSE(5);
        }
    }
    void endState(int code){
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
            gp.playSE(5);
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
            gp.playSE(5);
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
    void optionState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.ui.previousState;
            gp.optionStateOn = false;
            gp.ui.commandNum = 0;
        }
        if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE){
            spacePressed = true;
        }
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 3;
            }
            gp.playSE(5);
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 3) {
                gp.ui.commandNum = 0;
            }
            gp.playSE(5);
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if(gp.ui.commandNum == 0 && gp.music.volumeScale < 5){
                gp.music.volumeScale++;
                gp.music.checkVolume();
                gp.playSE(5);
            }
            if(gp.ui.commandNum == 1 && gp.se.volumeScale < 5){
                gp.se.volumeScale++;
                gp.playSE(5);
            }
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if(gp.ui.commandNum == 0 && gp.music.volumeScale > 0){
                gp.music.volumeScale--;
                gp.music.checkVolume();
                gp.playSE(5);
            }
            if(gp.ui.commandNum == 1 && gp.se.volumeScale >0){
                gp.se.volumeScale--;
                gp.playSE(5);
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
}
