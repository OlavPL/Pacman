package PathFindingTest;


public class PathFindingTest {
    public static void main(String[] args) {
        int[][] maze = {
                {0,0,1,0,1,0,1},
                {1,1,1,0,1,1,1},
                {0,1,0,1,1,1,0},
                {1,1,1,1,0,1,1},
        };

        BreadthFirstMazeSolve test = new BreadthFirstMazeSolve(maze, 1, 1, 0, 6);
        System.out.println(test.solveMaze());

    }
}
