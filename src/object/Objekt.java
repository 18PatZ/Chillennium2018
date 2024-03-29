package object;

import geometry.Point3D;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import util.RImage;

public abstract class Objekt {

    public Point3D center;
    public Color color;
    public RImage image;
    public boolean flipped = false;
    public long lastFlip = 0;

    @Getter @Setter private Point3D lastScreenPoint;


    public Objekt(double x, double y, double vert, Color color){
        this.center = new Point3D(x, vert, y);
        this.color = color;
    }

    public Objekt(double x, double y, double vert, Color color, String imageName) {
        this(x, y, vert, color);
        this.image = new RImage(imageName, 50);
    }

    public double setX(double x){
        center.x = x;
        return x;
    }

    public double setY(double y){
        center.z = y;
        return y;
    }

    public double setVertical(double v){
        center.y = v;
        return v;
    }

    public double getX(){
        return center.x;
    }

    public double getY(){
        return center.z;
    }

    public double getVertical(){
        return center.y;
    }

    public abstract void tick();
    public void postTick(Point3D sPoint){ // point on screen

    }
}
