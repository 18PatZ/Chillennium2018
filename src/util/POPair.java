package util;

import geometry.Point3D;
import lombok.Builder;
import lombok.Data;
import object.Objekt;

@Data @Builder
public class POPair implements Comparable<POPair> {
    private Point3D screenPoint;
    private Objekt object;

    @Override
    public int compareTo(POPair o) {
        double x = o.object.center.x - object.center.x;
        double y = object.center.z - o.object.center.z;

        if(x >= 0 || y >= 0)
            return 1;
        return -1;
//        return Double.compare(- object.center.x + object.center.z, - o.object.center.x + o.object.center.z);
//        return Double.compare(screenPoint.z, o.screenPoint.z);
    }
}
