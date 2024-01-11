package main;

import entity.Boat;
import entity.EntityHandler;
import tile.TileManager;
import UI.ChoosePlayer;

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

    //FPS
    int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    public UI ui = new UI(this);
    Player player = new Player(this);
    ChoosePlayer choosePlayer = new ChoosePlayer(this);
    EntityHandler entityH = new EntityHandler(this,keyH);
    Thread gameThread;
    // For when we need to pause the game conveniently
    public int counter = 0;

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int chooseState = 3;
    public GamePanel(){
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));//???
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        gameState = titleState;
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
        //Now it is only use for when switching titleState and playState
        if(counter!=0){counter--;}

        if (gameState == playState) {
            entityH.update();
        }
        if (gameState == pauseState) {
            // Nothing
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        } else {
            tileM.draw(g2);
            player.draw(g2);
            entityH.draw(g2);

            ui.draw(g2);
        }




        g2.dispose();
    }
}
