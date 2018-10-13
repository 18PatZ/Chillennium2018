package geometry;

import javafx.scene.paint.Color;

public class Line {

    public Point3D p1;
    public Point3D p2;
    public Color color = Color.CADETBLUE;

    public Line(Point3D p1, Point3D p2){
        this.p1 = p1;
        this.p2 = p2;
    }

}
