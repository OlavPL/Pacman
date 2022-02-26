package Main.panes;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HighscorePane extends Pane {
    private static Label oneUpScore;
    private static Label score;
    public HighscorePane(int scale, int width){
        super();
        setPrefHeight(25*scale);
        setPrefWidth(width);
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Font font = new Font("Comic Sans",10*scale);
        Label oneUp = newLabel(30, 0, "1UP", scale, font);
        oneUpScore = newLabel(30, 10, "000", scale, font);
        Label high = newLabel(75, 0, "HIGH", scale, font);
        Label highscore = newLabel(105, 0, "SCORE", scale, font);
        score = newLabel(105, 10, "000", scale, font);
        getChildren().addAll(oneUp, oneUpScore, high, highscore, score);

    }

    private Label newLabel(int x, int y, String string, int scale, Font font){
        Label label = new Label(string);
        label.setTranslateX(x*scale);
        label.setTranslateY(y*scale);
        label.setFont(font);
        label.setTextFill(Color.WHITE);
        return label;
    }

    public static void setScore(int newScore){
        score.setText(""+newScore);
    }
    public static void setOneUPScore(int newScore){
        oneUpScore.setText(""+newScore);
    }
    public static Label getScore(){return score;}
    public static Label getOneUpScore(){return oneUpScore;}

}
