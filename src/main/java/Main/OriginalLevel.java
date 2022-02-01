package Main;

import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

@Getter
@Setter

public class OriginalLevel extends Pane {
    private final int SCALE = 3;
    private final int HEIGHT = 248*SCALE;
    private final int WIDTH = 192*SCALE;
    private GameUpdate gameUpdate;

    public OriginalLevel(){
        Player player = new Player(fileToHashMap("src/main/resources/Levels/OriginalLevelHorizontalPath.txt"),
                                   fileToHashMap("src/main/resources/Levels/OriginalLevelVerticalPath.txt"),
                                   SCALE);
        gameUpdate = new GameUpdate(player);
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        ImageView backgroundimage = new ImageView("Images/LevelSprite.png");
        backgroundimage.setFitWidth(WIDTH);
        backgroundimage.setFitHeight(HEIGHT);
        getChildren().add(backgroundimage);
        setPrefSize(WIDTH, HEIGHT);
        getChildren().addAll(player);

    }

    private ArrayList<Point3D> fileToHashMap(String filePath){
        ArrayList<Point3D> pathArray= new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()){
                String string = scanner.nextLine();
                String[] arr = string.split(";");
                pathArray.add(new Point3D(parseInt(arr[0])*SCALE, parseInt(arr[1])*SCALE, parseInt(arr[2])*SCALE));
            }
            scanner.close();
            return pathArray;
        }catch (IOException IOexc){
            IOexc.printStackTrace();
        }
        return null;
    }
}
