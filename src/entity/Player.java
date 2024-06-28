package entity;

import main.SetGame;
import main.KeyboardHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{ // klasa gracza

    SetGame sg;
    KeyboardHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasMetal, hasPaper, hasGlass, hasFV;
    public int cntr;

    public Player(SetGame sg, KeyboardHandler keyH){ // kontruktor odpowiedzialny za tworzenie gracza
        this.sg = sg;
        this.keyH = keyH;

        screenX = sg.screenWidth/2 - (sg.tileSize/2);
        screenY = sg.screenHeight/2 - (sg.tileSize/2);

        solidArea = new Rectangle(24, 24, sg.tileSize/4, sg.tileSize - 24);
        solidAreaDefaultX = 24;
        SolidAreaDefaultY = 24;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() { // ustawienie domylsnej pozycji gracza na mapie oraz predkosci poruszania
        worldX = sg.tileSize * 13;
        worldY = sg.tileSize * 13;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage() { // przypisanie do zmiennych odpowiednich grafik

        up1 = setup("upl");
        up2 = setup("upr");
        down1 = setup("dwl");
        down2 = setup("dwr");
        right1 = setup("str");
        right2 = setup("r");
        left1 = setup("stl");
        left2 = setup("l");
    }
    public BufferedImage setup(String imageName) { // wczytywanie obrazkow gracza

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/playerIcon/" + imageName + ".png"));
            image = uTool.scaleImage(image, sg.tileSize, sg.tileSize);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    public void update() { // rysowanie pozycji gracza

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if(keyH.upPressed) {
                direction = "up";
            }
            else if(keyH.downPressed) {
                direction = "down";
            }
            else if (keyH.leftPressed) {
                direction = "left";
            }
            else if(keyH.rightPressed) {
                direction = "right";
            }

            collisionOn = false;
            sg.cCheck.checkTile(this);

           // sprawdzany kolizje
            int objIndex = sg.cCheck.checkObject(this, true);
            pickUpObj(objIndex);

            // jesli kolizja -> false - gracz bedzie mogl sie poruszac

            if(!collisionOn) {

                switch(direction) {
                    case "up":
                        worldY -= speed;
                    break;
                    case "down":
                        worldY += speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;

                }
            }
            // czestotliwosc zmienianie wyswietlanych obrazkow podczas ruchu
            spriteCounter++;
            if(spriteCounter > 15) {
                if(spriteNum == 1) {
                    spriteNum =2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }


    }

    public void pickUpObj(int i) {   // funkcja na podnoszenie elementow, kazde odlozenie elementu zwieksza cntr++, ktory odpowiada za zakonczenie gry
        if(i != 999) {
            String objectName = sg.obj[i].name;

            switch (objectName) {
                case "Can":
                    if((hasPaper==0) && (hasGlass==0) && (hasFV==0)) {
                        sg.playSE(1);
                        hasMetal++;
                        sg.obj[i] = null;
                        sg.ui.popupMess("Podniosłeś puszkę!");
                    }
                    break;
                case "Paper":
                    if((hasMetal==0) && (hasGlass==0) && (hasFV==0)) {
                        sg.playSE(3);
                        hasPaper++;
                        sg.obj[i] = null;
                        sg.ui.popupMess("Podniosłeś papier!");
                    }
                    break;
                case "Glass":
                    if((hasMetal==0) && (hasPaper==0) && (hasFV==0)) {
                        sg.playSE(2);
                        hasGlass++;
                        sg.obj[i] = null;
                        sg.ui.popupMess("Podniosłeś szklaną butelkę!");
                    }
                    break;
                case "FV":
                    if((hasMetal==0) && (hasPaper==0) && (hasGlass==0)) {
                        sg.playSE(6);
                        hasFV++;
                        sg.obj[i] = null;
                        sg.ui.popupMess("Podniosłeś zgniłe jabłko!");
                    }
                    break;
                case "Yellow Bin":
                    if(hasMetal > 0) {
                        hasMetal--;
                        sg.playSE(4);
                        cntr++;
                    }
                    if((hasPaper!=0) || (hasGlass!=0) || (hasFV!=0)) {
                        sg.playSE(5);
                    }
                    break;
                case "Blue Bin":
                    if(hasPaper > 0) {
                        hasPaper--;
                        sg.playSE(4);
                        cntr++;
                    }
                    if((hasMetal!=0) || (hasGlass!=0) || (hasFV!=0)) {
                        sg.playSE(5);
                    }
                    break;
                case "Green Bin":
                    if(hasGlass > 0) {
                        hasGlass--;
                        sg.playSE(4);
                        cntr++;
                    }
                    if((hasPaper!=0) || (hasMetal!=0) || (hasFV!=0)) {
                        sg.playSE(5);
                    }
                    break;
                case "Brown Bin":
                    if(hasFV > 0) {
                        hasFV--;
                        sg.playSE(4);
                        cntr++;
                    }
                    if((hasPaper!=0) || (hasMetal!=0) || (hasGlass!=0)) {
                        sg.playSE(5);
                    }
                    break;

            }
            if(cntr == 12){ // jesli licznik osiagnie 12 to gra zostanie zakonczona

                sg.stopMusic();
                sg.playSE(7);
                sg.gameState = sg.gameEndState;
            }
        }

    }

    public void draw(Graphics2D g2) { // rysowanie grafik gracza w zaleznosci od kierunku ruchu

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                else if(spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                else if(spriteNum == 2) {
                    image = down2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                else if(spriteNum == 2) {
                    image = right2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                else if(spriteNum == 2) {
                    image = left2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, sg.tileSize, sg.tileSize, null);

    }
}
