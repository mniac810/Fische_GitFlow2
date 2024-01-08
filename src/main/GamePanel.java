package main;

import entity.Boat;
import entity.EntityHandler;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    public final int scale = 5;
    final int originTileWidth = 16;
    final int originTileHeight = originTileWidth * 4;//64
    //final int originBigTileWidth = originNormalTileWidth *3;//48

   public final int tileWidth = originTileWidth * scale;
   public final int tileHeight = originTileHeight * scale;

    public final int totalRow = 14;
    public final int WIDTH = 14 * tileWidth;
    public final int HEIGHT = tileHeight + 48*scale;

    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    EntityHandler entityH = new EntityHandler(this,keyH);

    public GamePanel(){
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));//???
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        double drawInterval = 1000000000.0/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while(gameThread != null){
            update();

            repaint();

            //60FPS
            try{
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime/=1000000;

                if(remainingTime<0){
                    remainingTime=0;
                }

                Thread.sleep((long)remainingTime);

                nextDrawTime +=drawInterval;

            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void update(){
        entityH.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        entityH.draw(g2);

        g2.dispose();
    }
}
