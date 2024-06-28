package object;

import main.SetGame;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Glass extends SuperObject{ // tworzenie obiektu szklana butelka
    SetGame sg;

    public OBJ_Glass(SetGame sg) {

        this.sg = sg;
        name = "Glass";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/glass.png"));
            uTool.scaleImage(image, sg.tileSize, sg.tileSize);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
