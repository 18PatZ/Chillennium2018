package object;

import geometry.Point2D;
import geometry.Point3D;
import javafx.scene.paint.Color;
import main.Screen;
import util.Cooldown;
import util.Util;


public class Passive extends Objekt {

    boolean reached = true; //reached target
    double minTargetDist = 4;
    boolean scared = false;
    
    private double[] target = new double[2];
        private double lastDX = 1;
        private double lastDY = -1;
        private double threshold = 0.01; //closeness to target
        private double lastPX=100;
        private double lastPY=100;

    public Passive(double x, double y, double vert, Color color){
        super(x, y, vert, color);
        lastPX = x;
        lastPY = y;
    }

    public Passive(double x, double y, double vert, Color color, String imageName){
        super(x, y, vert, color, imageName);
    }


    public void tick() { //change dy and dx based on ai engine
        double size=5;

        double dx=0;
        double dy=0;
        
        if(!scared){ //normal operating
            if(reached){
                findTarget();
                System.out.println("new target: "+target[0]+","+target[1]);
                System.out.println("position: "+getX()+","+getY());
                reached = false;
            }
            else{
                        dx = -(getX()-target[0]);
            dy = -(getY()-target[1]);
            }

            if((target[0]-getX() )< threshold && (target[1]-getY())< threshold){
                reached = true;
                System.out.println("on target!");
            }
            else if(getX() - lastPX == 0  || getY() - lastPY ==0.0){
                reached = true; //not reached but time to find new place
                System.out.println("wall");
            }
            }
        else{//scared
            
        }
        
        
        double mag = Math.sqrt(dx * dx + dy * dy);
        double speed = .25;//walking speed


        if(mag != 0) {
            lastDX = dx / mag;
            lastDY = dy / mag;

            double nx = getX() + lastDX / 15.0 * speed;
            double ny = getY() + lastDY / 15.0 * speed;
            
            lastPX = getX();
            lastPY = getY();
            if(Util.canMoveTo(nx, ny)) {
                setX(nx);
                setY(ny);
            }
            else{
                findTarget();
                setX(getX()-lastDX/15.0*speed);
                setY(getY()-lastDY/15.0*speed);
            }
        }
        
    }
    
    void findTarget(){
        double size = 10; //size of bounding box
        target[0] = (Math.random()*2*size-size);
        target[1] = (Math.random()*2*size-size); // random + or -
        if(((target[0]-getX())*(target[0]-getX()) + (target[1]-getY())*(target[1]-getY()) < minTargetDist)||
                (!Util.canMoveTo(target[0], target[1])))//or out of bounds
                {
            findTarget();
        }
        
        
    }
    
    void run(){ //run away 
        
    }
       
}
