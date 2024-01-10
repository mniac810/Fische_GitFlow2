package main;

import java.awt.*;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public int commandNum = 0;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
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
        // PLAY STATE
        if (gp.gameState == gp.playState) {
            // Do later
        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
    }

    public void drawTitleScreen() {

        g2.setColor(new Color(0,128,255));
        g2.fillRect(0,0,gp.WIDTH, gp.HEIGHT);
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
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));

        text = "New Game";
        x = getXforCenteredText(text);
        y += gp.HEIGHT-300;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x-gp.tileWidth, y);
        }
        text = "Load Game";
        x = getXforCenteredText(text);
        y += gp.HEIGHT-290;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x-gp.tileWidth, y);
        }
        text = "Quit";
        x = getXforCenteredText(text);
        y += gp.HEIGHT;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x-gp.tileWidth, y);
        }
    }
    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,60F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);

        int y = gp.HEIGHT/2;

        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.WIDTH/2 - length/2;
        return x;
    }
}
