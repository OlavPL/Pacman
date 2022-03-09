package Main.Moveables;

import Main.Main;
import Main.PathCell;
import Main.panes.HighscorePane;
import Main.panes.OriginalLevel;
import Main.SpriteAnimation;
import PathFinding.BreadthFirstMazeSolve;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static java.lang.Integer.parseInt;

@Setter
@Getter

public class Ghost extends MovableImgView {
    protected final Image vulnerableImage = new Image("Images/VulnerableGhostBlueSprite.png");
    protected final Image eatenImage = new Image("Images/VulnerableGhostGraySprite.png");
    protected Point2D startPos;
    protected Image normalImage;
    protected Player player;
    protected BreadthFirstMazeSolve breadthFirstMazeSolve;
    protected KeyCode moveDirection = KeyCode.W;
    private boolean multiDir = false;
    protected boolean isVulnerable;
    private boolean givesPoints = false;

    public Ghost(String path, double speed, Player player, OriginalLevel parent) {
        super(path, Main.SCALE, 16, 34, 34, speed, parent);
        normalImage = new Image(path);
        this.player = player;
        setAnimation(new SpriteAnimation(this, 2,0, 0, getSpriteOffsetY(), width, height, Duration.millis(200)));
        animation.play();
    }

    public void move(){
        if(canMove()) {
            if (moveDirection == KeyCode.A) {
                moveLeft();
                offsetAndStartAnimation(200);
            } else if (moveDirection == KeyCode.D) {
                moveRight();
                offsetAndStartAnimation(0);
            } else if (moveDirection == KeyCode.W) {
                moveUp();
                offsetAndStartAnimation(300);
            } else if (moveDirection == KeyCode.S) {
                moveDown();
                offsetAndStartAnimation(100);
            }
            consume();
            if(multiDir) {
                moveRandomDirection();
                multiDir = false;
            }

            return;
        }
        moveRandomDirection();
    }


    protected boolean canMove(){
        PathCell tempRectangle = walkablePath.get(hashMapPos);

        //Left
        if (moveDirection == KeyCode.A){
            setNewPosition(-1,0);

            if (newPosition.intersects(walkablePath.get(hashMapPos).getBoundsInLocal())) {
                if(isCenter(tempRectangle.getCenter())) {
                    if(isMultiDirectional(hashMapPos))
                        multiDir = true;
                }
                if(newPosition.getX() >= tempRectangle.getCenter().getX())
                    return true;

                if(containsNextHashPosHorizontal(tempRectangle, -1))
                    return true;
            }

            return false;
        }
        //Right
        if(moveDirection == KeyCode.D) {
            setNewPosition(1, 0);
            if (newPosition.intersects(walkablePath.get(hashMapPos).getBoundsInLocal())) {

                if(isCenter(tempRectangle.getCenter())) {
                    if(isMultiDirectional(hashMapPos))
                        multiDir = true;
                }

                if(newPosition.getX() <= tempRectangle.getCenter().getX()) {
                    return true;
                }

                if(containsNextHashPosHorizontal(tempRectangle, 1))
                    return true;
            }

            return false;
        }

        //Up
        if(moveDirection == KeyCode.W) {
            setNewPosition(0,-1);
            if (newPosition.intersects(walkablePath.get(hashMapPos).getBoundsInLocal())) {
                if(newPosition.getY() >= tempRectangle.getCenter().getY()) {
                    if(isCenter(tempRectangle.getCenter())) {
                        if(isMultiDirectional(hashMapPos))
                            multiDir = true;
                    }
                    return true;
                }

                if(containsNextHashPosVertical(tempRectangle, -1))
                    return true;
            }
            return false;
        }

        //Down
        if(moveDirection == KeyCode.S) {
            setNewPosition(0, +1);
            if (newPosition.intersects(walkablePath.get(hashMapPos).getBoundsInLocal())) {
                if (newPosition.getY() <= tempRectangle.getCenter().getY()) {
                    if(isCenter(tempRectangle.getCenter())) {
                        if(isMultiDirectional(hashMapPos))
                            multiDir = true;
                    }
                    return true;
                }

                if(containsNextHashPosVertical(tempRectangle, 1))
                    return true;
            }
        }
        return false;
    }

    public void moveRandomDirection(){
        Random random = new Random();
        if(parentPane.getWalkablePath().get(hashMapPos).isGhostOnly()){
            switch (random.nextInt(4)){
                case 0 -> moveDirection = KeyCode.A;
                case 1 -> moveDirection = KeyCode.D;
                case 2, 3 -> moveDirection = KeyCode.W;
            }
            return;
        }

        boolean hit = false;
        do {
            switch (random.nextInt(4)){
                case 0 -> {
                    if(parentPane.getWalkablePath().containsKey(new Point2D(hashMapPos.getX()-1,hashMapPos.getY()))) {
                        moveDirection = KeyCode.A;
                        hit = true;
                    }
                }
                case 1 -> {
                    if(parentPane.getWalkablePath().containsKey(new Point2D(hashMapPos.getX()+1,hashMapPos.getY()))) {
                        moveDirection = KeyCode.D;
                        hit = true;
                    }

                }
                case 2 -> {
                    if(parentPane.getWalkablePath().containsKey(new Point2D(hashMapPos.getX(),hashMapPos.getY()-1))) {
                        moveDirection = KeyCode.W;
                        hit = true;
                    }
                }
                case 3 -> {
                    if(parentPane.getWalkablePath().containsKey(new Point2D(hashMapPos.getX(),hashMapPos.getY()+1))) {
                        moveDirection = KeyCode.S;
                        hit = true;
                    }
                }
            }
        }while(!hit);

    }



    public void checkPosition(ComparablePoint2D seekPosition, TreeSet<ComparablePoint2D> pathTree, double x, double y){
        seekPosition = new ComparablePoint2D(seekPosition.getX() + x, seekPosition.getY() +y);
        if(walkablePath.containsKey(seekPosition)){
            pathTree.add(seekPosition);
        }
    }

    public boolean isCenter(Point2D cellCenter){
        return newPosition.getX() == cellCenter.getX() &&
               newPosition.getY() == cellCenter.getY();
    }

    @Override
    public void consume(){
        if(this.getBoundsInParent().intersects(player.getBoundsInParent())){
            if(isVulnerable) {
                if(givesPoints) {
                    HighscorePane.setScore(parseInt(HighscorePane.getScore().getText()) + 200);
                    givesPoints = false;
                }
                returnToStart();
                return;
            }
            if(player.loseLife())
                return;

            player.gameOver();

        }
    }
    public void makeVulnerable(){
        isVulnerable = true;
        givesPoints = true;
        setImage(vulnerableImage);
        offsetAndStartAnimation(0);
    }

    public void makeDangerous(){
        isVulnerable = false;
        setImage(normalImage);
        offsetAndStartAnimation(0);
    }

    protected void returnToStart(){
        newPosition.setTranslateX(startPos.getX());
        newPosition.setTranslateY(startPos.getY());
        setImage(eatenImage);
        offsetAndStartAnimation(0);
    }
}