package Main;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {
    private final ImageView imageView;
    private final int count;
    private final int columns;
    private int offsetX;
    private int offsetY;
    private final int width;
    private final int height;

    public SpriteAnimation(
            ImageView imageView, int count, int columns,
            int offsetX, int offsetY, int width, int height, Duration duration
    ){
        this.imageView = imageView;
        this.count = count;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;

        setCycleDuration(duration);
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
        this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        setAutoReverse(true);

    }

    @Override
    protected void interpolate(double fraction) {
        final int index = Math.min((int)Math.floor(count*fraction),count-1);
        int y = (index/columns)*(height+18)+offsetY;

        imageView.setViewport(new Rectangle2D(0,y,width,height));
    }
}
