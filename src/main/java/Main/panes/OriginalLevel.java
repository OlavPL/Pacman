package Main.panes;

import Main.*;
import Main.Moveables.Blinky;
import Main.Moveables.Ghost;
import Main.Moveables.Player;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter

public class OriginalLevel extends Pane {
    private int imageHeight;
    private int imageWidth;
    private static int tileWidth;
    private static int tileHeight;
    private GameUpdate gameUpdate;
    private HashMap<Point2D, PathCell> walkablePath = new HashMap<>();
    private static boolean[][] mapMatrix;
    private ArrayList<Consumable> snackList = new ArrayList<>();

    public OriginalLevel(int scale, int width, int hegiht, BottomPane bottomPane){
        width = width*scale;
        imageHeight = hegiht*scale;
        tileWidth = width/28;
        tileHeight = imageHeight /31;

        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        ImageView backgroundimage = new ImageView("Images/LevelSpriteResized.png");
        backgroundimage.setFitWidth(width);
        backgroundimage.setFitHeight(imageHeight);
        setImageHeight(imageHeight);
        setImageWidth(width);
        getChildren().add(backgroundimage);


//Reads specified file and constructs the level based in the txt file characters.
        ArrayList<String> lines = new ArrayList<>();
        int arrWidth = 0;
        int arrHeight = 0;
        try {
            InputStream in = new FileInputStream("src/main/resources/Levels/OriginalLevel.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    reader.close();
                    break;
                }
                lines.add(line);
                arrWidth = Math.max(arrWidth, line.length());
                arrHeight++;
            }
        }catch (IOException IOexc){
            IOexc.printStackTrace();
        }
        mapMatrix = new boolean[arrHeight][arrWidth];
        arrHeight = lines.size();

        Player player = new Player(scale, this, bottomPane);
        Blinky blinky = new Blinky(scale, 1, player, this);
        Ghost pinky = new Ghost("/Images/PinkGhostSprite.png", 1,player, this);
        Ghost inky = new Ghost("/Images/BlueGhostSprite.png", 1,player, this);
        Ghost clyde = new Ghost("/Images/OrangeGhostSprite.png", 1,player, this);

        for (int y = 0; y<arrHeight; y++){
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++){
                char ch = line.charAt(x);
                int tile = ch - 'A';
                if (tile>=0 && tile < lines.size()){
                    break;
                }
                int yPos = y*tileHeight;
                int xPos = x*tileWidth;

                if(ch != '=' && ch != '-') {
                    mapMatrix[y][x] = true;
                }else {
                    mapMatrix[y][x] = false;
                }


                switch (ch) {
                    case '#' -> {
                        Snack snack = new Snack(x * tileWidth +(int)(tileWidth/2), yPos +(int)(tileHeight/2) );
                        snackList.add(snack);
                        walkablePath.put(new Point2D(x, y), new PathCell(xPos, yPos, tileWidth, tileHeight, false, snack));

                    }
                    case ' ' -> {
                        walkablePath.put(new Point2D(x, y), new PathCell(xPos, yPos, tileWidth, tileHeight, false, null));
                    }
                    case '0' -> {
                        walkablePath.put(new Point2D(x, y), new PathCell(xPos, yPos, tileWidth, tileHeight, false,null));
                        player.setTranslateX(xPos + (int)(tileWidth/2));
                        player.setTranslateY(yPos + (int)(tileHeight/2));
                        player.setHashMapPos(new Point2D(x,y));
                    }
                    case '2' -> {
                        PowerSnack powerSnack = new PowerSnack(x * tileWidth +(int)(tileWidth/2), yPos +(int)(tileHeight/2));
                        getChildren().add(powerSnack);
                        walkablePath.put(new Point2D(x, y), new PathCell(xPos, yPos, tileWidth, tileHeight, false, powerSnack));
                    }
                    case '=' -> {
                        walkablePath.put(new Point2D(x, y), new PathCell(xPos, yPos, tileWidth, tileHeight, true, null));
                    }
                    case '3' -> {
                        blinky.setTranslateX(xPos + tileWidth-1);
                        blinky.setTranslateY(yPos + (int)(tileHeight/2));
                        blinky.setStartPos(new Point2D(x,y));
                        blinky.setHashMapPos(new Point2D(x,y));
                        walkablePath.put(new Point2D(x, y), new PathCell(xPos, yPos, tileWidth, tileHeight, false, null));
//                        getChildren().add(addTestRectangle(x,y));
                    }
                    case '4' -> {
                        inky.setTranslateX(xPos + tileWidth-1);
                        inky.setTranslateY(yPos + (int)(tileHeight/2));
                        inky.setStartPos(new Point2D(xPos + tileWidth-1,yPos + (int)(tileHeight/2)));
                        inky.setHashMapPos(new Point2D(x,y));
                        walkablePath.put(new Point2D(x, y), new PathCell(xPos, yPos, tileWidth, tileHeight, false, null));
                    }
                    case '5' -> {
                        pinky.setTranslateX(xPos + tileWidth-1);
                        pinky.setTranslateY(yPos + (int)(tileHeight/2));
                        pinky.setStartPos(new Point2D(xPos + tileWidth-1,yPos + (int)(tileHeight/2)));
                        pinky.setHashMapPos(new Point2D(x,y));
                        walkablePath.put(new Point2D(x, y), new PathCell(xPos, yPos, tileWidth, tileHeight, false, null));
                    }
                    case '6' -> {
                        clyde.setTranslateX(xPos + tileWidth-1);
                        clyde.setTranslateY(yPos + (int)(tileHeight/2));
                        clyde.setStartPos(new Point2D(xPos + tileWidth-1,yPos + (int)(tileHeight/2)));
                        clyde.setHashMapPos(new Point2D(x,y));
                        walkablePath.put(new Point2D(x, y), new PathCell(xPos, yPos, tileWidth, tileHeight, false, null));
                    }
                }
            }
        }

        gameUpdate = new GameUpdate(player, blinky, pinky, inky, clyde);
        setPrefSize(width, imageHeight);
        getChildren().addAll(snackList);
        getChildren().addAll(player, blinky, pinky, inky, clyde);
        gameUpdate.start();
        blinky.pathToTarget(player.getHashMapPos().getX(), player.getHashMapPos().getY());
    }

    public Rectangle addTestRectangle(int x, int y){
        Rectangle rec = new Rectangle(tileWidth, tileHeight, Color.TRANSPARENT);
        rec.setStroke(Color.RED);
        rec.setTranslateX(x*tileWidth);
        rec.setTranslateY(y*tileHeight);
        return rec;
    }
    public static boolean[][] getMapMatrix(){return mapMatrix;}
}
