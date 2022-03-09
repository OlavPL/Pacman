package Main.Moveables;

import Main.Consumable;
import Main.PowerSnack;
import Main.panes.BottomPane;
import Main.panes.GameOverPane;
import Main.panes.HighscorePane;
import Main.panes.OriginalLevel;
import Main.PathCell;
import Main.SpriteAnimation;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import java.util.Deque;
import java.util.HashMap;
import java.util.Objects;

import static java.lang.Integer.parseInt;

@Setter
@Getter

public class Player extends MovableImgView {
    private static final String spritePath = "Images/PlayerSprite.png";
    private static int lives = 3;
    private AudioClip audioClip;
    private Rectangle newPlayerPos = new Rectangle();
    private HashMap<Point2D, PathCell> movableArea;
    private BottomPane bottomPane;


    public Player(int scale, OriginalLevel parent, BottomPane bottomPane) {
        super(spritePath, scale, 18,32, 32, 1, parent);
        this.offsetMoveLeft = 300;
        this.offsetMoveRight = 0;
        this.offsetMoveUp = 448;
        this.offsetMoveDown = 150;
        this.bottomPane = bottomPane;
        movableArea = parent.getWalkablePath();

        audioClip = new AudioClip(Objects.requireNonNull(getClass().getResource("/Sounds/wakawakabearfast.mp3")).toString());
        audioClip.setCycleCount(MediaPlayer.INDEFINITE);
        audioClip.setVolume(.2);

        //Centering and sizing image
        setAnimation(new SpriteAnimation(this, 3,0,0,18, width, height, Duration.millis(100)));

    }



    public void move(Deque<KeyCode> moveQueue){
        if(moveQueue.size()==2) {
            switch (moveQueue.peekLast()) {
                case A -> {
                    if(moveLeft(moveQueue)){
                        if(!audioClip.isPlaying())
                            audioClip.play();
                        return;
                    }
                }
                case D -> {
                    if(moveRight(moveQueue)){
                        if(!audioClip.isPlaying())
                            audioClip.play();
                        return;
                    }
                }
                case W -> {
                    if(moveUp(moveQueue)){
                        if(!audioClip.isPlaying())
                            audioClip.play();
                        return;
                    }
                }
                case S -> {
                    if(moveDown(moveQueue)){
                        if(!audioClip.isPlaying())
                            audioClip.play();
                        return;
                    }
                }
            }
        }

        switch (Objects.requireNonNull(moveQueue.peek())){
            case A -> {
                if(!audioClip.isPlaying())
                    audioClip.play();
                moveLeft();
            }
            case D -> {
                if(!audioClip.isPlaying())
                    audioClip.play();
                moveRight();
            }
            case W -> {
                if(!audioClip.isPlaying())
                    audioClip.play();
                moveUp();
            }
            case S -> {
                if(!audioClip.isPlaying())
                    audioClip.play();
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
      public void moveLeft(){
        if(canMove(KeyCode.A)){
            offsetAndStartAnimation(offsetMoveLeft);
            if(getTranslateX() == 0) {
                setTranslateX(parentPane.getImageWidth());
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
            offsetAndStartAnimation(offsetMoveRight);
            if((int)getTranslateX() == parentPane.getImageWidth()) {
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
            offsetAndStartAnimation(offsetMoveUp);
            setTranslateY(getTranslateY() - speed);
            return;
        }
        stopPlayer();
    }
    @Override
    public void moveDown(){
        if(canMove(KeyCode.S)){
            offsetAndStartAnimation(offsetMoveDown);
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
                PathCell tempRectangle = movableArea.get(hashMapPos);
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
                PathCell tempRectangle = movableArea.get(hashMapPos);
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
                if (newPlayerPos.getX() <= parentPane.getImageWidth() + 1 && newPlayerPos.getY() == movableArea.get(hashMapPos).getCenter().getY()) {
                    if (newPlayerPos.getX() == parentPane.getImageWidth()+1)
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
                PathCell tempRectangle = movableArea.get(hashMapPos);
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
                PathCell tempRectangle = movableArea.get(hashMapPos);
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

    public void comparePosition(double pointX, double pointY){
        System.out.println("Player: "+getTranslateX()+", "+getTranslateY()+
                           "Point: "+ (int)pointX+", "+(int)pointY);
    }

    public void stopPlayer(){
        animation.setMidFrame();
        animation.pause();
        audioClip.stop();
    }

    private void setNewPlayerPos(int x, int y) {
        newPlayerPos.setX(getTranslateX()+x);
        newPlayerPos.setY(getTranslateY()+y);
    }

    @Override
    protected void consume(){
        if(movableArea.get(hashMapPos).getConsumable()!= null){
            Consumable consumable = movableArea.get(hashMapPos).getConsumable();
            if(consumable.getClass() == PowerSnack.class)
                parentPane.getGameUpdate().makeVulnerable();

            parentPane.getChildren().remove(consumable);
            parentPane.getSnackList().remove(consumable);
            movableArea.get(hashMapPos).setConsumable(null);
            HighscorePane.setScore(parseInt(HighscorePane.getScore().getText())+10);

            if(parentPane.getSnackList().isEmpty()){
                Main.Main.gameRestart(this);
            }
        }
    }

    public boolean loseLife(){
        if(Player.getLives() > 1) {
            Player.reduceLives();
            getAudioClip().stop();
            getBottomPane().removeLife();
            Main.Main.gameRestart(this);
             return true;
        }
        return false;
    }

    public void gameOver(){
        parentPane.getGameUpdate().stop();
        bottomPane.resetLives();
        Main.Main.getRoot().setCenter(new GameOverPane(this));
    }

    public static int getLives(){return lives;}
    public static void setLives(int n){lives = n;}
    public static void reduceLives(){lives--;}
}