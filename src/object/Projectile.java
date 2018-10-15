package object;

import collision.Moveable;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import main.Screen;
import util.Util;

@Getter @Setter
public class Projectile extends Objekt implements Moveable {

    private double velX;
    private double velY;
    private long start;
    private double life;
    private double init;
    protected double bounceHeight = 0.6;
    protected boolean killOnRebound = false;
    private double damage = 10;

    public Projectile(double x, double y, double vert, double init, Color color, double life) {
        super(x, y, vert, color);
        start = System.currentTimeMillis();
        this.life = (life * 1000);
        this.init = init;
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
            if(killOnRebound) {
                Screen.getInstance().markForDestruction(this);
                return;
            }

            if (velX == 0)
                velY *= -1;
            else if (velY == 0)
                velX *= -1;
            else {
                if (Util.canMoveTo(this, getX() + velX, getY() - velY))
                    velY *= -1;
                else if (Util.canMoveTo(this, getX(), getY() + velY))
                    velX *= -1;
                else {
                    velY *= -1;
                    velX *= -1;
                }
            }
        }

        setVertical(init + bounceHeight * (1 - (System.currentTimeMillis() - start) / life) * Math.sin((System.currentTimeMillis() - start) * Math.PI * 2 / 180));

        for (Moveable moveable : Screen.getInstance().getMoveables()) {
            if(moveable instanceof Enemy || (moveable instanceof Player && ((Player) moveable).isWolf())){
                Living e = (Living) moveable;
                if(Math.abs(e.getX() - getX()) <= 0.2 && Math.abs(e.getY() - getY()) <= 0.2){
//                    Screen.getInstance().markForDestruction(e);
//                    if(LevelManager.getInstance().getCurrentWave() != null)
//                        LevelManager.getInstance().getCurrentWave().decWolf();
                    if(e instanceof Player)
                        e.doDamage(damage / 10);
                    else
                        e.doDamage(damage);
                }
            }
        }

        if(System.currentTimeMillis() >= start + life)
            Screen.getInstance().markForDestruction(this);
    }

}
