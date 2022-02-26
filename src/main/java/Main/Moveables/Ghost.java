package Main.Moveables;

import Main.panes.OriginalLevel;
import Main.SpriteAnimation;
import PathFindingTest.MiniNode;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.*;


public abstract class Ghost extends MoveableImgView {
    Player player;
    ArrayList<MiniNode> pathArray;
    Deque<Point2D> moveQueue = new ArrayDeque();
    public Ghost(String path, int scale, int speed, Player player, OriginalLevel parent) {
        super(path, scale, 16, 34, 34, speed, parent);
        this.player = player;
        setAnimation(new SpriteAnimation(this, 2,0, 0, getSpriteOffsetY(), width, height, Duration.millis(200)));
        animation.play();
    }

//    public void move(KeyCode key){
//        switch (key){
//            case A -> {
//                moveLeft();
//                offsetAndStartAnimation(200);
//            }
//            case D -> {
//                moveRight();
//                offsetAndStartAnimation(0);
//            }
//            case W -> {
//                moveUp();
//                offsetAndStartAnimation(300);
//            }
//            case S -> {
//                moveDown();
//                offsetAndStartAnimation(100);
//            }
//        }
//    }
public void move(){
    Point2D point = new Point2D(hashMapPos.getX()-pathArray.get(1).getCurrentRow(), hashMapPos.getY()-pathArray.get(1).getCurrentCol());

        if (point.getY() > 0) {
            moveUp();
            offsetAndStartAnimation(300);
        }
        if (point.getX() < 0) {
            moveLeft();
            offsetAndStartAnimation(200);
        }
        if (point.getY() > 0) {
            moveDown();
            offsetAndStartAnimation(100);
        }
        if (point.getX() > 0) {
            moveRight();
            offsetAndStartAnimation(0);
        }
    }

    public void getPathToPlayer(Player player){

    }

    public void checkCurrentPosition(ComparablePoint2D seekPosition, TreeSet<ComparablePoint2D> pathTree){
        checkPosition(seekPosition, pathTree,0,0);
        checkPosition(seekPosition, pathTree,1,0);
        checkPosition(seekPosition, pathTree,1,1);
        checkPosition(seekPosition, pathTree,0,1);
    }

    public void checkPosition(ComparablePoint2D seekPosition, TreeSet<ComparablePoint2D> pathTree, double x, double y){
        seekPosition = new ComparablePoint2D(seekPosition.getX()+x, seekPosition.getY() +y);
        if(movableArea.containsKey(seekPosition)){
            pathTree.add(seekPosition);
        }
    }
}
