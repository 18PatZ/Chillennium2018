/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import geometry.Point2D;
import geometry.Point3D;
import javafx.scene.paint.Color;
import main.Screen;
import util.Cooldown;
import util.RImage;
import util.Util;

/**
 *
 * @author start
 */
public class Enemy extends NPC{
    private Cooldown inputCool = new Cooldown();


    private double lastDX = 1;
    private double lastDY = 0;

    private RImage imageNorm;
    private RImage imageFlipped;
    

    public Enemy(double x, double y, double vert, Color color){
        super(x, y, vert, color);
        minWalkDist = 4;
    }

    public Enemy(double x, double y, double vert, Color color, String imageName){
        super(x, y, vert, color, imageName);
        minWalkDist = 4;
        imageNorm = image;
        //imageFlipped = new RImage("troll_flipped.png", 50);
    }


    @Override
    public void tick() {
        
        double dx = 0;
        double dy = 0;


       
        

        double mag = Math.sqrt(dx * dx + dy * dy);
        double speed = 1;

//if chasing - sprint?

        if(mag != 0) {
            lastDX = dx / mag;
            lastDY = dy / mag;

            double nx = getX() + lastDX / 15.0 * speed;
            double ny = getY() + lastDY / 15.0 * speed;

            if(Util.canMoveTo(this,nx, ny)) {
                setX(nx);
                setY(ny);
            }
        }

    }


}


