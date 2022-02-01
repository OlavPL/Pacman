package Main;

import javafx.animation.Animation;
import javafx.geometry.Point3D;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

@Getter
@Setter

public class Player extends ImageView {
    private static final String spritePath = "Images/PlayerSprite.png";
    private final int SPRITE_OFFSET_Y = 18;
    private final int RADIUS = 16;
    private final int SPEED = 1;
    private int SCALE;
    private ArrayList<SpriteAnimation> animationList = new ArrayList<>();
    private SpriteAnimation animationRight;
    private SpriteAnimation animationLeft;
    private SpriteAnimation animationUp;
    private SpriteAnimation animationDown;
    private ArrayList<Point3D> horizontalPaths;
    private ArrayList<Point3D> verticalPaths;



    public Player(ArrayList<Point3D> horizontalPaths, ArrayList<Point3D> verticalPaths, int scale) {
        super(spritePath);
        this.SCALE = scale;
        this.horizontalPaths = horizontalPaths;
        this.verticalPaths = verticalPaths;


        //Center pos is x96 y92
        setTranslateX(12*SCALE);
        setTranslateY(12*SCALE);
        //Centering and sizing image
        setFitHeight((RADIUS+4) *2);
        setFitWidth((RADIUS+4) *2);
        setLayoutX(-getFitHeight()/2);
        setLayoutY(-getFitWidth()/2);
        setAnimationRight(new SpriteAnimation(this, 3,1,0,0, RADIUS*2,RADIUS*2, Duration.millis(150)));
        setAnimationLeft(new SpriteAnimation(this, 3,1,0,300, RADIUS*2,RADIUS*2, Duration.millis(150)));
        setAnimationUp(new SpriteAnimation(this, 3,1,0,448, RADIUS*2,RADIUS*2, Duration.millis(150)));
        setAnimationDown(new SpriteAnimation(this, 3,1,0,150, RADIUS*2,RADIUS*2, Duration.millis(150)));
        animationList.addAll(List.of(getAnimationRight(),getAnimationLeft(),getAnimationUp(), getAnimationDown()));
    }

    public void move(Deque<KeyCode> moveQueue){

        if(moveQueue.size()==2) {
            switch (moveQueue.peekLast()) {
                case A -> {
                    if(moveLeft(moveQueue)){return;}
                }
                case D -> {
                    if(moveRight(moveQueue)){return;}
                }
                case W -> {
                    if(moveUp(moveQueue)){return;}
                }
                case S -> {
                    if(moveDown(moveQueue)){return;}
                }
            }
        }

        switch (Objects.requireNonNull(moveQueue.peek())){
            case A -> moveLeft();
            case D -> moveRight();
            case W -> moveUp();
            case S -> moveDown();
        }
    }

//    Movement for queued key
    private boolean moveLeft(Deque<KeyCode> moveQueue){
        if(canMove(moveQueue.peekLast())){
            moveLeft();
            moveQueue.removeFirst();
            return true;
        }
        return false;
    }
    private boolean moveRight(Deque<KeyCode> moveQueue){
        if(canMove(moveQueue.peekLast())){
            moveRight();
            moveQueue.removeFirst();
            return true;
        }
        return false;
    }
    private boolean  moveUp(Deque<KeyCode> moveQueue){
        if(canMove(moveQueue.peekLast())){
            moveUp();
            moveQueue.removeFirst();
            return true;
        }
        return false;
    }
    private boolean moveDown(Deque<KeyCode> moveQueue){
        if(canMove(moveQueue.peekLast())){
            moveQueue.removeFirst();
            moveDown();
            return true;
        }
        return false;
    }

//    Just movement
    public void moveLeft(){
        if(canMove(KeyCode.A)){
            startAnimation(animationLeft);
            setTranslateX(getTranslateX() - SPEED);
        }
    }
    public void moveRight(){
        if(canMove(KeyCode.D)) {
            startAnimation(animationRight);
            setTranslateX(getTranslateX() + SPEED);
        }
    }
    public void  moveUp(){
        if(canMove(KeyCode.W)) {
            startAnimation(animationUp);
            setTranslateY(getTranslateY() - SPEED);
        }
    }
    public void moveDown(){
        if(canMove(KeyCode.S)){
            startAnimation(animationDown);
            setTranslateY(getTranslateY() + SPEED);
        }
    }

    public boolean canMove( KeyCode key){
        if (key == KeyCode.A)
            for (Point3D point : horizontalPaths) {
                if(getTranslateY() == point.getY()) {
                    if(getTranslateX() >= point.getX()+SPEED && getTranslateX()<= point.getX()+point.getZ() ) {
                        return true;
                    }
                }
            }
        if(key == KeyCode.D)
            for (Point3D point : horizontalPaths) {
                if(getTranslateY() == point.getY()){
                    if(getTranslateX()>= point.getX() && getTranslateX()<=point.getX()+point.getZ()-SPEED ){
                        return true;
                    }
                }
            }

        if(key == KeyCode.W)
            for (Point3D point : verticalPaths) {
                if(getTranslateX() == point.getX()){
                    if(getTranslateY()>= point.getY()+SPEED && getTranslateY()<=point.getY()+point.getZ()){
                        return true;
                    }
                }
            }

        if(key == KeyCode.S)
            for (Point3D point : verticalPaths) {
                if(getTranslateX() == point.getX()) {
                    if(getTranslateY()>= point.getY() && getTranslateY()<=point.getY()+point.getZ()-SPEED ) {
                         return true;
                    }
                }
            }
      return false;
    }

    public void startAnimation(SpriteAnimation animation){
        if(animation.getStatus() == Animation.Status.RUNNING)
            return;

        for (SpriteAnimation anim : getAnimationList()) {
            if(!anim.equals(animation)){
                anim.stop();
            }
        }
        animation.playFromStart();
    }

    public void comparePosition(double pointX, double pointY){
        System.out.println("Player: "+getTranslateX()+", "+getTranslateY()+
                       "    Point: "+ (int)pointX+", "+(int)pointY);
    }
}
