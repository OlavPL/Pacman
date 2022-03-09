package Main.panes;

import Main.Main;
import Main.Moveables.Player;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GameOverPane extends Pane {
    public GameOverPane(Player player) {
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Button restartButton = new Button("Play Again");
        getChildren().add(restartButton);
        restartButton.setTranslateX(Main.WIDTH/2 * Main.SCALE - 10*Main.SCALE);
        restartButton.setTranslateY(Main.HEIGHT/3 * Main.SCALE);

        restartButton.setOnAction(e ->{
            HighscorePane.getScore().setText(""+000);
            HighscorePane.getOneUpScore().setText(""+000);
            Main.gameRestart(player);
        });

    }
}