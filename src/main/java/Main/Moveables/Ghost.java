package Main.Moveables;

import Main.SpriteAnimation;
import javafx.util.Duration;

public abstract class Ghost extends MoveableImgView {
    Player player;
    public Ghost(String path, int scale, int speed, Player player) {
        super(path, scale, 16, 34, 34, speed);
        this.player = player;
        setAnimation(new SpriteAnimation(this, 2,0, 0, getSpriteOffsetY(), width, height, Duration.millis(200)));
        animation.play();

    }

    public void move(){

    }

}
