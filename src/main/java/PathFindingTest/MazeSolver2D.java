package PathFindingTest;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
@Getter

public class MazeSolver2D extends Node {
    int col, row;
    int[][] mazeMatrix;
    int startC, startR;
    int endR;
    int endC;
    int currentRow, currentCol;
    Queue<Integer> rowQ = new ArrayDeque<>();
    Queue<Integer> colQ = new ArrayDeque<>();

    static int[] directionsR = {-1, 1, 0, 0};
    static int[] directionsC = {0, 0, -1, 1};

    int moveCount;
    ArrayList<Point2D> parentPath = new ArrayList<>();
    int nodesInLeftInQueue = 1;
    int nodesInNextQueue = 0;

    boolean reachedEnd = false;
    boolean[][] visited;

    public MazeSolver2D(int row, int col){
        currentRow = row;
        currentCol = col;
    }

    public MazeSolver2D(int[][] mazeMatrix, int startR, int startC, int endR, int endC){
        this.mazeMatrix = mazeMatrix;
        visited = new boolean[mazeMatrix.length][mazeMatrix[0].length];
        this.startR = startR;
        this.startC = startC;
        this.endR = endR;
        this.endC = endC;
        System.out.println("End Pos: "+endR+", "+endC);
    }

    public int solveMaze(){
        rowQ.add(startR);
        colQ.add(startC);
        visited[startR][startC] = true;

        while (rowQ.size() >0){
            row = rowQ.remove();
            col = colQ.remove();
            System.out.println("Current Check: "+row+", "+col);
            if(row == endR && col == endC){
                System.out.println("End Reached!");
                reachedEnd = true;
                break;
            }

            exploreNeighbours(row, col);
            nodesInLeftInQueue--;

            if(nodesInLeftInQueue == 0){
                nodesInLeftInQueue = nodesInNextQueue;
                nodesInNextQueue = 0;
                moveCount++;
            }
        }
        if(reachedEnd)
            return moveCount;
        return -1;
    }

    public void exploreNeighbours( int row, int col){
        for (int i = 0; i < 4; i++) {
            int newR = row+directionsR[i];
            int newC = col+directionsC[i];

            if(newR < 0 || newR >= visited.length)
                continue;
            if(newC < 0 || newC >= visited[0].length)
                continue;

            if(visited[newR][newC])
                continue;
            if(mazeMatrix[newR][newC] != 1)
                continue;


            rowQ.add(newR);
            colQ.add(newC);
            visited[newR][newC] = true;
            nodesInNextQueue++;
        }
    }
}
