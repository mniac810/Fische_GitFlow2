package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.text.DecimalFormat;
import UI.ChoosePlayer;

public class UI {

    GamePanel gp;
    public ChoosePlayer choosePlayer;
    Graphics2D g2;
    Font arial_40, arial_80B, arial_20;
    BufferedImage menuImage;


    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public int commandNum = 0;

    public boolean fishWin = false;
    public  boolean boatWin = false;
    public boolean tie = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;
        choosePlayer = new ChoosePlayer(gp);
        setDefaultValue();
    }

    void setDefaultValue(){
        try {
            arial_40 = new Font("Arial", Font.PLAIN, 40);
            arial_80B = new Font("Arial", Font.BOLD, 80);
            arial_20 = new Font("Arial", Font.PLAIN, 20);


            menuImage = ImageIO.read(getClass().getResourceAsStream("/Menu/sea3.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);
        //TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        // CHOOSE PLAYER STATE
        if( gp.gameState == gp.chooseState){
            choosePlayer.draw(g2);
        }
        // PLAY STATE
        if (gp.gameState == gp.playState) {
            if (gameFinished == true){

                gp.gameState = gp.endState;
            }
            else {
                g2.setFont(arial_20);
                g2.setColor(Color.white);
                g2.drawString("Fishes remaining = " + gp.entityH.getFishes().getFishRemaining(), 30, 335);

                if (messageOn == true) {
                    g2.drawString(message, 800, 335);

                    messageCounter++;

                    if (messageCounter > 120) {
                        messageCounter = 0;
                        messageOn = false;
                    }
                }
            }
        }

        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        // END GAME STATE
        if (gp.gameState == gp.endState){
            g2.setFont(arial_80B);
            g2.setColor(Color.white);
            String text = "Game end!";

            if (boatWin)
                if(choosePlayer.playerChoice == choosePlayer.fishermanChoice){
                    text = "YOU WIN!!!";
                }
                else{
                    text = "YOU LOSE!!!";
                }
            else if (fishWin) {
                if(choosePlayer.playerChoice == choosePlayer.fishChoice){
                    text = "YOU WIN!!!";
                }
                else{
                    text = "YOU LOSE!!!";
                }
            }
            else if (tie){
                text = "The game is tie ._.";
            }

            int x, y;
            x = getXforCenteredText(text);
            y = gp.HEIGHT / 2 - (gp.tileHeight * 2);
            g2.drawString(text, x, 200);
            drawEndScreen();
        }
    }

    public void drawTitleScreen() {
        g2.drawImage(menuImage,0,0,gp.WIDTH,gp.HEIGHT,null);

        // TITLE NAME;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,90F));
        String text = "Tempo, Kleine Fische";
        int x = getXforCenteredText(text);
        int y = gp.HEIGHT-400;
        // SHADOW COLOR
        g2.setColor(Color.black);
        g2.drawString(text,x+5,y+5);


        // MAIN COLOR
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // MENU
        int gap = 40;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
        text = "New Game";
        x = getXforCenteredText(text);
        y += gp.HEIGHT-300;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x-30, y);
        }

        text = "Load Game";
        x = getXforCenteredText(text);
        y += gap;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x-30, y);
        }

        text = "Quit";
        x = getXforCenteredText(text);
        y += gap;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x-30, y);
        }
    }


    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,60F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);

        int y = gp.HEIGHT/2;

        g2.drawString(text, x, y);
    }

    public void drawEndScreen() {
        RescaleOp rop = new RescaleOp(1.1f, 20.0f, null);
        g2.setColor(new Color(0, 0, 0, 0.5f)); // 50% darker (change to 0.25f for 25% darker)
        g2.fillRect(0, 0, gp.WIDTH, gp.HEIGHT);

        // MENU
        int gap = 40;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
        String text = "New Game";
        int x = getXforCenteredText(text);
        int y = gp.HEIGHT-300;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x-30, y);
        }

        text = "Back to Title";
        x = getXforCenteredText(text);
        y += gap;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x-30, y);
        }

        text = "Quit";
        x = getXforCenteredText(text);
        y += gap;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x-30, y);
        }
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.WIDTH/2 - length/2;
        return x;
    }


}
