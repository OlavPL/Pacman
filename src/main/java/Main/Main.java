package Main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage){

        OriginalLevel level = new OriginalLevel();
        Scene scene = new Scene(level);
        level.getGameUpdate().setListenerOnScene(scene);
        stage.setScene(scene);

        stage.setTitle("Pacman");
        stage.setResizable(false);
        stage.show();

    }



    public static void main(String[] args) {
        launch(args);
    }
}
