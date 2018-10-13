package level;

import lombok.Getter;

public class LevelManager {

    @Getter private static LevelManager instance;
    @Getter private Level currentLevel;

    public LevelManager(){
        instance = this;
        currentLevel = new Level();
    }

}
