package Main;

import Main.Moveables.Player;
import Main.panes.BottomPane;
import Main.panes.HighscorePane;
import Main.panes.OriginalLevel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    private final static int SCALE = 3;
    private final static int WIDTH = 196;
    private final static int HEIGHT = 248;
    private static final BorderPane root = new BorderPane();
    private static OriginalLevel gamePane;

    @Override
    public void start(Stage stage) {

        gamePane = new OriginalLevel(SCALE, WIDTH, HEIGHT);
        root.setTop(new HighscorePane(SCALE, WIDTH));
        root.setCenter(gamePane);
        root.setBottom(new BottomPane(SCALE, WIDTH));
        Scene scene = new Scene(root);
        gamePane.getGameUpdate().setListenerOnScene(scene);
        stage.setScene(scene);
        stage.setTitle("Pacman");
        stage.setResizable(false);
        stage.show();
    }

    public static void gameWon(Player player){
        OriginalLevel gamePane2 = new OriginalLevel(SCALE, WIDTH, HEIGHT);
        gamePane2.getGameUpdate().setListenerOnScene(root.getCenter().getScene());
        player.getAudioClip().stop();
        player.getAnimation().stop();
        gamePane.getGameUpdate().stop();
        root.getChildren().remove(gamePane);
        gamePane = gamePane2;
        root.setCenter(gamePane2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
