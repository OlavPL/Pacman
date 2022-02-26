package Main;

import Main.Moveables.Blinky;
import Main.Moveables.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.Deque;
@Getter

public class GameUpdate extends AnimationTimer {
    private final Deque<KeyCode> movementStack = new ArrayDeque<>();
    private final Player player;
    private final Blinky blinky;


    public GameUpdate(Player player, Blinky blinky) {
        this.player = player;
        this.blinky = blinky;
        start();
    }

    @Override
    public void handle(long l) {
        handleKeyPress();
    }

    public void setListenerOnScene(Scene scene){
        scene.setOnKeyPressed(e -> {
            KeyCode key = e.getCode();
            if( key == KeyCode.A || key == KeyCode.W ||
                key == KeyCode.S || key == KeyCode.D){

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
                Main.gameWon(player);
            }
        });
    }

    private void handleKeyPress(){
        if(!movementStack.isEmpty()) {
            player.move(movementStack);
        }
        if (movementStack.peek() != null)
            blinky.move();
    }
}
