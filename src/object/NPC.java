/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import javafx.scene.paint.Color;
import util.RImage;
import util.Util;

/**
 * @author start
 */
abstract class NPC extends Living {
    double[] target = new double[2];
    double minWalkDist;
    private double lastDX;
    private double lastDY;


    public NPC(double x, double y, double vert, Color color, int health) {
        super(x, y, vert, color, health);
    }

    public NPC(double x, double y, double vert, Color color, String imageName, int health) {
        super(x, y, vert, color, health);
        this.image = new RImage(imageName, 50);
    }

    @Override
    public abstract void tick();

    void findTarget() {
        double size = 10; //size of bounding box
        target[0] = (Math.random() * 2 * size - size);
        target[1] = (Math.random() * 2 * size - size); // random + or -

        if (((target[0] - getX()) * (target[0] - getX()) + (target[1] - getY()) * (target[1] - getY()) < minWalkDist) ||
                (!Util.canMoveTo(this, target[0], target[1]))) { //or out of bounds
            findTarget();
        }


    }

}
