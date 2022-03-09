package Main;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PathCell extends Rectangle {
    private final boolean isGhostOnly;
    private final Point2D center;
    private Consumable consumable;
    public PathCell(float x, float y, float width, float height, boolean isGhostOnly, Consumable consumable){
        super(x,y,width,height);
        this.isGhostOnly = isGhostOnly;
        this.center = new Point2D(x+(int)width/2,y+(int)height/2);
        this.consumable = consumable;
    }
}
