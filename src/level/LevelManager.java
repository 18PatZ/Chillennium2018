package level;

import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import main.Screen;
import object.Armed;
import object.Passive;
import object.Player;

@Getter @Setter
public class LevelManager {

    @Getter @Setter private int defaultEnemyHealth = 100;
    @Getter @Setter private int defaultPassiveHealth = 100;
    @Getter @Setter private int playeStartHealth = 100;
    @Getter private static LevelManager instance;
    private Level currentLevel;
    private Wave currentWave;
    private int waveNumber = 0;
    private boolean gameOver;
    private int nextWaveTick = -1;
    private int tick = 0;
    private int needSpawn = 0;

    public LevelManager(){
        instance = this;
        currentLevel = new Level();
    }

    public Wave nextWave(){
        waveNumber++;
        Wave wave = new Wave();
        wave.start();
        currentWave = wave;
        nextWaveTick = -1;
        return wave;
    }

    public void endGame(){
        // GAME OVER
        gameOver = true;
        System.out.println("GAME OVER");
    }

    public void endWave(){
        nextWaveTick = tick + 15 * 60;
        currentWave = null;
        if(Player.getInstance().isWolf())
            Player.getInstance().revertToHuman();
    }

    public void tick(){

        if(currentWave != null)
            currentWave.tick();

        if(nextWaveTick != -1 && tick >= nextWaveTick)
            nextWave();

        if(needSpawn > 0 && tick % 10 == 0){
            if(Math.random() < 0.25)
                Screen.getInstance().addToQue(new Armed(-4,1,0,Color.RED, "person2.png", LevelManager.getInstance().getPlayeStartHealth()));
            else
                Screen.getInstance().addToQue(new Passive(-4,1,0, Color.GREEN, "person2.png", defaultPassiveHealth));
            needSpawn--;
        }

        tick++;
    }

}
