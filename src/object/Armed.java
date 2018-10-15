package object;

import collision.Moveable;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import main.Screen;
import util.Cooldown;
import util.Util;

import java.util.ArrayList;
import java.util.List;

public class Armed extends Passive {

    private Cooldown shootCool = new Cooldown();

    private int tick = 0;
    private Objekt target = null;

    public Armed(double x, double y, double vert, Color color, int health) {
        super(x, y, vert, color, health);
    }

    public Armed(double x, double y, double vert, Color color, String imageName, int health) {
        super(x, y, vert, color, imageName, health);
    }

    @Override
    public void tick() {
        super.tick();

        if(target == null || tick % 5 == 0)
            target = getTarget();

        if(target != null){
            double dx = target.getX() - getX();
            double dy = target.getY() - getY();
            double mag = Math.sqrt(dx * dx + dy * dy);
            if(mag != 0 && shootCool.expired("SHOOT", 0.3)){
                Bullet proj = new Bullet(getX(), getY(), 0, getVertical(), Color.BLACK, 5);
                proj.setVelX(0.1 * dx / mag);
                proj.setVelY(0.1 * dy / mag);
                Screen.getInstance().getAddQueue().add(proj);
            }
        }

        tick++;
    }

    private Objekt getTarget(){

        Objekt target = null;
        double dist = Double.MAX_VALUE;

        for(Moveable p1 : Screen.getInstance().getMoveables()){
            if(p1 instanceof Enemy || (p1 instanceof Player && ((Player) p1).isWolf())){
                Objekt enemy = (Objekt) p1;
                double d = Math.pow(enemy.getX() - getX(), 2) + Math.pow(enemy.getY() - getY(), 2);
                if(d <= 8 && d < dist){
                    target = enemy;
                    dist = d;
                }
            }
        }

        return target;

    }
}
