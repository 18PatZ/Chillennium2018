package geometry;

import lombok.Getter;

public class Point2D {

    @Getter public double x;
    @Getter public double y;

    public Point2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public static Point2D fromArray(double[] points){
        return new Point2D(points[0], points[1]);
    }

    public double[] toArray(){
        return new double[]{x, y};
    }

    public String print(){
        return "(" + x + ", " + y + ")";
    }

}
