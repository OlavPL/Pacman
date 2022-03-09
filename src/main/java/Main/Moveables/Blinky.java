package Main.Moveables;

import Main.PathCell;
import Main.panes.OriginalLevel;
import PathFinding.BreadthFirstMazeSolve;
import PathFinding.MiniNode;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Blinky extends Ghost{
    Deque<Point2D> moveQueue = new ArrayDeque();
    public Blinky(int scale, double speed, Player player, OriginalLevel parent) {
        super("/Images/RedGhostSprite.png", speed, player, parent);

    }


    public void move(){

        if(moveQueue.isEmpty()){
            return;
        }

        //Up
        if (moveQueue.getFirst().getY() == -1) {
            if(canMove(KeyCode.W)) {
                moveUp();
                offsetAndStartAnimation(300);
            }
        }

        //Left
        if (moveQueue.getFirst().getX() == -1) {
            if(canMove(KeyCode.A)) {
                moveLeft();
                offsetAndStartAnimation(200);
            }
        }
        //Down
        if (moveQueue.getFirst().getY() == 1) {
            if(canMove(KeyCode.S)) {
                moveDown();
                offsetAndStartAnimation(100);
            }
        }

        //Right
        if (moveQueue.getFirst().getX() == 1 ) {
            if(canMove(KeyCode.D)) {
                moveRight();
                offsetAndStartAnimation(0);
            }
        }
    }

    @Override
    protected boolean canMove(KeyCode key){
        PathCell tempRectangle = walkablePath.get(hashMapPos);
        if (key == KeyCode.A){
            setNewPosition(-1,0);

            if (newPosition.intersects(walkablePath.get(hashMapPos).getBoundsInLocal())) {
                if(newPosition.getX() >= tempRectangle.getCenter().getX()) {
                    if(newPosition.getX() == tempRectangle.getCenter().getX() &&
                       newPosition.getY() == tempRectangle.getCenter().getY()) {
                        if(isVulnerable) {
                            pathToTarget(startPos.getX(), startPos.getY());
                        }else
                            pathToTarget(player.hashMapPos.getX(), player.hashMapPos.getY());
                    }
                    consume();
                    return true;
                }
                if(walkablePath.containsKey(new Point2D(hashMapPos.getX()-1, hashMapPos.getY()))) {
                    if(tempRectangle.getCenter().getY() == newPosition.getY()){
                        if(newPosition.getX()<=tempRectangle.getX()) {
                            setHashMapPos(new Point2D(hashMapPos.getX() - 1, hashMapPos.getY()));
                        }
                        consume();
                        return true;
                    }
                }
            }

            if(newPosition.getX()==-1 && hashMapPos.getY() == 15){
                System.out.println("Teleport");
                if(isVulnerable) {
                            pathToTarget(startPos.getX(), startPos.getY());
                        }else
                            pathToTarget(player.hashMapPos.getX(), player.hashMapPos.getY());
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

        if(key == KeyCode.D) {
            setNewPosition(1, 0);
            if (newPosition.intersects(walkablePath.get(hashMapPos).getBoundsInLocal())) {
                if(newPosition.getX() <= tempRectangle.getCenter().getX()) {
                    if(newPosition.getX() == tempRectangle.getCenter().getX() &&
                            newPosition.getY() == tempRectangle.getCenter().getY()) {
                        if(isVulnerable) {
                            pathToTarget(startPos.getX(), startPos.getY());
                        }else
                            pathToTarget(player.hashMapPos.getX(), player.hashMapPos.getY());
                    }
                    return true;
                }

                if(walkablePath.containsKey(new Point2D(hashMapPos.getX()+1, hashMapPos.getY()))) {
                    if( newPosition.getY() == tempRectangle.getCenter().getY()){
                        if(newPosition.getX() == tempRectangle.getCenter().getX()) {
                            if(isVulnerable) {
                            pathToTarget(startPos.getX(), startPos.getY());
                        }else
                            pathToTarget(player.hashMapPos.getX(), player.hashMapPos.getY());
                        }
                        if(newPosition.getX() >=tempRectangle.getX()+tempRectangle.getWidth()) {
                            setHashMapPos(new Point2D(hashMapPos.getX() + 1, hashMapPos.getY()));
                        }
                        consume();
                        return true;
                    }
                }
            }
            if(hashMapPos.equals(new Point2D(27, 14))){
                if (newPosition.getX() <= parentPane.getImageWidth() + 1 && newPosition.getY() == walkablePath.get(hashMapPos).getCenter().getY()) {
                    if (newPosition.getX() == parentPane.getImageWidth()+1) {
                        setHashMapPos(new Point2D(0, hashMapPos.getY()));
                    }

                    consume();
                    return true;
                }
            }
            moveRandomDirection();
            return false;
        }

        if(key == KeyCode.W) {
            setNewPosition(0,-1);
            if (newPosition.intersects(walkablePath.get(hashMapPos).getBoundsInLocal())) {
                if(newPosition.getY() >= tempRectangle.getCenter().getY()) {
                    if(newPosition.getX() == tempRectangle.getCenter().getX() &&
                            newPosition.getY() == tempRectangle.getCenter().getY()) {
                        if(isVulnerable) {
                            pathToTarget(startPos.getX(), startPos.getY());
                        }else
                            pathToTarget(player.hashMapPos.getX(), player.hashMapPos.getY());
                    }

                    consume();
                    return true;
                }

                if(walkablePath.containsKey(new Point2D(hashMapPos.getX(), hashMapPos.getY()-1))) {
                    if(tempRectangle.getCenter().getX() == newPosition.getX()){
                        if(newPosition.getY() <=tempRectangle.getY()) {
                            setHashMapPos(new Point2D(hashMapPos.getX(), hashMapPos.getY() - 1));
                        }
                        consume();
                        return true;
                    }
                }
            }
            return false;
        }

        if(key == KeyCode.S) {
            setNewPosition(0, +1);
            if (newPosition.intersects(walkablePath.get(hashMapPos).getBoundsInLocal())) {
                if (newPosition.getY() <= tempRectangle.getCenter().getY()) {
                    if(newPosition.getX() == tempRectangle.getCenter().getX() &&
                       newPosition.getY() == tempRectangle.getCenter().getY()) {
                        if(isVulnerable) {
                            pathToTarget(startPos.getX(), startPos.getY());
                        }else
                            pathToTarget(player.hashMapPos.getX(), player.hashMapPos.getY());
                    }

                    consume();
                    return true;
                }

                if (walkablePath.containsKey(new Point2D(hashMapPos.getX(), hashMapPos.getY() + 1))) {
                    if (tempRectangle.getCenter().getX() == newPosition.getX()) {
                        if(newPosition.getY() == walkablePath.get(hashMapPos).getCenter().getY()) {
                            if(isVulnerable) {
                            pathToTarget(startPos.getX(), startPos.getY());
                        }else
                            pathToTarget(player.hashMapPos.getX(), player.hashMapPos.getY());
                        }

                        if (newPosition.getY() >= tempRectangle.getY()+tempRectangle.getHeight()) {
                            if(isVulnerable) {
                            pathToTarget(startPos.getX(), startPos.getY());
                        }else
                            pathToTarget(player.hashMapPos.getX(), player.hashMapPos.getY());
                            setHashMapPos(new Point2D(hashMapPos.getX(), hashMapPos.getY() + 1));
                        }
                        consume();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void pathToTarget(double x, double y){
        if(hashMapPos.equals(new Point2D(x, y))){
            moveRandomDirection();
            return;
        }
        breadthFirstMazeSolve = new BreadthFirstMazeSolve(OriginalLevel.getMapMatrix(),(int)hashMapPos.getY(),
                (int)hashMapPos.getX(), (int)y, (int)x);

//        breadthFirstMazeSolve.newSearch(player, (int)hashMapPos.getY(), (int)hashMapPos.getX());
        ArrayList<MiniNode> path = breadthFirstMazeSolve.solveMaze();
        moveQueue.clear();
        for (MiniNode node : path) {
            if(node.getCurrentRow() == hashMapPos.getY() && node.getCurrentCol() == hashMapPos.getX())
                continue;

            moveQueue.add(new Point2D(node.getCurrentCol()-hashMapPos.getX(), node.getCurrentRow()-hashMapPos.getY()));
        }
    }

    @Override
    protected void returnToStart(){
        pathToTarget(startPos.getX(), startPos.getY());
//        isVulnerable = false;
//        setImage(normalImage);
//        offsetAndStartAnimation(0);
    }

    @Override
    public void makeDangerous(){
        this.isVulnerable = false;
        setImage(normalImage);
        offsetAndStartAnimation(0);
        pathToTarget(player.hashMapPos.getX(), player.hashMapPos.getY());
    }
}
