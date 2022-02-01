package Main;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class GameUpdate extends AnimationTimer {
    private final Deque<KeyCode> movementStack = new ArrayDeque<>();
    private final Player player;
    public GameUpdate(Player player){
        this.player = player;
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
        });
    }

    private void handleKeyPress(){
        if(!movementStack.isEmpty()) {
            player.move(movementStack);
        }
    }
}
