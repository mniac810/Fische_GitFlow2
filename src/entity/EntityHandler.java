package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

public class EntityHandler extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    Boat boat;
    Dice dice;

    Fishes fishes;
    public boolean diceTimer = false;
    public boolean done = true;

    public EntityHandler(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        boat = new Boat(gp,this);
        dice = new Dice(gp,this);
        fishes = new Fishes(gp,this);
    }
    public void update() {
        // Activate the dice
        if(keyH.spacePressed && done){
            diceTimer=true;
            gp.playSE(6);
            done = false;
        }
        // Action base on the result
        if(!diceTimer && !done){
            if(dice.result <4){
                //If this fish is caught
                if(fishes.getCaught(dice.result)){
                    boat.run = true;
                }
                else {
                    if(fishes.getFinished(dice.result)){
                        int min = gp.tileWidth*15;
                        int fishNum = -1;
                        for(int i=0;i<4;i++){
                            if(min>fishes.getFishX(i)){
                                min = fishes.getFishX(i);
                                fishNum = i;
                            }
                        }
                        //If the fish is finished -> change to the lowest fish
                        dice.result = fishNum;
                    }

                    fishes.run = dice.result;
                }
            }
            else{
                boat.run=true;
            }
        }
        if(fishes.update()){
            System.out.println("play SE");
            fishes.playFishSE();
        }
        boat.update();
    }

    public void draw(Graphics2D g2){
        fishes.draw(g2);
        boat.draw(g2);
        //Check if Boat catch any
        if(fishes.collision(boat.x+boat.boatWidth)){
            //Play SE
            boat.playBoatSE();
        }

        if (diceTimer) {
            dice.draw(g2);
        }
    }

    public Fishes getFishes() {
        return fishes;
    }
    public Boat getBoat() {return boat;}
}
