package object;

import collision.Moveable;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import main.Screen;
import util.Util;

import java.util.ArrayList;
import java.util.List;

public class Passive extends NPC {

    boolean reached = true; //reached target
    boolean scared = false; //running aways


    private double threshold = 0.01; //closeness to target
    private double lastPX = 100;
    private double lastPY = 100;
    @Getter
    @Setter
    double runSpeed = 1.3;
    @Getter
    @Setter
    double walkSpeed = 0.3;
    private double lastDX = 1;
    private double lastDY = 0;
    private double fov = 4; //when they get scared
    private double speed;

    public Passive(double x, double y, double vert, Color color) {
        super(x, y, vert, color);
        lastPX = x;
        lastPY = y;
        minWalkDist = 4;
    }

    public Passive(double x, double y, double vert, Color color, String imageName) {
        super(x, y, vert, color, imageName);
        minWalkDist = 4;
    }


    public void tick() { //change dy and dx based on ai engine
        double size = 5;

        double dx = 0;
        double dy = 0;

        if (!scared) { //normal operating
            if (reached) {
                findTarget();
                // System.out.println("new target: " + target[0] + "," + target[1]);
                //  System.out.println("position: " + getX() + "," + getY());
                reached = false;
            }
            speed = walkSpeed;//walking speed
        }
        if (Math.abs(target[0] - getX()) < threshold && Math.abs(target[1] - getY()) < threshold) {
            reached = true;
            // System.out.println("on target!");
            //pause!
        } else if (getX() - lastPX == 0 || getY() - lastPY == 0.0) {
            reached = true; //not reached but time to find new place
            //System.out.println("wall");
        }

//
//            
        double[] go = runWhere();
//        System.out.println("runwhere: " + go[0] + "," + go[1]);
        if (!(go[0] == 1000) || !(go[1] == 1000)) {
//            System.out.println("scared");
//                 System.out.println("scared");
            scared = true;
//                //introducing some randomness
            target[0] = run(go)[0] - (Math.random() / 10);
            target[1] = run(go)[1] + Math.random() / 10;
        } else {
            scared = false;
        }
//            
        if (scared) {//scared
            speed = runSpeed;//running speed
//                //still scared?
        }
        dx = -(getX() - target[0]);
        dy = -(getY() - target[1]);


        double mag = Math.sqrt(dx * dx + dy * dy);


        if (mag != 0) {
            lastDX = dx / mag;
            lastDY = dy / mag;

//            double nx = getX() + lastDX / 15.0 * speed;
//            double ny = getY() + lastDY / 15.0 * speed;

            lastPX = getX();
            lastPY = getY();

            double[] pos = Util.calcCollision(this, getX(), getY(), lastDX / 15.0 * speed, lastDY / 15.0 * speed);
//            if (Util.canMoveTo(this, nx, ny)) {
//                setX(nx);
//                setY(ny);
//            }
            if (pos != null) {
                setX(pos[0]);
                setY(pos[1]);
            } else { //scared shouldn't mater here
                findTarget();
                setX(getX() - lastDX / 15.0 * speed);
                setY(getY() - lastDY / 15.0 * speed);
            }
        }


    }

    //
    private double[] runWhere() {
        int enemies = 0;
        double sumx = 0;
        double sumy = 0;
        double avx, avy;

        //finding average location of all enemies within field of view:
        List<Objekt> pairs = new ArrayList<>();
        for (Moveable p1 : Screen.getInstance().getMoveables()) {
            if (p1 instanceof Enemy || (p1 instanceof Player && ((Player) p1).isWolf())) {
                Objekt enemy = (Objekt) p1;
                if (Math.abs(enemy.getX() - getX()) < fov && Math.abs(enemy.getY() - getY()) < fov) {
                    sumx += enemy.getX();
                    sumy += enemy.getY();
                    enemies += 1;
                    //scared = true;
                }
            }
        }
        if (enemies > 0) {
            avx = sumx / enemies;
            avy = sumy / enemies;
        } else {
            avx = 1000;
            avy = 1000;
        }
        return new double[]{avx, avy};
    }

    double[] run(double[] enemies) { //run away  gives direction to run
        double[] point = new double[2];
        point[0] = 2 * getX() - enemies[0];
        point[1] = 2 * getY() - enemies[1];
        return point;
    }


}
