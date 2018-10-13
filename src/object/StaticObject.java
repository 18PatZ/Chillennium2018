package object;

import collision.Collidable;
import collision.Hitbox;
import javafx.scene.paint.Color;
import util.RImage;

public class StaticObject extends Objekt implements Collidable {

    private Hitbox hitbox;

    public StaticObject(double x, double y, double vert, String imageName, double width){
        super(x, y, vert, Color.CADETBLUE);
        this.image = new RImage(imageName, width);
    }

    public StaticObject(double x, double y, double vert, String imageName, double width, double xLen, double yWidth){
        this(x, y, vert, imageName, width);

        hitbox = new Hitbox(x, y, xLen, yWidth);
    }

    @Override
    public void tick() {

    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }
}
