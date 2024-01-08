package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Dice extends Entity{
    GamePanel gp;
    EntityHandler entityH;

    int x;
    int y;
    int diceWidth ;
    int diceHeight ;
    //Time for  the Dice to exist
    int diceExist = 90;
    int diceCount = 0;
    //Time to end Rolling for player to see
    int endDiceRoll = 40;
    public int result=5;

    BufferedImage[] img = new BufferedImage[6];



    public Dice(GamePanel gp,EntityHandler entityH){
        this.gp = gp;
        this.entityH = entityH;
        getDiceImage();
        setDefaultValues();
        //32*16
        diceWidth = 48 * gp.scale;
        diceHeight = 48 * gp.scale;
    }

    public void setDefaultValues(){
        x = gp.WIDTH/2-24*gp.scale;
        //y = gp.tileHeight/2-24* gp.scale;
        y = gp.tileHeight;
        speed = 4;
    }

    public void getDiceImage(){
        try{
            img[0] = ImageIO.read(getClass().getResourceAsStream("/Dices/bluedice.png"));
            img[1] = ImageIO.read(getClass().getResourceAsStream("/Dices/pinkdice.png"));
            img[2] = ImageIO.read(getClass().getResourceAsStream("/Dices/yellowdice.png"));
            img[3] = ImageIO.read(getClass().getResourceAsStream("/Dices/orangedice.png"));
            img[4] = ImageIO.read(getClass().getResourceAsStream("/Dices/reddice.png"));
            img[5] = ImageIO.read(getClass().getResourceAsStream("/Dices/darkgreendice.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g2){
        if(diceCount % 10==0 && diceCount<endDiceRoll){
            result = (int)(Math.random()*6);
        }

        g2.drawImage(img[result],x,y,diceWidth,diceHeight,null);

        //Count the dice
        diceCount++;
        if(diceCount>diceExist){
            entityH.diceTimer=false;
            diceCount=0;
        }
    }
}
