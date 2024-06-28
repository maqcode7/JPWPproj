package tile;

import main.SetGame;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    SetGame sg;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(SetGame sg) { // tworzenie kafelkow (pojedynczych kafli na planszy)

        this.sg = sg;

        tile = new Tile[10];
        mapTileNum = new int [sg.maxWorldCol][sg.maxWorldRow];

        getTileImage();
        loadMap("/maps/map.txt");
    }

    public void getTileImage() { // przypisywanie grafik do indexow obiektu

            setup(0, "grass", false);
            setup(1, "bricks2", false);
            setup(2, "water2", true);
            setup(4, "dirt", false);
            setup(5, "tree2", true);

    }
    public void setup(int index, String imageName, boolean collision) { // tworzenie tablicy przechowujacej kafelki

        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName+ ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, sg.tileSize, sg.tileSize);
            tile[index].collision = collision;


        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) { // wczytywanie mapy, odczyt umozliwujacy rysowanie na podstawie pliku txt

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < sg.maxWorldCol && row < sg.maxWorldRow) {
                String line = br.readLine();

                while (col < sg.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == sg.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch(Exception e) {

        }

    }
    public void draw(Graphics2D g2) { // rysowanie mapy

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < sg.maxWorldCol && worldRow < sg.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * sg.tileSize;
            int worldY = worldRow * sg.tileSize;
            int screenX = worldX - sg.player.worldX + sg.player.screenX;
            int screenY = worldY - sg.player.worldY + sg.player.screenY;

            if(worldX + sg.tileSize > sg.player.worldX - sg.player.screenX
                    && worldX - sg.tileSize < sg.player.worldX + sg.player.screenX
                    && worldY + sg.tileSize > sg.player.worldY - sg.player.screenY
                    && worldY - sg.tileSize < sg.player.worldY + sg.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);

            }
            worldCol++;

            if(worldCol == sg.maxWorldCol) {
                worldCol = 0;
                worldRow++;


            }

        }



    }
}
