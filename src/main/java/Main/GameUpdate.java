package Main;

import Main.Moveables.*;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
@Getter

public class GameUpdate extends AnimationTimer {
    private final Deque<KeyCode> movementStack = new ArrayDeque<>();
    private final Player player;
    private final Blinky blinky;
    private final Ghost pinky;
    private final Ghost inky;
    private final Ghost clyde;
    private long lastUpdatePlayer = 0;
    private long lastUpdateGhost = 0;
    private final ArrayList<Ghost> enemies = new ArrayList<>();
    private final Timeline vulnerableTimer;
    private boolean vulnerableState = false;

    /**
     * Constructor with all moving objects as parameter
     */
    public GameUpdate(Player player, Blinky blinky, Ghost pinky, Ghost inky, Ghost clyde) {
        this.player = player;
        this.blinky = blinky;
        this.pinky = pinky;
        this.inky = inky;
        this.clyde = clyde;
        enemies.add(blinky);
        enemies.add(pinky);
        enemies.add(pinky);
        enemies.add(inky);
        enemies.add(clyde);
        vulnerableTimer = new Timeline(new KeyFrame(Duration.seconds(6), e ->{
            for (Ghost ghost : enemies) {
                if(ghost.isVulnerable()) {
                    ghost.setGivesPoints(false);
                    ghost.makeDangerous();
                }
            }
            vulnerableState = false;
        }));
        vulnerableTimer.setCycleCount(1);
    }

    /**
     * This method overrides the handle method. It calls movement commands for player and ghosts at specified intervals.
     * <p>
     * This method applies calls the movement methods of ghosts and player every x number of nanoseconds.
     * The counter variables get reset when the counter is reached;
     *
     * @param  now  the value the interpreter manipulates, aka time counter.
     */
    @Override
    public void handle(long now) {
        if(now - lastUpdatePlayer >= 4_000_000){
            movePlayer();
            lastUpdatePlayer = now;
        }
        if(now - lastUpdateGhost >= (vulnerableState ? 10_000_000:5_000_000)){
            blinky.move();
            pinky.move();
            inky.move();
            clyde.move();
            lastUpdateGhost = now;
        }
    }

    /**
     * This method applies a KeyListener on a specified scene
     * <p>
     * This method applies a onClick to the specified scene which can take in AWSD
     * or the arrow keys and R for resetting. Movement keys manipulates the movement queue of player
     * R key calls method in Main to reset the game, keeping the scores, this is for testing purposes.
     *
     * @param  scene  the scene which the listener is applied to.
     */
    public void setListenerOnScene(Scene scene){
        scene.setOnKeyPressed(e -> {
            KeyCode key = e.getCode();
            if( key == KeyCode.A || key == KeyCode.W ||
                key == KeyCode.S || key == KeyCode.D ||
                key == KeyCode.LEFT || key == KeyCode.UP ||
                key == KeyCode.DOWN || key == KeyCode.RIGHT){

                if(key != movementStack.peek()) {
                    if (movementStack.size() > 1) {
                        if (key == movementStack.peekLast())
                            return;
                        movementStack.removeLast();
                    }
                    movementStack.addLast(key);
                }
            }
            if(key == KeyCode.R){
                Main.gameRestart(player);
            }
        });
    }

    private void movePlayer(){
        if(!movementStack.isEmpty()) {
            player.move(movementStack);
        }
    }

    public void makeVulnerable(){
        vulnerableState = true;
        for (Ghost ghost : enemies) {
            ghost.makeVulnerable();
        }
        vulnerableTimer.play();

    }

}
