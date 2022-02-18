package Main.Moveables;

import Main.Consumable;
import Main.OriginalLevel;
import Main.PathBlock;
import Main.SpriteAnimation;
import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Objects;

@Setter

public class Player extends MoveableImgView {
    private static final String spritePath = "Images/PlayerSprite.png";
    private AudioClip clip;
    private Point2D hashMapPos = new Point2D(0,0);
    private OriginalLevel parent;
    private Rectangle newPlayerPos = new Rectangle();
    private HashMap<Point2D, PathBlock> movableArea;


    public Player(int scale, OriginalLevel parent) {
        super(spritePath, scale, 18,32, 32, 1);
        this.parent = parent;
        movableArea = parent.getBlocks();


        clip = new AudioClip(Objects.requireNonNull(getClass().getResource("/Sounds/wakawakabearfast.mp3")).toString());
        clip.setCycleCount(MediaPlayer.INDEFINITE);
        clip.setVolume(.2);

        //Centering and sizing image
        setAnimation(new SpriteAnimation(this, 3,0,0,18, width, height, Duration.millis(100)));

    }



    public void move(Deque<KeyCode> moveQueue){
        if(moveQueue.size()==2) {
            switch (moveQueue.peekLast()) {
                case A -> {
                    if(moveLeft(moveQueue)){
                        if(!clip.isPlaying())
                            clip.play();
                        return;
                    }
                }
                case D -> {
                    if(moveRight(moveQueue)){
                        if(!clip.isPlaying())
                            clip.play();
                        return;
                    }
                }
                case W -> {
                    if(moveUp(moveQueue)){
                        if(!clip.isPlaying())
                            clip.play();
                        return;
                    }
                }
                case S -> {
                    if(moveDown(moveQueue)){
                        if(!clip.isPlaying())
                            clip.play();
                        return;
                    }
                }
            }
        }

        switch (Objects.requireNonNull(moveQueue.peek())){
            case A -> {
                if(!clip.isPlaying())
                    clip.play();
                moveLeft();
            }
            case D -> {
                if(!clip.isPlaying())
                    clip.play();
                moveRight();
            }
            case W -> {
                if(!clip.isPlaying())
                    clip.play();
                moveUp();
            }
            case S -> {
                if(!clip.isPlaying())
                    clip.play();
                moveDown();
            }
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
    @Override
    public void moveLeft(){
        if(canMove(KeyCode.A)){
            offsetAndStartAnimation(300);
            if(getTranslateX() == 0) {
                setTranslateX(OriginalLevel.WIDTH);
                return;
            }
            setTranslateX(getTranslateX() - speed);
            return;
        }
        stopPlayer();
    }
    @Override
    public void moveRight(){
        if(canMove(KeyCode.D)) {
            offsetAndStartAnimation(0);
            if((int)getTranslateX() == OriginalLevel.WIDTH) {
                setTranslateX(speed);
                return;
            }
            setTranslateX(getTranslateX() + speed);
            return;
        }
        stopPlayer();
    }
    @Override
    public void moveUp(){
        if(canMove(KeyCode.W)) {
            offsetAndStartAnimation(448);
            setTranslateY(getTranslateY() - speed);
            return;
        }
        stopPlayer();
    }
    @Override
    public void moveDown(){
        if(canMove(KeyCode.S)){
            offsetAndStartAnimation(150);
            setTranslateY(getTranslateY() + speed);
            return;
        }
        stopPlayer();
    }


    @Override
    public boolean canMove( KeyCode key){

        if (key == KeyCode.A){
            setNewPlayerPos(-1,0);

            if (newPlayerPos.intersects(movableArea.get(hashMapPos).getBoundsInLocal())) {
                PathBlock tempRectangle = movableArea.get(hashMapPos);
                if(newPlayerPos.getX() >= tempRectangle.getCenter().getX()) {
                    consume();
                    return true;
                }
                if(movableArea.containsKey(new Point2D(hashMapPos.getX()-1, hashMapPos.getY()))) {
                    if(tempRectangle.getCenter().getY() == newPlayerPos.getY()){
                        if(newPlayerPos.getX()<=tempRectangle.getX())
                            setHashMapPos(new Point2D(hashMapPos.getX() - 1,hashMapPos.getY()));
                        consume();
                        return true;
                    }
                }
            }

            if(newPlayerPos.getX()==-1 && hashMapPos.getY() == 15){
                System.out.println("Teleport");
                setHashMapPos(new Point2D(28,hashMapPos.getY()));
                consume();
                return true;
            }
            if(hashMapPos.equals(new Point2D(0, 14))){
                if (newPlayerPos.getX() >= -1 && newPlayerPos.getY() == movableArea.get(hashMapPos).getCenter().getY()) {
                    if (newPlayerPos.getX() == -1)
                        setHashMapPos(new Point2D(27, hashMapPos.getY()));
                    consume();
                    return true;
                }
            }
            return false;
        }

        if(key == KeyCode.D) {
            setNewPlayerPos(1, 0);
            if (newPlayerPos.intersects(movableArea.get(hashMapPos).getBoundsInLocal())) {
                PathBlock tempRectangle = movableArea.get(hashMapPos);
                if(newPlayerPos.getX() <= tempRectangle.getCenter().getX())
                    return true;
                if(movableArea.containsKey(new Point2D(hashMapPos.getX()+1, hashMapPos.getY()))) {
                    if(tempRectangle.getCenter().getY() == newPlayerPos.getY()){
                        if(newPlayerPos.getX() >=tempRectangle.getX()+tempRectangle.getWidth())
                            setHashMapPos(new Point2D(hashMapPos.getX() + 1,hashMapPos.getY()));
                        consume();
                        return true;
                    }
                }
            }
            if(hashMapPos.equals(new Point2D(27, 14))){
                if (newPlayerPos.getX() <= OriginalLevel.WIDTH + 1 && newPlayerPos.getY() == movableArea.get(hashMapPos).getCenter().getY()) {
                    if (newPlayerPos.getX() == OriginalLevel.WIDTH+1)
                        setHashMapPos(new Point2D(0, hashMapPos.getY()));
                    consume();
                    return true;
                }
            }
            return false;
        }

        if(key == KeyCode.W) {
            setNewPlayerPos(0,-1);
            if (newPlayerPos.intersects(movableArea.get(hashMapPos).getBoundsInLocal())) {
                PathBlock tempRectangle = movableArea.get(hashMapPos);
                if(newPlayerPos.getY() >= tempRectangle.getCenter().getY()) {
                    consume();
                    return true;
                }

                if(movableArea.containsKey(new Point2D(hashMapPos.getX(), hashMapPos.getY()-1))) {
                    if(tempRectangle.getCenter().getX() == newPlayerPos.getX()){
                        if(newPlayerPos.getY() <=tempRectangle.getY())
                            setHashMapPos(new Point2D(hashMapPos.getX(),hashMapPos.getY()-1));
                        consume();
                        return true;
                    }
                }
            }
            return false;
        }

        if(key == KeyCode.S) {
            setNewPlayerPos(0, +1);
            if (newPlayerPos.intersects(movableArea.get(hashMapPos).getBoundsInLocal())) {
                PathBlock tempRectangle = movableArea.get(hashMapPos);
                if (newPlayerPos.getY() <= tempRectangle.getCenter().getY()) {
                    consume();
                    return true;
                }

                if (movableArea.containsKey(new Point2D(hashMapPos.getX(), hashMapPos.getY() + 1))) {
                    if (tempRectangle.getCenter().getX() == newPlayerPos.getX()) {
                        if (newPlayerPos.getY() >= tempRectangle.getY()+tempRectangle.getHeight())
                            setHashMapPos(new Point2D(hashMapPos.getX(), hashMapPos.getY() + 1));
                        consume();
                        return true;
                    }
                }
            }
        }
      return false;
    }

    public void startAnimation(SpriteAnimation animation){
        if(animation.getStatus() == Animation.Status.RUNNING)
            return;

        animation.playFromStart();
    }

    public void offsetAndStartAnimation(int offsetY){
        if(animation.getOffsetY() !=offsetY)
            animation.setOffsetY(offsetY);
        if(animation.getStatus()!= Animation.Status.RUNNING)
            startAnimation(animation);
    }

    public void comparePosition(double pointX, double pointY){
        System.out.println("Player: "+getTranslateX()+", "+getTranslateY()+
                           "Point: "+ (int)pointX+", "+(int)pointY);
    }

    public void stopPlayer(){
        animation.setMidFrame();
        animation.pause();
        clip.stop();
    }

    private void setNewPlayerPos(int x, int y) {
        newPlayerPos.setX(getTranslateX()+x);
        newPlayerPos.setY(getTranslateY()+y);
    }

    private void consume(){
        if(movableArea.get(hashMapPos).getConsumable()!= null){
            Consumable consumable = movableArea.get(hashMapPos).getConsumable();
            parent.getChildren().remove(consumable);
            movableArea.get(hashMapPos).setConsumable(null);
        }
    }
}
