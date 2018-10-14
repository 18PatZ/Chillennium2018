package level;

import collision.Moveable;
import main.Screen;
import object.Enemy;
import object.Passive;
import object.Player;

import java.util.ArrayList;
import java.util.List;

public class Wave {

    private int numTurn = 1;//5;

    public void start(){

        List<Passive> turn = new ArrayList<>();

        List<Moveable> humans = Screen.getInstance().getMoveables();
        if(humans.size() > 0){
            double chance = ((double)numTurn) / humans.size();
            for(int i = 0; i < humans.size(); i++){
                Moveable h = humans.get(i);
                if(h instanceof Passive && Math.random() <= chance)
                    turn.add((Passive) h);
            }
        }

        for (Passive passive : turn) {
            Screen.getInstance().markForDestruction(passive);
            Enemy enemy = new Enemy(passive.getX(), passive.getY(), passive.getVertical(), passive.color, "liz.png");
            Screen.getInstance().getAddQueue().add(enemy);
        }

    }

    private int tick = 0;
    private boolean wolving = false;
    private int nextTransform = -1;
    private int unwolfTick = -1;
    public void tick(){

        if(!wolving){
            if(nextTransform == -1)
                nextTransform = tick + (int)(Math.random() * 1200);//3600);
            else if(tick >= nextTransform){
                wolving = true;
                nextTransform = -1;
                unwolfTick = tick + (int)(Math.random() * 1800) + 600;

                Player.getInstance().turnToWolf();
            }
        }
        else if(tick >= unwolfTick){
            wolving = false;
            Player.getInstance().revertToHuman();
        }

//        System.out.println(wolving + " " + tick + " " + nextTransform + " " + unwolfTick);
        tick++;
    }

}
