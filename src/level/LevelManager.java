package level;

import lombok.Getter;

public class LevelManager {

    @Getter private static LevelManager instance;
    @Getter private Level currentLevel;
    @Getter private Wave currentWave;

    public LevelManager(){
        instance = this;
        currentLevel = new Level();
    }

    public Wave nextWave(){
        Wave wave = new Wave();
        wave.start();
        currentWave = wave;
        return wave;
    }

}
