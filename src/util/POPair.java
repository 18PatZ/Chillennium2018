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
        return Double.compare(screenPoint.z, o.screenPoint.z);
    }
}
