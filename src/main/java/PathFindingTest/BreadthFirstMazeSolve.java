package PathFindingTest;

import lombok.Getter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
@Getter

public class BreadthFirstMazeSolve extends MiniNode {
    protected int[][] mazeMatrix;
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

    public BreadthFirstMazeSolve(int[][] mazeMatrix, int startR, int startC, int endR, int endC){
        super(startR, startC);
        this.mazeMatrix = mazeMatrix;
        visited = new boolean[mazeMatrix.length][mazeMatrix[0].length];
        this.startR = startR;
        this.startC = startC;
        this.endR = endR;
        this.endC = endC;
        System.out.println("End Pos: "+endR+", "+endC);
    }

    public ArrayList<MiniNode> solveMaze(){
        queue.add(new MiniNode(startR, startC, parentPath));
        visited[startR][startC] = true;

        while (queue.size() > 0){
            MiniNode tempNode = queue.remove();
            System.out.println("Current Check: "+tempNode.currentRow+", "+tempNode.currentCol);

            if(tempNode.currentRow == endR && tempNode.currentCol == endC){
                System.out.println("End Reached!");
                parentPath = tempNode.getParentPath();
                reachedEnd = true;
                break;
            }

            exploreNeighbours(tempNode);
            nodesInLeftInQueue--;

            if(nodesInLeftInQueue == 0){
                nodesInLeftInQueue = nodesInNextQueue;
                nodesInNextQueue = 0;
                moveCount++;
            }
        }
        if(reachedEnd) {
            System.out.println("Step Count: "+moveCount);
            return parentPath;
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
            if(mazeMatrix[newNode.currentRow][newNode.currentCol] != 1) {
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
}
