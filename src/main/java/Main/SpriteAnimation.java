package Main;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SpriteAnimation extends Transition {
    private final ImageView imageView;
    private int count;
    private int offsetX;
    private int offsetY;
    private int gapY;
    private final int width;
    private final int height;

    public SpriteAnimation(
            ImageView imageView, int count, int offsetX, int offsetY, int gapY, int width, int height, Duration duration
    ){
        this.imageView = imageView;
        this.count = count;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.gapY = gapY;

        this.width = width;
        this.height = height;

        setCycleDuration(duration);
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
        this.imageView.setViewport(new Rectangle2D(0, 0, width, height));
        setAutoReverse(true);
    }

    @Override
    protected void interpolate(double fraction) {
        final int index = Math.min((int)Math.floor(count*fraction),count-1);
        int y = (index)*(height+gapY)+offsetY;

        imageView.setViewport(new Rectangle2D(0,y,width,height));
    }

    public void setMidFrame(){

        imageView.setViewport(new Rectangle2D(0,(height+gapY)+offsetY,width,height));
    }

}
