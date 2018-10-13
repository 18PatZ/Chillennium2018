package object;

import collision.Collidable;
import geometry.Point2D;
import geometry.Point3D;
import javafx.scene.paint.Color;
import level.Level;
import main.Screen;
import util.Cooldown;
import util.RImage;

public class Player extends Objekt {

    private Cooldown inputCool = new Cooldown();
    private int jumpTick = -1;

    private double lastDX = 1;
    private double lastDY = 0;

    private RImage imageNorm;
    private RImage imageFlipped;

    private Level level;

    public Player(double x, double y, double vert, Color color){
        super(x, y, vert, color);
    }

    public Player(double x, double y, double vert, Color color, String imageName){
        super(x, y, vert, color, imageName);

        imageNorm = image;
        imageFlipped = new RImage("troll_flipped.png", 50);

        level = new Level();
    }

    int jumpTime = 40;

    @Override
    public void tick() {

        double dx = 0;
        double dy = 0;
        if(Screen.getInstance().isPressed("W")){
            dx += 1;
            dy += -1;
        }
        if(Screen.getInstance().isPressed("S")){
            dx += -1;
            dy += 1;
        }
        if(Screen.getInstance().isPressed("A")){
            dx += -1;
            dy += -1;

            image = imageFlipped;
        }
        if(Screen.getInstance().isPressed("D")){
            dx += 1;
            dy += 1;

            image = imageNorm;
        }

        if(Screen.getInstance().isPressed("SPACE") && inputCool.expired("SPACE", jumpTime / 60.0))
            jumpTick = 0;

        if(jumpTick > -1) {
            jumpTick++;

            setVertical(0.8 * Math.sin(jumpTick * Math.PI / jumpTime));

            if(jumpTick >= jumpTime) {
                jumpTick = -1;
                setVertical(0);
            }
        }

        double mag = Math.sqrt(dx * dx + dy * dy);
        double speed = 1;

        if(Screen.getInstance().isPressed("SHIFT"))
            speed *= 2.5;

        if(mag != 0) {
            lastDX = dx / mag;
            lastDY = dy / mag;

            double nx = getX() + lastDX / 15.0 * speed;
            double ny = getY() + lastDY / 15.0 * speed;

            if(canMoveTo(nx, ny)) {
                setX(nx);
                setY(ny);
            }
        }

        if(Screen.getInstance().isPressed("T") && inputCool.expired("T", 0.05)){
            Projectile proj = new Projectile(getX(), getY(), getVertical(), Color.GOLDENROD, 1);
            proj.setVelX(0.3 * lastDX);
            proj.setVelY(0.3 * lastDY);
            Screen.getInstance().getAddQueue().add(proj);
        }
    }

    private boolean canMoveTo(double nx, double ny){
        if(!level.getHitbox().isInside(nx, ny)) return false;

        for (Objekt objekt : Screen.getInstance().getObjekts())
            if (objekt instanceof Collidable && ((Collidable) objekt).getHitbox() != null &&
                    ((Collidable) objekt).getHitbox().isInside(nx, ny))
                return false;

        return true;
    }

    @Override
    public void postTick(Point3D point) {

        double width = Screen.getWidth();
        double height = Screen.getHeight();
        Point2D targetOffset = Screen.getInstance().getTargetOffset();
        Point2D offset = Screen.getInstance().getOffset();

        double right = point.x - width * 9 / 10.0;
        double left = width / 10.0 - point.x;
        double up = height / 10.0 - point.y;
        double down = point.y - height * 9 / 10.0;

        if (right > 0)
            targetOffset.x = offset.x - right;
        else if (left > 0)
            targetOffset.x = offset.x + left;
        if (up > 0)
            targetOffset.y = offset.y + up;
        else if (down > 0)
            targetOffset.y = offset.y - down;
    }
}
