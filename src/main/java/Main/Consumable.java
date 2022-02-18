package Main;

import javafx.scene.image.ImageView;

public class Consumable extends ImageView {

    public Consumable(String imagePath, int x, int y, double size){
        super(imagePath);
        setTranslateX(x);
        setTranslateY(y);
        setFitHeight(size);
        setFitWidth(size);
        setLayoutX(-getFitWidth()/2);
        setLayoutY(-getFitHeight()/2);

    }

}
