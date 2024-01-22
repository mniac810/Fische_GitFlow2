package main;

import UI.ChoosePlayer;
import entity.EntityHandler;
import entity.Fishes;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {

    public final int scale = 5;
    final int originTileWidth = 16;
    final int originTileHeight = originTileWidth * 4;//64
    //final int originBigTileWidth = originNormalTileWidth *3;//48

   public final int tileWidth = originTileWidth * scale; // 80
   public final int tileHeight = originTileHeight * scale; // 320

    public final int totalRow = 14;
    public final int WIDTH = 14 * tileWidth; //1120
    public final int HEIGHT = tileHeight + 48*scale; //560
    // FOR FULL SCREEN
    int screenWidth2 = WIDTH;
    int screenHeight2 = HEIGHT;
    BufferedImage tempScreen;
    Graphics2D g2;
    //FPS
    int FPS = 60;

    // SYSTEM
    Sound music = new Sound();
    Sound se = new Sound();
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    public UI ui = new UI(this);
    Player player = new Player(this);
    EntityHandler entityH = new EntityHandler(this,keyH);
    Thread gameThread;
    // For when we need to pause the game conveniently
    public int counter = 0;
    public int fishRemaining = 4;

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int chooseState = 3;
    public final int optionsState = 4;
    public final int endState = 5;
    public GamePanel(){
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));//???
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        gameState = titleState;

        tempScreen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        playMusic(0);



//        setFullScreen();
    }

    public void setFullScreen() {

        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
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

            drawToTempScreen(); // draw everything to the buffered image
            drawToScreen(); // draw the buffered image to the screen

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

    public void drawToTempScreen() {
        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        }
        else
        {
            tileM.draw(g2);
            player.draw(g2);
            entityH.draw(g2);

            ui.draw(g2);
        }
    }
    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
    public void playMusic(int i){
        music.setFile(i);
        music.setVolume(80);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
    public void newGame(){
        entityH.getBoat().setDefaultValues();
        entityH.getFishes().setDefaultValue();
        ui.restoreDefaultValue();
    }
}
