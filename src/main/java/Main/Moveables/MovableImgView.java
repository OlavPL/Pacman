package Main.Moveables;

import Main.panes.OriginalLevel;
import Main.PathCell;
import Main.SpriteAnimation;
import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;

@Setter
@Getter

public class MovableImgView extends ImageView {
    protected int spriteOffsetY;
    protected int offsetMoveLeft, offsetMoveRight, offsetMoveUp, offsetMoveDown;
    protected int width;
    protected int height;
    protected double speed;
    protected int SCALE;
    protected SpriteAnimation animation;
    protected Point2D hashMapPos = new Point2D(0,0);
    protected Rectangle newPosition = new Rectangle();
    protected HashMap<Point2D, PathCell> walkablePath;
    protected OriginalLevel parentPane;


    public MovableImgView(String path, int scale, int offsetY, int width, int height, double speed, OriginalLevel parent) {
        super(path);
        this.spriteOffsetY = offsetY;
        this.SCALE = scale;
        this.speed = speed;
        this.width = width;
        this.height = height;
        walkablePath = parent.getWalkablePath();
        this.parentPane = parent;

        //Centering and sizing image
        setFitHeight(height);
        setFitWidth(width);
        setLayoutX(-getFitWidth()/2);
        setLayoutY(-getFitHeight()/2);
    }


    //    Just movement

    public boolean isMultiDirectional(Point2D block){
        int[] y = {-1, 1};
        int[] x = {-1, 1};

        boolean xDir = false;
        boolean yDir = false;

        for (int i = 0; i < 2; i++) {
            if(parentPane.getWalkablePath().containsKey(new Point2D(block.getX()+x[i], block.getY())))
                xDir = true;
            if(parentPane.getWalkablePath().containsKey(new Point2D(block.getX(), block.getY()+y[i])))
                yDir = true;
            }
        return xDir && yDir;
    }

    /**
     * This method checks if the MovableImageView can move in the specified direction.
     * <p>
     *
     *</p>
     */
    protected void moveLeft(){
        offsetAndStartAnimation(offsetMoveLeft);
        if(getTranslateX() == 0) {
            setTranslateX(parentPane.getImageWidth());
            return;
        }
        setTranslateX(getTranslateX() - speed);
    }

    protected void moveRight(){
        offsetAndStartAnimation(offsetMoveRight);
        if((int)getTranslateX() == parentPane.getImageWidth()) {
            setTranslateX(speed);
            return;
        }
        setTranslateX(getTranslateX() + speed);
    }
    protected void moveUp(){
        offsetAndStartAnimation(offsetMoveUp);
        setTranslateY(getTranslateY() - speed);
    }
    protected void moveDown(){
        offsetAndStartAnimation(offsetMoveDown);
        setTranslateY(getTranslateY() + speed);
    }

    /**
     * This method checks if the MovableImageView can move in the specified direction.
     * <p>
     *</p>
     * @param  key  the key value of the direction being checked.
     */
    protected boolean canMove(KeyCode key){
        if(canMoveLeft(key))
            return true;

        if(canMoveRight(key))
            return true;

        if(canMoveUp(key))
            return true;

        if(canMoveDown(key))
            return true;

        return false;
    }

    /**
     * These method checks if the MovableImageView can move in the specified direction.
     *<p>
     * The object has to be in the center of the opposite axis of the movement, and cell it tries
     * to move into has to be a valid movable cell
     *</p>
     * @param  key defines the pixels between sprites along the Y axis of the spritesheet;
     */
    public boolean canMoveLeft(KeyCode key){
        if (key == KeyCode.A){
            setNewPosition(-1,0);

            if (newPosition.intersects(walkablePath.get(hashMapPos).getBoundsInLocal())) {
                PathCell tempRectangle = walkablePath.get(hashMapPos);
                if(newPosition.getX() >= tempRectangle.getCenter().getX()) {
                    consume();
                    return true;
                }

                if(containsNextHashPosHorizontal(tempRectangle, -1))
                    return true;
            }

            if(newPosition.getX()==-1 && hashMapPos.getY() == 15){
                System.out.println("Teleport");
                setHashMapPos(new Point2D(28,hashMapPos.getY()));
                consume();
                return true;
            }
            if(hashMapPos.equals(new Point2D(0, 14))){
                if (newPosition.getX() >= -1 && newPosition.getY() == walkablePath.get(hashMapPos).getCenter().getY()) {
                    if (newPosition.getX() == -1)
                        setHashMapPos(new Point2D(27, hashMapPos.getY()));
                    consume();
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    public boolean canMoveRight(KeyCode key){
        if(key == KeyCode.D) {
            setNewPosition(1, 0);
            if (newPosition.intersects(walkablePath.get(hashMapPos).getBoundsInLocal())) {
                PathCell tempRectangle = walkablePath.get(hashMapPos);
                if(newPosition.getX() <= tempRectangle.getCenter().getX())
                    return true;

                if(containsNextHashPosHorizontal(tempRectangle, 1))
                    return true;
            }
            if(hashMapPos.equals(new Point2D(27, 14))){
                if (newPosition.getX() <= parentPane.getImageWidth() + 1 && newPosition.getY() == walkablePath.get(hashMapPos).getCenter().getY()) {
                    if (newPosition.getX() == parentPane.getImageWidth()+1)
                        setHashMapPos(new Point2D(0, hashMapPos.getY()));
                    consume();
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    public boolean canMoveUp(KeyCode key){
        if(key == KeyCode.W) {
            setNewPosition(0,-1);
            if (newPosition.intersects(walkablePath.get(hashMapPos).getBoundsInLocal())) {
                PathCell tempRectangle = walkablePath.get(hashMapPos);
                if(newPosition.getY() >= tempRectangle.getCenter().getY()) {
                    consume();
                    return true;
                }


                if(containsNextHashPosVertical(tempRectangle, -1))
                    return true;
                if(walkablePath.containsKey(new Point2D(hashMapPos.getX(), hashMapPos.getY()-1))) {
                    if(tempRectangle.getCenter().getX() == newPosition.getX()){
                        if(newPosition.getY() <=tempRectangle.getY())
                            setHashMapPos(new Point2D(hashMapPos.getX(),hashMapPos.getY()-1));
                        consume();
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }
    public boolean canMoveDown(KeyCode key){
        if(key == KeyCode.S) {
            setNewPosition(0, +1);
            if (newPosition.intersects(walkablePath.get(hashMapPos).getBoundsInLocal())) {
                PathCell tempRectangle = walkablePath.get(hashMapPos);
                if (newPosition.getY() <= tempRectangle.getCenter().getY()) {
                    consume();
                    return true;
                }

                if(containsNextHashPosVertical(tempRectangle, 1))
                    return true;
            }
        }
        return false;
    }

    /**
     * This method check if player can move into specified next cell in X axis
     *
     * @param  cell  the cell the player currently is inside, used to check for center coordinates.
     * @param  x indocator of whether target cell is lower or higher on the axis.
     */
    public boolean containsNextHashPosHorizontal(PathCell cell, double x){
        if(walkablePath.containsKey(new Point2D(hashMapPos.getX()+x, hashMapPos.getY()))) {
            if(cell.getCenter().getY() == newPosition.getY()){
                if(x > 0)
                    if(newPosition.getX() >= cell.getX()+cell.getWidth())
                        setHashMapPos(new Point2D(hashMapPos.getX() + x,hashMapPos.getY()));
                if(x < 0)
                    if(newPosition.getX() <= cell.getX())
                        setHashMapPos(new Point2D(hashMapPos.getX() + x,hashMapPos.getY()));
                consume();
                return true;
            }
        }
        return false;
    }

    /**
     * This method is the same as the one over this one, but for Y axis
     */
    public boolean containsNextHashPosVertical(PathCell cell, double y){
        if (walkablePath.containsKey(new Point2D(hashMapPos.getX(), hashMapPos.getY() + y))) {
            if (cell.getCenter().getX() == newPosition.getX()) {
                if(y>0)
                    if (newPosition.getY() >= cell.getY()+cell.getHeight())
                        setHashMapPos(new Point2D(hashMapPos.getX(), hashMapPos.getY() + y));
                if(y<0)
                    if(newPosition.getY() <= cell.getY())
                        setHashMapPos(new Point2D(hashMapPos.getX(),hashMapPos.getY()+y));
                consume();
                return true;
            }
        }
        return false;
    }

    /**
     * This method changes the values of the nextPosition variables, used to calculate next move
     *<p>
     *
     *</p>
     * @param  x  the ammout the translateX variable should icrement by.
     * @param  y the ammout the translateY variable should icrement by.
     */
    protected void setNewPosition(int x, int y) {
        newPosition.setX(getTranslateX()+x);
        newPosition.setY(getTranslateY()+y);
    }

    /**
     * This method changes the sprite viewport and starts animation of not already running
     *<p>
     *
     *</p>
     * @param  offsetY defines the pixels between sprites along the Y axis of the spritesheet;
     */
    public void offsetAndStartAnimation(int offsetY){
        if(animation.getOffsetY() !=offsetY)
            animation.setOffsetY(offsetY);
        if(animation.getStatus()!= Animation.Status.RUNNING)
            animation.playFromStart();
    }

    /**
     * This method should be overwritten
     */
    protected void consume(){
        /*System.out.println("Override this consume() call");*/
    };

    protected void comparePosition(double pointX, double pointY){
        System.out.println("Player: "+getTranslateX()+", "+getTranslateY()+
                "    Point: "+ (int)pointX+", "+(int)pointY);
    }


}
