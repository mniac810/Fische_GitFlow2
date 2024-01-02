package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Boat extends Entity{
    GamePanel gp;
    EntityHandler entityH;

    public int x;
    int y;
    int boatWidth ;
    int boatHeight ;
    public boolean run =false;
    //Set Boat Timer
    int boatCount = 0;
    int boatTimer = 20;

    BufferedImage img;

    public Boat(GamePanel gp,EntityHandler entityH){
        this.gp = gp;
        this.entityH = entityH;
        getBoatImage();
        setDefaultValues();
        //32*16
        boatWidth = 32 * gp.scale;
        boatHeight = 16 * gp.scale;
    }

    public void setDefaultValues(){
        x = 0;
        y = gp.tileHeight/2;
        speed = 4;
    }

    public void getBoatImage(){
        try{
            img = ImageIO.read(getClass().getResourceAsStream("/Objects/boat.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if(run){
            x += speed;
            boatCount ++;
        }
        if(boatCount>=boatTimer){
            run=false;
            entityH.done =true;
            boatCount = 0;
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(img,x,y,boatWidth,boatHeight,null);
    }
}
