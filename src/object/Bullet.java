package object;

import javafx.scene.paint.Color;

public class Bullet extends Projectile {
    public Bullet(double x, double y, double vert, double init, Color color, double life) {
        super(x, y, vert, init, color, life);

        bounceHeight = 0;
        killOnRebound = true;
    }
}
