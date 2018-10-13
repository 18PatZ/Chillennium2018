package level;

import collision.Hitbox;
import lombok.Getter;

public class Level {

    @Getter private Hitbox hitbox;
    public Level(){
        hitbox = Hitbox.builder().x(-5).y(5).width(10).length(10).build();
    }

}
