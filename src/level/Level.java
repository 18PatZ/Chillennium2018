package level;

import collision.Hitbox;
import lombok.Getter;

public class Level {

    @Getter private Hitbox hitbox;
    public Level(){
        hitbox = Hitbox.builder().x(-9).y(16).width(19).length(16).build();
    }

}
