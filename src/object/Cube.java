package object;

import collision.Collidable;
import collision.Hitbox;
import geometry.Line;
import javafx.scene.paint.Color;
import main.Screen;
import util.Util;

import java.util.ArrayList;
import java.util.List;

public class Cube extends Objekt implements Collidable {

    private Hitbox hitbox;

    public Cube(double x, double y, double vert, Color color) {
        super(x, y, vert, color);

        hitbox = new Hitbox(x, y, 1, 1);
    }

    @Override
    public void tick() {
        List<Line> lines = new ArrayList<>();
        lines.add(Screen.getInstance().makeLine(p(getX(), getVertical(), getY()), p(getX(), getVertical(), getY() - 1), color));
        lines.add(Screen.getInstance().makeLine(p(getX() + 1, getVertical(), getY()), p(getX() + 1, getVertical(), getY() - 1), color));
        lines.add(Screen.getInstance().makeLine(p(getX(), getVertical(), getY()), p(getX() + 1, getVertical(), getY()), color));
        lines.add(Screen.getInstance().makeLine(p(getX(), getVertical(), getY() - 1), p(getX() + 1, getVertical(), getY() - 1), color));

        lines.add(Screen.getInstance().makeLine(p(getX(), getVertical(), getY()), p(getX(), getVertical() + 1, getY()), color));
        lines.add(Screen.getInstance().makeLine(p(getX() + 1, getVertical(), getY()), p(getX() + 1, getVertical() + 1, getY()), color));
        lines.add(Screen.getInstance().makeLine(p(getX(), getVertical(), getY() - 1), p(getX(), getVertical() + 1, getY() - 1), color));
        lines.add(Screen.getInstance().makeLine(p(getX() + 1, getVertical(), getY() - 1), p(getX() + 1, getVertical() + 1, getY() - 1), color));

        lines.add(Screen.getInstance().makeLine(p(getX(), getVertical() + 1, getY()), p(getX(), getVertical() + 1, getY() - 1), color));
        lines.add(Screen.getInstance().makeLine(p(getX() + 1, getVertical() + 1, getY()), p(getX() + 1, getVertical() + 1, getY() - 1), color));
        lines.add(Screen.getInstance().makeLine(p(getX(), getVertical() + 1, getY()), p(getX() + 1, getVertical() + 1, getY()), color));
        lines.add(Screen.getInstance().makeLine(p(getX(), getVertical() + 1, getY() - 1), p(getX() + 1, getVertical() + 1, getY() - 1), color));

        lines.forEach(l -> {

            Screen.getInstance().getContext().setFill(l.color);

            double p1 = Screen.getWidth() / 2.0 + l.p1.x + Screen.getInstance().getOffset().x;
            double p1y = Screen.getHeight() / 2.0 - l.p1.y + Screen.getInstance().getOffset().y;

            double p2 = Screen.getWidth() / 2.0 + l.p2.x + Screen.getInstance().getOffset().x;
            double p2y = Screen.getHeight() / 2.0 - l.p2.y + Screen.getInstance().getOffset().y;

            double angle = Util.getAngle(p2 - p1, p2y - p1y) - 90;

            Screen.getInstance().getContext().save();
            Screen.getInstance().getContext().rotate(angle);

            double[] p1r = Util.rotate(p1, p1y, angle);

            double mag = Util.dist(p1, p1y, p2, p2y);

            Screen.getInstance().getContext().fillRect(p1r[0], p1r[1] - 2, mag, 4);

            Screen.getInstance().getContext().restore();

        });
    }

    private double[] p(double... d){
        return d;
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }
}
