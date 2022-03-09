package PathFinding;

import Main.Moveables.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
@Getter
@Setter

public class BreadthFirstMazeSolve extends MiniNode {
    protected boolean[][] mazeMatrix;
    protected int startC, startR;
    protected int endR;
    protected int endC;
    protected int currentRow, currentCol;
    protected Queue<MiniNode> queue = new ArrayDeque<>();

    protected  static int[] directionsR = {-1, 1, 0, 0};
    protected static int[] directionsC = {0, 0, -1, 1};

    protected int moveCount;
    private ArrayList<MiniNode> parentPath = new ArrayList<>();
    protected int nodesInLeftInQueue = 1;
    protected int nodesInNextQueue = 0;

    protected boolean reachedEnd = false;
    protected boolean[][] visited;

    public BreadthFirstMazeSolve(int row, int col, ArrayList<MiniNode> path){
        super(row, col, path);
    }

    public BreadthFirstMazeSolve(boolean[][] mazeMatrix, int startR, int startC, int endR, int endC){
        super(startR,startC);
        this.mazeMatrix = mazeMatrix;
        visited = new boolean[mazeMatrix.length][mazeMatrix[0].length];
        this.startR = startR;
        this.startC = startC;
        this.endR = endR;
        this.endC = endC;
    }

    public ArrayList<MiniNode> solveMaze(){
        queue.add(new MiniNode(startR, startC, parentPath));
        visited[startR][startC] = true;

        while (queue.size() > 0){
            MiniNode tempNode = queue.remove();

            if(tempNode.currentRow == endR && tempNode.currentCol == endC){
                parentPath = tempNode.getParentPath();
                reachedEnd = true;
                moveCount = 0;
                tempNode.getParentPath().remove(0);
                return tempNode.getParentPath();
            }

            exploreNeighbours(tempNode);
            nodesInLeftInQueue--;

            if(nodesInLeftInQueue == 0){
                nodesInLeftInQueue = nodesInNextQueue;
                nodesInNextQueue = 0;
                moveCount++;
            }
        }
        return null;
    }

    public void exploreNeighbours(MiniNode node){
        for (int i = 0; i < 4; i++) {
            MiniNode newNode = new MiniNode(node.getCurrentRow()+directionsR[i], node.getCurrentCol()+directionsC[i], node.getParentPath());

            if(newNode.currentRow < 0 || newNode.currentRow >= visited.length) {
                continue;
            }
            if(newNode.currentCol < 0 || newNode.currentCol >= visited[0].length) {
                continue;
            }

            if(visited[newNode.currentRow][newNode.currentCol]) {
                continue;
            }
            if(!mazeMatrix[newNode.currentRow][newNode.currentCol]) {
                continue;
            }


            queue.add(newNode);
            visited[newNode.currentRow][newNode.currentCol] = true;
            nodesInNextQueue++;
        }
    }

    public String toString(ArrayList<MiniNode> parentPath){
        String string = "Pos: ";
        for (MiniNode node : parentPath) {
            string += "["+node.currentRow+", "+node.currentCol+"], ";
        }
        return string;
    }

    public void newSearch(Player player, int startR, int startC){
        setStartR(startR);
        setStartC(startC);
        setCurrentRow(startR);
        setCurrentCol(startC);
        setEndR((int)player.getHashMapPos().getY());
        setEndC((int)player.getHashMapPos().getX());
        setVisited(new boolean[visited.length][visited[0].length]);
    }
}