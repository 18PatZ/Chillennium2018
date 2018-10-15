package util;

import collision.Collidable;
import geometry.Point3D;
import level.LevelManager;
import main.Screen;
import object.Objekt;

public class Util {

    public static double[][] mult(double[][] a, double[][] b){
        double[][] ans = new double[a.length][b[0].length];

        for(int x = 0; x < a.length; x++)
            for(int i = 0; i < b[0].length; i++)
                ans[x][i] = dot(a[x], getColumn(b, i));

        return ans;
    }

    public static double[][] translate(double[][] a, double dX, double dY, double dZ){
        double[][] b = new double[a.length][a[0].length];

        for(int i = 0; i < a[0].length; i++){
            b[0][i] = a[0][i] + dX;
            b[1][i] = a[1][i] + dY;
            b[2][i] = a[2][i] +dZ;
        }
        return b;
    }

    public static double getAngle(double dX, double dY){
        if(dY != 0)
            return Math.toDegrees(Math.atan(dX / -dY)) + (dY > 0 ? 180 : 0);
        else if(dX != 0)
            return dX > 0 ? 90 : -90;
        return 0;
    }

    public static double dist(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }



    public static double[][] getDisplayed(double[][] points){
        double[][] disp = new double[points[0].length][3];
        for(int i = 0; i < points[0].length; i++){
            double[] col = Util.getColumn(points, i);
            disp[i][0] = col[0] * 100;
            disp[i][1] = col[1] * 100;
            disp[i][2] = col[2] * 100;
        }
        return disp;
    }

    public static double[] rotate(double x, double y, double theta){
        double rang = Math.atan(y /x) + (x < 0 ? Math.PI : 0);
        double ang = rang - theta * Math.PI/180;
        double hyp = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double[] newCoords = {Math.cos(ang) * hyp, Math.sin(ang) * hyp};
        return newCoords;
    }

    // in radians
    public static double[][] rotationMatrix(double a, double b, double c){
        return mult(new double[][]
                {{1, 0, 0}, {0, Math.cos(a), -Math.sin(a)}, {0, Math.sin(a), Math.cos(a)}}, mult(new double[][]
                {{Math.cos(b), 0, Math.sin(b)}, {0, 1, 0}, {-Math.sin(b), 0, Math.cos(b)}}, new double[][]
                {{Math.cos(c), -Math.sin(c), 0}, {Math.sin(c), Math.cos(c), 0}, {0, 0, 1}}));
    }

    public static double[] getColumn(double[][] a, int c){
        double[] column = new double[a.length];
        for(int i = 0; i < a.length; i++)
            column[i] = a[i][c];
        return column;
    }

    public static double dot(double[] a, double[] b){
        double s = 0;
        for(int i = 0; i < a.length; i++)
            s += a[i] * b[i];
        return s;
    }

    public static Point3D getPointOnScreen(double... point){
        double[][] points = new double[][]{
                {point[0]},
                {point[1]},
                {point[2]},
        };

        double[][] rot = Util.mult(Util.rotationMatrix(0, Math.toRadians(Screen.getInstance().getYaw()), 0), points);

        double[][] fin = Util.mult(Util.rotationMatrix(Math.toRadians(Screen.getInstance().getPitch()), 0, 0),
                Util.translate(rot, 0, 0, 0));

        double[] p = Util.getDisplayed(fin)[0];
        double x = Screen.getWidth() / 2.0 + p[0] + Screen.getInstance().getOffset().x;
        double y = Screen.getHeight() / 2.0 - p[1] + Screen.getInstance().getOffset().y;

        return new Point3D(x, y, p[2]);
    }

    public static Point3D getPointOnScreen(Point3D point){
        return getPointOnScreen(point.x, point.y, point.z);
    }

    public static double[] calcCollision(Objekt obj, double x, double y, double dx, double dy){
        if(canMoveTo(obj, x + dx, y + dy))
            return new double[]{x + dx, y + dy};
        if(canMoveTo(obj, x + dx, y))
            return new double[]{x + dx, y};
        if(canMoveTo(obj, x, y + dy))
            return new double[]{x, y + dy};
        return null;
    }

    public static boolean canMoveTo(Objekt obj, double nx, double ny){
        if(!LevelManager.getInstance().getCurrentLevel().getHitbox().isInside(nx, ny)) return false;

        for (Objekt objekt : Screen.getInstance().getObjekts())
            if (!obj.equals(objekt) && objekt instanceof Collidable && ((Collidable) objekt).getHitbox() != null &&
                    ((Collidable) objekt).getHitbox().isInside(nx, ny))
                return false;

        return true;
    }

    public static String getFile(String name){
        return Util.class.getResource("/" + name).toExternalForm();
    }
}
