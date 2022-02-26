package Main.Moveables;

import Main.panes.OriginalLevel;
import Main.PathBlock;
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

public class MoveableImgView extends ImageView {
    protected int spriteOffsetY;
    protected int offsetMoveLeft, offsetMoveRight, offsetMoveUp, offsetMoveDown;
    protected int width;
    protected int height;
    protected int speed;
    protected int SCALE;
    protected SpriteAnimation animation;
    protected Point2D hashMapPos = new Point2D(0,0);
    protected Rectangle newPosition = new Rectangle();
    protected HashMap<Point2D, PathBlock> movableArea;
    protected OriginalLevel parentPane;


    public MoveableImgView(String path, int scale, int offsetY, int width, int height, int speed, OriginalLevel parent) {
        super(path);
        this.spriteOffsetY = offsetY;
        this.SCALE = scale;
        this.speed = speed;
        this.width = width;
        this.height = height;
        movableArea = parent.getBlocks();
        this.parentPane = parent;

        //Centering and sizing image
        setFitHeight(height);
        setFitWidth(width);
        setLayoutX(-getFitWidth()/2);
        setLayoutY(-getFitHeight()/2);
    }


    //    Just movement
    protected void moveLeft(){
        if(canMove(KeyCode.A)){
            offsetAndStartAnimation(offsetMoveLeft);
            if(getTranslateX() == 0) {
                setTranslateX(parentPane.getImageWidth());
                return;
            }
            setTranslateX(getTranslateX() - speed);
        }
    }

    protected void moveRight(){
//        System.out.println(getTranslateX()+",    "+getTranslateY()+"      "+parentPane.getBlocks().get(hashMapPos).getCenter());
        if(canMove(KeyCode.D)) {
            offsetAndStartAnimation(offsetMoveRight);
            if((int)getTranslateX() == parentPane.getImageWidth()) {
                setTranslateX(speed);
                return;
            }
            setTranslateX(getTranslateX() + speed);
        }
    }
    protected void moveUp(){
        if(canMove(KeyCode.W)) {
            offsetAndStartAnimation(offsetMoveUp);
            setTranslateY(getTranslateY() - speed);
        }
    }
    protected void moveDown(){
        if(canMove(KeyCode.S)){
            offsetAndStartAnimation(offsetMoveDown);
            setTranslateY(getTranslateY() + speed);
        }
    }

    protected boolean canMove( KeyCode key){

        if (key == KeyCode.A){
            setNewPosition(-1,0);

            if (newPosition.intersects(movableArea.get(hashMapPos).getBoundsInLocal())) {
                PathBlock tempRectangle = movableArea.get(hashMapPos);
                if(newPosition.getX() >= tempRectangle.getCenter().getX()) {
                    consume();
                    return true;
                }
                if(movableArea.containsKey(new Point2D(hashMapPos.getX()-1, hashMapPos.getY()))) {
                    if(tempRectangle.getCenter().getY() == newPosition.getY()){
                        if(newPosition.getX()<=tempRectangle.getX())
                            setHashMapPos(new Point2D(hashMapPos.getX() - 1,hashMapPos.getY()));
                        consume();
                        return true;
                    }
                }
            }

            if(newPosition.getX()==-1 && hashMapPos.getY() == 15){
                System.out.println("Teleport");
                setHashMapPos(new Point2D(28,hashMapPos.getY()));
                consume();
                return true;
            }
            if(hashMapPos.equals(new Point2D(0, 14))){
                if (newPosition.getX() >= -1 && newPosition.getY() == movableArea.get(hashMapPos).getCenter().getY()) {
                    if (newPosition.getX() == -1)
                        setHashMapPos(new Point2D(27, hashMapPos.getY()));
                    consume();
                    return true;
                }
            }
            return false;
        }

        if(key == KeyCode.D) {
            setNewPosition(1, 0);
            if (newPosition.intersects(movableArea.get(hashMapPos).getBoundsInLocal())) {
                PathBlock tempRectangle = movableArea.get(hashMapPos);
                if(newPosition.getX() <= tempRectangle.getCenter().getX())
                    return true;
                if(movableArea.containsKey(new Point2D(hashMapPos.getX()+1, hashMapPos.getY()))) {
                    if(tempRectangle.getCenter().getY() == newPosition.getY()){
                        if(newPosition.getX() >=tempRectangle.getX()+tempRectangle.getWidth())
                            setHashMapPos(new Point2D(hashMapPos.getX() + 1,hashMapPos.getY()));
                        consume();
                        return true;
                    }
                }
            }
            if(hashMapPos.equals(new Point2D(27, 14))){
                if (newPosition.getX() <= parentPane.getImageWidth() + 1 && newPosition.getY() == movableArea.get(hashMapPos).getCenter().getY()) {
                    if (newPosition.getX() == parentPane.getImageWidth()+1)
                        setHashMapPos(new Point2D(0, hashMapPos.getY()));
                    consume();
                    return true;
                }
            }
            return false;
        }

        if(key == KeyCode.W) {
            setNewPosition(0,-1);
            if (newPosition.intersects(movableArea.get(hashMapPos).getBoundsInLocal())) {
                PathBlock tempRectangle = movableArea.get(hashMapPos);
                if(newPosition.getY() >= tempRectangle.getCenter().getY()) {
                    consume();
                    return true;
                }

                if(movableArea.containsKey(new Point2D(hashMapPos.getX(), hashMapPos.getY()-1))) {
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

        if(key == KeyCode.S) {
            setNewPosition(0, +1);
            if (newPosition.intersects(movableArea.get(hashMapPos).getBoundsInLocal())) {
                PathBlock tempRectangle = movableArea.get(hashMapPos);
                if (newPosition.getY() <= tempRectangle.getCenter().getY()) {
                    consume();
                    return true;
                }

                if (movableArea.containsKey(new Point2D(hashMapPos.getX(), hashMapPos.getY() + 1))) {
                    if (tempRectangle.getCenter().getX() == newPosition.getX()) {
                        if (newPosition.getY() >= tempRectangle.getY()+tempRectangle.getHeight())
                            setHashMapPos(new Point2D(hashMapPos.getX(), hashMapPos.getY() + 1));
                        consume();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void setNewPosition(int x, int y) {
        newPosition.setX(getTranslateX()+x);
        newPosition.setY(getTranslateY()+y);
    }

    public void offsetAndStartAnimation(int offsetY){
        if(animation.getOffsetY() !=offsetY)
            animation.setOffsetY(offsetY);
        if(animation.getStatus()!= Animation.Status.RUNNING)
            animation.playFromStart();
    }

    protected void consume(){
        System.out.println("Override this consume() call");
    };

    protected void comparePosition(double pointX, double pointY){
        System.out.println("Player: "+getTranslateX()+", "+getTranslateY()+
                "    Point: "+ (int)pointX+", "+(int)pointY);
    }


}
