package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {
    GamePanel gp;
    int x;
    int y;
    BufferedImage img;
    public Player(GamePanel gp){
        this.gp = gp;
        setDefaultValue();
    }

    void setDefaultValue(){
        x = 0;
        y = gp.tileHeight;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/Menu/maptit2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void draw(Graphics2D g2){
        g2.drawImage(img,x,y,gp.WIDTH,48* gp.scale,null);
    }
}
