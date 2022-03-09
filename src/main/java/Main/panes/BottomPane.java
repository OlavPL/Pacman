package Main.panes;

import Main.Main;
import Main.Moveables.Player;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;

public class BottomPane extends Pane {
    public ArrayList<Circle> livesArray = new ArrayList<>();

    /**
     * This method overrides the handle method. It calls movement commands for player and ghosts at specified intervals.
     * <p>
     * This method applies calls the movement methods of ghosts and player every x number of nanoseconds.
     * The counter variables get reset when the counter is reached;
     *
     * @param  width of the pane.
     */
    public BottomPane(int width) {
        super();
        setPrefHeight(15 * Main.SCALE);
        setPrefWidth(width);
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        resetLives();

        Circle cherry = new Circle(5*Main.SCALE,Color.RED);
        cherry.setTranslateX(180*Main.SCALE);
        cherry.setTranslateY(7*Main.SCALE);

    }

    private void newLifeIcon(){
        Circle circle = new Circle(5*Main.SCALE,Color.YELLOW);
        circle.setTranslateX((20* livesArray.size() + 10) *  Main.SCALE);
        circle.setTranslateY(7*Main.SCALE);
        livesArray.add(circle);
        getChildren().add(circle);
    }

    /**
     * This method sets Player lives to 3 and resets the life indication of the BottomPane
     */
    public void resetLives(){
        Player.setLives(3);
        int lifenum = 3;
        if(!livesArray.isEmpty())
            livesArray.clear();

        for (int i = 0; i < lifenum; i++) {
            newLifeIcon();
        }
    }

    /**
     * This method is called in player to remove the one from the visual indication of lives.
     */
    public void removeLife(){
        getChildren().remove(livesArray.get(livesArray.size()-1));
        livesArray.remove(livesArray.get(livesArray.size()-1));
    }

    public ArrayList<Circle> getLivesArray(){return livesArray;}
}
