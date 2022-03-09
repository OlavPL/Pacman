package Main;

import Main.Moveables.Player;
import Main.panes.BottomPane;
import Main.panes.HighscorePane;
import Main.panes.OriginalLevel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    public final static int SCALE = 3;
    public final static int WIDTH = 196;
    public final static int HEIGHT = 248;
    private static final BorderPane root = new BorderPane();
    private static OriginalLevel gamePane;
    private static BottomPane bottomPane;

    @Override
    public void start(Stage stage) {

        bottomPane = new BottomPane(WIDTH);
        root.setBottom(bottomPane);
        gamePane = new OriginalLevel(SCALE, WIDTH, HEIGHT, bottomPane);
        root.setTop(new HighscorePane(SCALE, WIDTH));
        root.setCenter(gamePane);
        Scene scene = new Scene(root);
        gamePane.getGameUpdate().setListenerOnScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                gamePane.getGameUpdate().stop();
                System.exit(0);
            }
        });
        stage.setScene(scene);
        stage.setTitle("Pacman");
        stage.setResizable(false);
        stage.show();
    }

    public static void gameRestart(Player player){
        OriginalLevel gamePane2 = new OriginalLevel(SCALE, WIDTH, HEIGHT, bottomPane);
        gamePane2.getGameUpdate().setListenerOnScene(root.getScene());
        player.getAudioClip().stop();
        player.getAnimation().stop();
        gamePane.getGameUpdate().stop();
        root.getChildren().remove(gamePane);
        gamePane = gamePane2;
        root.setCenter(gamePane2);
    }

    public static BorderPane getRoot(){return root;}
    public static int getScale(){return SCALE;}

    public static void main(String[] args) {
        launch(args);
    }
}
