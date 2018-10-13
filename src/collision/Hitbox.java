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

    public Hitbox(double x, double y, double length, double width){
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
    }

    public boolean isInside(Point3D point){
        return isInside(point.x, point.z);
    }

    public boolean isInside(double px, double py){
        return (px >= x && px <= x + length) && (py <= y && py >= y - width);
    }
}
