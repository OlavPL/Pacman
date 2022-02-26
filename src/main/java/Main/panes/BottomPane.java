package Main.panes;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BottomPane extends Pane {
    public BottomPane(int scale, int width) {
        super();
        setPrefHeight(15 * scale);
        setPrefWidth(width);
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Circle circle1 = new Circle(5*scale,Color.YELLOW);
        circle1.setTranslateX(10*scale);
        circle1.setTranslateY(7*scale);
        Circle circle2 = new Circle(5*scale,Color.YELLOW);
        circle2.setTranslateX(30*scale);
        circle2.setTranslateY(7*scale);
        Circle cherry = new Circle(5*scale,Color.RED);
        cherry.setTranslateX(180*scale);
        cherry.setTranslateY(7*scale);

        getChildren().addAll(circle1, circle2, cherry);
    }
}
