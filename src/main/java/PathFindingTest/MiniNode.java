package PathFindingTest;

import javafx.scene.Node;
import lombok.Getter;
import java.util.ArrayList;

@Getter

public class MiniNode extends Node {
    protected int currentRow, currentCol;
    private ArrayList<MiniNode> parentPath;

    public MiniNode(int row, int col){
        currentRow = row;
        currentCol = col;
        parentPath = new ArrayList<>();
        parentPath.add(this);
    }

    public MiniNode(int row, int col, ArrayList<MiniNode> path){
        this(row, col);
        parentPath = new ArrayList<>();
        parentPath.addAll(path);
        parentPath.add(this);
    }
}
