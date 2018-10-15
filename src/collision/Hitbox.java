package collision;

import geometry.Point3D;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Hitbox {

    private double x;
    private double y;
    private double width;  // along y
    private double length; // along x
    private double height;

    public Hitbox(double x, double y, double length, double width, double height){
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
    }

    public Hitbox(double x, double y, double length, double width){
        this(x, y, length, width, 0);
    }

    public boolean isInside(Point3D point){
        return isInside(point.x, point.z, point.y);
    }

    public boolean isInside(double px, double py, double v){
        return (px >= x && px <= x + length) && (py <= y && py >= y - width) && v <= height;
    }

    public boolean isInside(double px, double py){
        return isInside(px, py, 0);
    }
}
