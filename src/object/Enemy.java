/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import collision.Moveable;
import geometry.Point2D;
import geometry.Point3D;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import level.LevelManager;
import lombok.Getter;
import lombok.Setter;
import main.Screen;
import util.Cooldown;
import util.RImage;
import util.Util;

/**
 *
 * @author start
 */
public class Enemy extends NPC{



    private double lastDX = 1;
    private double lastDY = 0;
    private double lastPX;
    private double lastPY;

    private RImage imageNorm;
    private RImage imageFlipped;
    private double fov = 4;
    private boolean pursuing = false;
    private boolean reached = true; 
    private double speed;
    private double threshold = 0.1;
    @Getter @Setter double runSpeed=1.3;
    @Getter @Setter double walkSpeed=0.3;
    private Objekt victim= null;
    
    

    public Enemy(double x, double y, double vert, Color color){
        super(x, y, vert, color);
        minWalkDist = 4;
        lastPX = getX();
        lastPY = getY();
    }

    public Enemy(double x, double y, double vert, Color color, String imageName){
        super(x, y, vert, color, imageName);
        minWalkDist = 3;
        imageNorm = image;
        lastPX = getX();
        lastPY = getY();
        //imageFlipped = new RImage("troll_flipped.png", 50);
    }


    @Override
    public void tick() { //change dy and dx based on ai engine
        double size = 5;

        double dx = 0;
        double dy = 0;

        if (!pursuing) { //normal operating
            if (reached) {
                findTarget();
               // System.out.println("new target: " + target[0] + "," + target[1]);
              //  System.out.println("position: " + getX() + "," + getY());
                reached = false;
            } 
            speed = walkSpeed;//walking speed
            }
        
        //chasing test
                    victim = runWhere();
            if(victim != null){
                System.out.println("pursuing");
                pursuing=true;
                target[0] = (victim.getX())+(Math.random()/10);
                target[1] = (victim.getY())-(Math.random()/10);
            
            }
            else{
                pursuing= false;
            }
        
        
        
        //hasReached
            if (Math.abs(target[0] - getX()) < threshold && Math.abs(target[1] - getY()) < threshold) {
                reached = true;
                
                if(victim!=null && !(victim instanceof Player)){ //code for passives
                    
                    if(!(Screen.getInstance().getDestroyQueue().contains(victim))) { //not already killed
                        Screen.getInstance().addToQue(new Enemy(victim.getX(), victim.getY(), 0, Color.RED, "liz.png"));
                        Screen.getInstance().markForDestruction(victim);

                        if(LevelManager.getInstance().getCurrentWave() != null) {
                            LevelManager.getInstance().getCurrentWave().incWolf();
                            LevelManager.getInstance().getCurrentWave().decHumans();
                        }

                        System.out.println("destroying victim");
                    }
                }
                else if(victim instanceof Player){//hurt player
                    
                }
                victim = null; //if already killled
                pursuing = false;

            } else if (getX() - lastPX == 0 || getY() - lastPY == 0.0) {
                reached = true; //not reached but time to find new place
                //System.out.println("wall");
            }

//
//            

//            
        if(pursuing) {//scared
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
//            if (Util.canMoveTo(this, nx, ny)) {
//                setX(nx);
//                setY(ny);
//            }
            double[] pos = Util.calcCollision(this, getX(), getY(), lastDX / 15.0 * speed, lastDY / 15.0 * speed);
            if(pos != null){
                setX(pos[0]);
                setY(pos[1]);
            }
            else { //scared shouldn't mater here
                findTarget();
                setX(getX() - lastDX / 15.0 * speed);
                setY(getY() - lastDY / 15.0 * speed);
            }
        }

    
    }

    
private Objekt runWhere() {
     int enemies = 0;

     double posx = 1000, posy = 1000;
     double dist = Double.MAX_VALUE;
     Objekt targ = null;

     for (Moveable p1 : Screen.getInstance().getMoveables()) {
         if ((p1 instanceof Player && !((Player) p1).isWolf()) || p1 instanceof Passive) {//not wolf
             Objekt victim = (Objekt) p1;
             double dx = Math.abs(victim.getX() - getX());
             double dy = Math.abs(victim.getY() - getY());

             if (dx < fov && dy < fov) {
                 double d = dx * dx + dy * dy;
                 if(d < dist) {
                     dist = d;
                     targ = (Objekt)p1;


                 }
                 //scared = true;
             }
         }
     }

     if(posx != 1000 || posy != 1000)
         pursuing = true;

     return targ;
 }

}




