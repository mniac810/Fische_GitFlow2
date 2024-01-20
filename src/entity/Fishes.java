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
    int gap;

    int fishWidth,fishHeight;
    int speed;
    // Timer for fish to run
    int fishCount = 0;
    int fishTimer = 20;
    public int run = -1;

    public int fishRemaining = 4;
    public int fishFinished = 0;

    public Fishes(GamePanel gp,EntityHandler entityH){
        this.gp = gp;
        this.entityH = entityH;

        fish = new Fish[4];
        firstX = gp.tileWidth * 11 + 12;
        getDefaultValue();
        getFishImage();
    }

    private void getFishImage(){
        try{
            fish[0].img = ImageIO.read(getClass().getResourceAsStream("/fish/blue.png"));

            fish[1].img = ImageIO.read(getClass().getResourceAsStream("/fish/pink.png"));

            fish[2].img = ImageIO.read(getClass().getResourceAsStream("/fish/yellow.png"));

            fish[3].img = ImageIO.read(getClass().getResourceAsStream("/fish/orange.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void getDefaultValue(){
        gap = 12*gp.scale;
        fishWidth = 8 * gp.scale;
        fishHeight = 4 * gp.scale;
        speed = 4;

        fish[0] = new Fish();
        fish[0].x = firstX;
        fish[0].y = 10 * gp.scale;

        fish[1] = new Fish();
        fish[1].x = firstX;
        fish[1].y = fish[0].y +gap;

        fish[2] = new Fish();
        fish[2].x = firstX;
        fish[2].y = fish[1].y+gap;

        fish[3] = new Fish();
        fish[3].x = firstX;
        fish[3].y = fish[2].y+gap;
    }

    public void update(){
        if(run != -1){
            fish[run].x += speed;
            fishCount++;
            if(fish[run].x >= gp.tileWidth*13+12){
                fish[run].finished = true;
                fishFinished++;
            }
        }
        if(fishCount>=fishTimer){
            entityH.done = true;
            fishCount=0;
            run = -1;
            if (fishFinished > 2){
                gp.ui.gameFinished = true;
                gp.ui.fishWin = true;
            }
        }
    }

    public void draw(Graphics2D g2){
        for(int i=0;i<4;i++){
            if(!fish[i].caught){g2.drawImage(fish[i].img,fish[i].x,fish[i].y,fishWidth,fishHeight,null);}
        }

    }

    public void collision(int boatX){
        int counter = 0;
        for(int i=0;i<4;i++){
            if(boatX>fish[i].x && !fish[i].caught){
                counter++;
                fish[i].caught =true;
                fishRemaining--;
                gp.ui.showMessage("Fish number " + (4 - fishRemaining) + " is caught!");
                if (counter > 1)
                    gp.ui.showMessage("Another fish is caught also!");
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
    public int getFishRemaining() {return fishRemaining;}
}
