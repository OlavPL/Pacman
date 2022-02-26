package PathFindingTest;

import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter

public class GhostTreeNode extends Node{
    private int valueX;
    private int valueY;
    private int sizeX;
    private int sizeY;
    private int[][] inputMatrix;
    private GhostTreeNode previouslyVisitedX;
    private GhostTreeNode previouslyVisitedY;
    private ArrayList<Integer> neighborsX;
    private ArrayList<Integer> neighborsY;
    private ArrayList<Integer> alreadyVisitedX = new ArrayList<>();
    private ArrayList<Integer> alreadyVisitedY = new ArrayList<>();
    private boolean visited = false;

    //Direction for Row and Column relative to current node
    private static int[] dr = {-1, +1, 0, 0};
    private static int[] dc = {0, 0, +1, -1};

    public GhostTreeNode(int valueX, int valueY, int sizeX, int sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        inputMatrix = new int[sizeX][sizeY];
        this.valueX = valueX;
        this.valueY = valueY;
        this.neighborsX = new ArrayList<>();
        this.neighborsY = new ArrayList<>();
    }

    public void connect(GhostTreeNode node){
        if(this.equals(node))
            throw new IllegalArgumentException("Can't connect node to itself");
        node.neighborsX.add(this.getValueX());
        node.neighborsY.add(this.getValueY());
        this.neighborsX.add(node.getValueX());
        this.neighborsY.add(node.getValueY());
    }

    public static <T> Optional<GhostTreeNode> search(GhostTreeNode target, GhostTreeNode start, Set<Integer> alreadyVisitedX, Set<Integer> alreadyVisitedY){
        Queue<Integer> queueX = new ArrayDeque<>();
        Queue<Integer> queueY = new ArrayDeque<>();
        queueX.add(start.getValueX());
        queueY.add(start.getValueY());

        GhostTreeNode currentNode = new GhostTreeNode(0,0, start.sizeX, start.sizeY);

        while (!queueX.isEmpty() && !queueY.isEmpty()){
            currentNode.setValueX(queueX.remove());
            currentNode.setValueY(queueY.remove());

            if (currentNode.getValueX() == (target.getValueX()) && currentNode.getValueY() == (target.getValueY()))
                return Optional.of(currentNode);

            alreadyVisitedX.add(currentNode.getValueX());
            alreadyVisitedY.add(currentNode.getValueY());
            currentNode.setVisited(true);


            queueX.addAll(currentNode.getNeighborsX());
            queueY.addAll(currentNode.getNeighborsY());
            queueX.removeAll(alreadyVisitedX);
            queueY.removeAll(alreadyVisitedY);
        }
        return Optional.empty();
    }
}
