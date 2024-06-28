package object;

import main.SetGame;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Paper extends SuperObject{ // tworzenie obiektu papier

    SetGame sg;

    public OBJ_Paper(SetGame sg) {
        this.sg = sg;
        name = "Paper";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/papers.png"));
            uTool.scaleImage(image, sg.tileSize, sg.tileSize);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
