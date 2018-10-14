/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import javafx.scene.paint.Color;
import util.RImage;
import util.Util;
import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author start
 */
abstract class Living extends Objekt implements Movable{
    @Setter @Getter int health;
    
    public Living(double x, double y, double vert, Color color, int health) {
        super(x, y, vert, color);
        this.health = health;
    }
    
        public Living(double x, double y, double vert, Color color, String imageName,int health) {
        super(x, y, vert, color);
        this.image = new RImage(imageName, 50);
        this.health = health;
    }
    
    @Override
    public abstract void tick();
    
}
