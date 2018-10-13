package object;

import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import main.Screen;

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
        setX(getX() + velX);
        setY(getY() + velY);

        setVertical(0.6 * (1 - (System.currentTimeMillis() - start) / life) * Math.sin((System.currentTimeMillis() - start) * Math.PI * 2 / 180));

        if(System.currentTimeMillis() >= start + life)
            Screen.getInstance().markForDestruction(this);
    }

}
