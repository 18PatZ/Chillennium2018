package object;

import javafx.scene.paint.Color;
import util.RImage;

public class StaticObject extends Objekt {

    public StaticObject(double x, double y, double vert, String imageName, double width){
        super(x, y, vert, Color.CADETBLUE);
        this.image = new RImage(imageName, width);
    }

    @Override
    public void tick() {

    }
}
