package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTile[];

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[13];
        mapTile = new int[gp.totalRow];
        getTileImage();
    }

    public void getTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/test.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/bigtest.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/test2.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

        int col = 0;
        int x = 0,y = 0;
        while(col < gp.totalRow){

            g2.drawImage(tile[2].image,x,y,gp.tileWidth,gp.tileHeight,null);

            col++;
            x += gp.tileWidth;
        }

    }
}
