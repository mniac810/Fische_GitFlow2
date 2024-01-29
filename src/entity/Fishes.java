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

    int fishRemaining = 4;
    int fishFinished = 0;
    //Determined when to play SE
    boolean playSE = false;

    public Fishes(GamePanel gp,EntityHandler entityH){
        this.gp = gp;
        this.entityH = entityH;

        fish = new Fish[4];
        firstX = gp.tileWidth * 3 + 12;
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

        for(int i = 0; i < 4; i++){
            fish[i] = new Fish();
            if (i == 0){
                fish[i].y = 10 * gp.scale;
            }
            else{
                fish[i].y = fish[i - 1].y + gap;
            }
            fish[i].x = firstX;
        }
    }

    public boolean update(){
        boolean finishedChanges = false;
        if(run != -1){
            playFishSE();
            fish[run].x += speed;
            fishCount++;
            if(fish[run].x >= gp.tileWidth*13+12){
                fish[run].finished = true;
                fishFinished++;
                finishedChanges = true;
            }
        }
        if(fishCount>=fishTimer){
            entityH.done = true;
            fishCount=0;
            run = -1;
            if (fishFinished > 2){
                gp.ui.gameFinished = true;
                gp.ui.fishWin = true;
                if(gp.ui.playerChoice == gp.ui.fishChoice){
                    gp.playSE(3);
                }
                else {
                    gp.playSE(4);
                }
            }
            else if(playSE){
                gp.playSE(2);
                playSE = false;
            }
        }
        return finishedChanges;
    }

    public void draw(Graphics2D g2){
        for(int i=0;i<4;i++){
            if(!fish[i].caught){g2.drawImage(fish[i].img,fish[i].x,fish[i].y,fishWidth,fishHeight,null);}
        }

    }

    public boolean collision(int boatX){
        int fishCaughtCounter = 0;
        for(int i=0;i<4;i++){
            if(boatX>fish[i].x && !fish[i].caught){
                fishCaughtCounter++;
                fish[i].caught =true;
                fishRemaining--;
                gp.ui.showMessage("Fish number " + (4 - fishRemaining) + " is caught!");
            }
        }
        //This is to check whether we should play SE
        return fishCaughtCounter > 0;
    }
    public boolean getCaught(int fishNum){
        return fish[fishNum].caught;
    }
    public boolean getFinished(int fishNum){
        return fish[fishNum].finished;
    }
    public int getFishX(int fishNum){
        return fish[fishNum].x;
    }
    public int getFishRemaining() {return fishRemaining;}
    public int getFishFinished(){return fishFinished;}
    public void playFishSE(){
        playSE = true;
    }

    //For restarting the game
    public void setDefaultValue(){
        getDefaultValue();
        getFishImage();
        fishFinished = 0;
        fishRemaining = 4;
    }
}
