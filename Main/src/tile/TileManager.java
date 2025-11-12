package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    int tilesAnimationCounter = 0;
    int tilesAnimationSpeed = 60;
    int currentTileSprite = 0;

    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {


        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_1.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_1.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water_1.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/tiles/grass_2.png"));

            tile[4] = new Tile();
            tile[4].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/tiles/grass_3.png"));

            tile[5] = new Tile();
            tile[5].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/tiles/water_2.png"));
            tile[5].collision = true;

            tile[6] = new Tile();
            tile[6].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/tiles/water_3.png"));
            tile[6].collision = true;

            tile[7] = new Tile();
            tile[7].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/tiles/tree_1.png"));
            tile[7].collision = true;

            tile[8] = new Tile();
            tile[8].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/tiles/earth_1.png"));

            tile[9] = new Tile();
            tile[9].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/tiles/sand_1.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while (col < gp.maxWorldCol) {

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        tilesAnimationCounter++;
        if (tilesAnimationCounter > tilesAnimationSpeed) {
            tilesAnimationCounter = 0;
            currentTileSprite++;
            if (currentTileSprite > 2) {
                currentTileSprite = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {


        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {


                if (tileNum == 0) {
                    if (currentTileSprite == 0) {
                        tileNum = 0;
                    } else if (currentTileSprite == 1) {
                        tileNum = 3;
                    } else if (currentTileSprite == 2) {
                        tileNum = 4;
                    }
                } else if (tileNum == 2) {
                    if (currentTileSprite == 0) {
                        tileNum = 2;
                    } else if (currentTileSprite == 1) {
                        tileNum = 5;
                    } else if (currentTileSprite == 2) {
                        tileNum = 6;
                    }
                }
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }


                worldCol++;


                if (worldCol == gp.maxWorldCol) {
                    worldCol = 0;
                    worldRow++;
                }
            }
        }

}


