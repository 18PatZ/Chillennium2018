package object;

import collision.Collidable;
import collision.Hitbox;
import collision.Moveable;
import geometry.Point2D;
import geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import level.Level;
import lombok.Getter;
import lombok.Setter;
import main.Screen;
import util.Cooldown;
import util.RImage;
import util.Util;

import java.util.ArrayList;
import java.util.List;

public class Player extends Objekt implements Collidable, Moveable {

    private Cooldown inputCool = new Cooldown();
    private int jumpTick = -1;

    private double lastDX = 1;
    private double lastDY = 0;

    private Hitbox hitbox;

    private List<RImage> images = new ArrayList<>();
    private RImage imgStand;

    @Getter private static Player instance;

    @Getter @Setter
    private boolean isWolf = false;
    @Getter @Setter private double aiStrength = 0.82;

    public Player(double x, double y, double vert, Color color){
        super(x, y, vert, color);
    }

    public Player(double x, double y, double vert, Color color, String imageName){
        super(x, y, vert, color, imageName);

        for(int i = 1; i <= 4; i++)
            images.add(new RImage("luna" + i + ".png", 80));
        imgStand = new RImage("luna_standing.png", 80);

        image = imgStand;

        instance = this;
    }

    public Player(double x, double y, double vert, Color color, String imageName, double xLen, double yWidth){
        this(x, y, vert, color, imageName);

        hitbox = new Hitbox(x, y, xLen, yWidth);
    }

    int jumpTime = 40;

    private int animTick = 0;
    private int tick = 0;

    private Passive target;

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
            flipped = true;
        }
        if(Screen.getInstance().isPressed("D")){
            dx += 1;
            dy += 1;
            flipped = false;
        }

        if(target == null || tick % 120 == 0){
            Passive t = null;
            double dist = Integer.MAX_VALUE;
            for (Moveable moveable : Screen.getInstance().getMoveables()) {
                if(moveable instanceof Passive){
                    Passive p = (Passive) moveable;
                    double d = Math.pow(p.center.x - center.x, 2) + Math.pow(p.center.z - center.z, 2);
                    if(d < dist){
                        t = p;
                        dist = d;
                    }
                }
            }
            target = t;
        }

        double ddx = 0;
        double ddz = 0;

        if(isWolf){
            if(target != null) {
                ddx = target.center.x - center.x;
                ddz = target.center.z - center.z;

                double magg = Math.sqrt(ddx * ddx + ddz * ddz);
                if(magg != 0) {
                    ddx /= magg;
                    ddz /= magg;

                    dx += ddx;
                    dy += ddz;
                }

                if(magg <= 0.1) {
                    Screen.getInstance().markForDestruction(target); // KILLED
                    target = null;
                }
            }
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
            if(dx != ddx)
                dx += aiStrength * ddx;
            if(dy != ddz)
                dy += aiStrength * ddz;

            lastDX = dx / mag;
            lastDY = dy / mag;

//            double nx = getX() + lastDX / 15.0 * speed;
//            double ny = getY() + lastDY / 15.0 * speed;

            double[] pos = Util.calcCollision(this, getX(), getY(), lastDX / 15.0 * speed, lastDY / 15.0 * speed);
            if(pos != null){
                setX(pos[0]);
                setY(pos[1]);
            }
//            if(Util.canMoveTo(this, nx, ny)) {
//                setX(nx);
//                setY(ny);
//            }

            animTick += (int) speed;

            image = images.get((animTick / 10) % images.size());
        }
        else
            image = imgStand;

        if(Screen.getInstance().isPressed("T") && inputCool.expired("T", 0.05)){
            Projectile proj = new Projectile(getX(), getY(), 0, getVertical(), Color.INDIANRED, 5);
            proj.setVelX(0.35 * lastDX);
            proj.setVelY(0.35 * lastDY);
            Screen.getInstance().getAddQueue().add(proj);
        }

        tick++;
    }

    public void turnToWolf(){
        setWolf(true);
    }

    public void revertToHuman(){
        setWolf(false);
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

//        System.out.println(point.print());
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }
}
