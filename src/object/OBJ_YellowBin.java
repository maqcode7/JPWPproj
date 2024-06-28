package object;

import main.SetGame;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_YellowBin extends SuperObject{ // tworzenie obiektu zolty smietnik

    SetGame sg;
    public OBJ_YellowBin(SetGame sg) {
        name = "Yellow Bin";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/yellowbin.png"));
            uTool.scaleImage(image, sg.tileSize, sg.tileSize);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
