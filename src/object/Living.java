/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import collision.Moveable;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import main.Screen;
import util.RImage;

/**
 * @author start
 */
abstract class Living extends Objekt implements Moveable {
    @Setter
    @Getter
    double health;

    public Living(double x, double y, double vert, Color color, int health) {
        super(x, y, vert, color);
        this.health = health;
    }

    public Living(double x, double y, double vert, Color color, String imageName, int health) {
        super(x, y, vert, color);
        this.image = new RImage(imageName, 50);
        this.health = health;
    }

    @Override
    public abstract void tick();
    public void onHit(){
        
    }

    public void doDamage(double damage) {//do damadge to the character
        health -= damage;
        onHit();
        if (health <= 0) {
            die();
//            Screen.getInstance().markForDestruction(this); //killed
        }
    }

    public void die(){

    }

}
