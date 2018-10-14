package level;

import collision.Moveable;
import javafx.scene.paint.Color;
import lombok.Getter;
import main.Screen;
import object.Enemy;
import object.Passive;
import object.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Wave {

    private int numTurn = 5;
    private int numHumans;
    private int numWolves;

    public void start(){

        numTurn *= LevelManager.getInstance().getWaveNumber();

        List<Passive> turn = new ArrayList<>();

        List<Moveable> humans = Screen.getInstance().getMoveables();
        if(humans.size() > 0){
            double chance = ((double)numTurn) / humans.size();
            for(int i = 0; i < humans.size(); i++){
                Moveable h = humans.get(i);
                if(h instanceof Passive) {
                    if (Math.random() <= chance) {
                        turn.add((Passive) h);
                        numWolves++;
                    }
                    else
                        numHumans++;
                }
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

    public void decHumans(){
        numHumans--;
        if(numHumans <= 0)
            LevelManager.getInstance().endGame();
    }

    public void decWolf(){
        numWolves--;
        if(numWolves <= 0) {
            int num = 20 * LevelManager.getInstance().getWaveNumber();
            LevelManager.getInstance().setNeedSpawn(num - numHumans);
            numHumans = num;

            System.out.println("END WAVE");

            LevelManager.getInstance().endWave();
        }
    }

    public void incWolf(){
        numWolves++;
    }


}
