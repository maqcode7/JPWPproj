package object;

import main.SetGame;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_BlueBin extends SuperObject{ // obiekt niebieski smietnik

    SetGame sg;
    public OBJ_BlueBin(SetGame sg) {
        this.sg = sg;
        name = "Blue Bin";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/bluebin.png"));
            uTool.scaleImage(image, sg.tileSize, sg.tileSize);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
