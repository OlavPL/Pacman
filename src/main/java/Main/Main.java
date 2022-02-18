package Main;

import Main.Moveables.MoveableImgView;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Main extends Application {
    private final static int SCALE = 3;
    @Override
    public void start(Stage stage) {

        OriginalLevel level = new OriginalLevel(SCALE);
        Scene scene = new Scene(level);
        level.getGameUpdate().setListenerOnScene(scene);
        stage.setScene(scene);

        stage.setTitle("Pacman");
        stage.setResizable(false);
        stage.show();
    }

    public static ArrayList<Point3D> fileToHashMap(String filePath, int scale){
        ArrayList<Point3D> pathArray= new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()){
                String string = scanner.nextLine();
                String[] arr = string.split(";");
                pathArray.add(new Point3D(parseInt(arr[0])*scale, parseInt(arr[1])*scale, parseInt(arr[2])*scale));
            }
            scanner.close();
            return pathArray;
        }catch (IOException IOexc){
            IOexc.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
