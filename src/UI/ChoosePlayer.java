package UI;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ChoosePlayer {
    public int playerChoice = 1;
    public int fishChoice = 1;
    public int fishermanChoice = 2;
    GamePanel gp;
    BufferedImage fisherman, fishes;
    int x;
    int y;
    int playerSide;
    int shadowGap;
    int gap;
    public ChoosePlayer(GamePanel gp){
        this.gp = gp;
        setDefaultValue();
    }

    void setDefaultValue(){
        shadowGap = 8;

        gap = gp.tileWidth*5;

        playerSide = 3*gp.tileWidth;
        y = gp.HEIGHT/2-playerSide/2;
        x = 48* gp.scale;

        try {
            fisherman = ImageIO.read(getClass().getResourceAsStream("/Menu/fisherman.png"));
            fishes = ImageIO.read(getClass().getResourceAsStream("/Menu/fishtitle1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.black);
        if (playerChoice == fishChoice) {
            g2.fillRoundRect(x-shadowGap,y-shadowGap,playerSide+2*shadowGap,playerSide+2*shadowGap,35,35);
        }
        else{
            g2.fillRoundRect(x+gap-shadowGap,y-shadowGap,playerSide+2*shadowGap,playerSide+2*shadowGap,35,35);
        }
        g2.setColor(Color.blue);
        g2.fillRoundRect(x,y,playerSide,playerSide,35,35);
        g2.fillRoundRect(x+gap,y,playerSide,playerSide,35,35);

        g2.drawImage(fisherman,x,y,playerSide,playerSide,null);
        g2.drawImage(fisherman,x+gap,y,playerSide,playerSide,null);
    }
}
