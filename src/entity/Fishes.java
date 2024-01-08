package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Fishes{
    GamePanel gp;
    Fish[] fish;
    EntityHandler entityH;

    int firstX;

    int fishWidth,fishHeight;
    int speed;
    // Timer for fish to run
    int fishCount = 0;
    int fishTimer = 20;
    public int run = -1;

    public Fishes(GamePanel gp,EntityHandler entityH){
        this.gp = gp;
        this.entityH = entityH;

        fish = new Fish[4];
        firstX = gp.tileWidth * 6 + 12;
        getDefaultValue();
        getFishImage();
    }

    private void getFishImage(){
        try{
            fish[0].img = ImageIO.read(getClass().getResourceAsStream("/fish/black.png"));

            fish[1].img = ImageIO.read(getClass().getResourceAsStream("/fish/blue.png"));

            fish[2].img = ImageIO.read(getClass().getResourceAsStream("/fish/yellow.png"));

            fish[3].img = ImageIO.read(getClass().getResourceAsStream("/fish/green.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void getDefaultValue(){
        fishWidth = 12 * gp.scale;
        fishHeight = 8 * gp.scale;
        speed = 4;

        fish[0] = new Fish();
        fish[0].x = firstX;
        fish[0].y = 4 * gp.scale;

        fish[1] = new Fish();
        fish[1].x = firstX;
        fish[1].y = fish[0].y +16* gp.scale;

        fish[2] = new Fish();
        fish[2].x = firstX;
        fish[2].y = fish[1].y+16* gp.scale;

        fish[3] = new Fish();
        fish[3].x = firstX;
        fish[3].y = fish[2].y+16* gp.scale;
    }

    public void update(){
        if(run != -1){
            fish[run].x += speed;
            fishCount++;
            if(fish[run].x >= gp.tileWidth*13+12){
                fish[run].finished = true;
            }
        }
        if(fishCount>=fishTimer){
            entityH.done = true;
            fishCount=0;
            run = -1;
        }
    }

    public void draw(Graphics2D g2){
        for(int i=0;i<4;i++){
            if(!fish[i].caught){g2.drawImage(fish[i].img,fish[i].x,fish[i].y,fishWidth,fishHeight,null);}
        }

    }

    public void collision(int boatX){
        for(int i=0;i<4;i++){
            if(boatX>fish[i].x){
                fish[i].caught =true;
            }
        }
    }
    public boolean getCatched(int fishNum){
        return fish[fishNum].caught;
    }
    public boolean getFinished(int fishNum){
        return fish[fishNum].finished;
    }
    public int getFishX(int fishNum){
        return fish[fishNum].x;
    }
}
