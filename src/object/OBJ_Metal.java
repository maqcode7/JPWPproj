package object;

import main.SetGame;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Metal extends SuperObject{ // tworzenie obiektu puszka

    SetGame sg;

    public OBJ_Metal(SetGame sg) {
        this.sg = sg;
        name = "Can";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/cola.png"));
            uTool.scaleImage(image, sg.tileSize, sg.tileSize);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
