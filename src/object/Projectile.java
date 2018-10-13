package object;

import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import main.Screen;
import util.Util;

@Getter @Setter
public class Projectile extends Objekt {

    private double velX;
    private double velY;
    private long start;
    private double life;

    public Projectile(double x, double y, double vert, Color color, double life) {
        super(x, y, vert, color);
        start = System.currentTimeMillis();
        this.life = (life * 1000);
    }

    @Override
    public void tick() {

        double nx = getX() + velX;
        double ny = getY() + velY;

        if(Util.canMoveTo(this, nx, ny)) {
            setX(nx);
            setY(ny);
        }
        else {
            velX *= -1;
            velY *= -1;
        }

        setVertical(0.6 * (1 - (System.currentTimeMillis() - start) / life) * Math.sin((System.currentTimeMillis() - start) * Math.PI * 2 / 180));

        if(System.currentTimeMillis() >= start + life)
            Screen.getInstance().markForDestruction(this);
    }

}
