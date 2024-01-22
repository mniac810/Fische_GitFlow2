package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.CookieHandler;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import UI.ChoosePlayer;

public class UI {

    GamePanel gp;
    public ChoosePlayer choosePlayer;
    Graphics2D g2;
    Font arial_40, arial_80B, arial_20;
    BufferedImage menuImage;


    public boolean[] messageOn = {false, false, false, false};
    ArrayList<String> messages = new ArrayList<>();
    int[] messageCounter = {0, 0, 0, 0};
    public boolean gameFinished = false;
    public int commandNum = 0;
    //USE FOR OPTION
    public int previousState = -1;

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

    //For restarting the game
    public void restoreDefaultValue(){
        gameFinished = false;

        fishWin = false;
        boatWin = false;
        tie = false;

        Arrays.fill(messageCounter, 0);
        Arrays.fill(messageOn, false);
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
        messages.add(text);
        messageOn[messages.size() - 1] = true;
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
                for(int messageIndex = 0; messageIndex < 4; messageIndex++) {
                    if (messageOn[messageIndex] && messages.size()>messageIndex) {
                        g2.drawString(messages.get(messageIndex), 800, 335 + messageIndex * 30);

                        messageCounter[messageIndex]++;

                        if (messageCounter[messageIndex] > 130) {
                            messageCounter[messageIndex] = 0;
                            messageOn[messageIndex] = false;
                            if (allMessageOnIsFalse()){
                                messages.removeAll(messages);
                            }
                        }
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
        if(gp.optionStateOn){
            drawOptionScreen();
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

        text = "Options";
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

    public void drawOptionScreen(){
        g2.setColor(Color.WHITE);

        //SUB WINDOW
        int frameWidth = gp.tileWidth*8;
        int frameHeight = gp.HEIGHT-gp.tileWidth ;
        int frameX = gp.WIDTH/2-frameWidth/2;
        int frameY = gp.HEIGHT/2-frameHeight/2;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        options(frameX,frameY);

        gp.keyH.enterPressed = false;
    }

    public void options(int frameX,int frameY){
        int textX;
        int textY;
        int gap = 14*gp.scale;
        g2.setFont(gp.boldFont);

        //TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gap;
        g2.drawString(text,textX,textY);
        g2.setFont(gp.plainFont);

        //MUSIC
        textX = frameX + gp.tileWidth;
        textY += gap;
        if(commandNum == 0){
            g2.setFont(gp.boldFont);
            g2.drawString(">",textX-25,textY);
        }
        g2.drawString("Music",textX,textY);
        g2.setFont(gp.plainFont);

        //SE
        textY += gap;
        if(commandNum == 1){
            g2.setFont(gp.boldFont);
            g2.drawString(">",textX-25,textY);
        }
        g2.drawString("SE",textX,textY);
        g2.setFont(gp.plainFont);

        //END GAME
        textY += gap;
        if(commandNum == 2){
            g2.setFont(gp.boldFont);
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.spacePressed == true){
                if(previousState == gp.playState){
                    commandNum = 0;
                    gp.gameState = gp.titleState;
                    gp.newGame();
                    gp.stopMusic();
                    gp.playMusic(0);
                    gp.keyH.spacePressed = false;
                    gp.optionStateOn = false;
                }
                else{
                    System.exit(0);
                }

            }
        }
        if(previousState == gp.playState){
            text = "End Game";
        }
        else{
            text = "Exit Game";
        }
        g2.drawString(text,textX,textY);
        g2.setFont(gp.plainFont);

        //BACK
        textX = getXforCenteredText("Back");
        textY += 2*gap;
        if(commandNum == 3){
            g2.setFont(gp.boldFont);
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.spacePressed == true){
                commandNum = 0;
                gp.gameState = previousState;
                gp.optionStateOn = false;
                gp.keyH.spacePressed = false;
            }
        }
        g2.drawString("Back",textX,textY);
        g2.setFont(gp.plainFont);

        //MUSIC
        textX = frameX + gp.tileWidth*5;
        textY = frameY + 2*gap-24;
        g2.drawRect(textX,textY,120,24);
        // 120 divide by 5 section
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        //SE
        textY += gap;
        g2.drawRect(textX,textY,120,24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);
    }

    public void drawSubWindow(int x, int y,int width, int height){
        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);

    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.WIDTH/2 - length/2;
    }

    private boolean allMessageOnIsFalse(){
        return messageOn[0]==messageOn[1]==messageOn[2]==messageOn[3]==false;
    }
}
