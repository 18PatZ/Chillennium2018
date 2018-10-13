package geometry;

import lombok.Getter;

public class Point3D extends Point2D {

    @Getter public double z;

    public Point3D(double x, double y, double z){
        super(x, y);
        this.z = z;
    }

    public static Point3D fromArray(double[] points){
        return new Point3D(points[0], points[1], points.length > 2 ? points[2] : 0);
    }

    @Override
    public double[] toArray(){
        return new double[]{x, y, z};
    }

    @Override
    public String print(){
        return "(" + x + ", " + y + ", " + z + ")";
    }

}
