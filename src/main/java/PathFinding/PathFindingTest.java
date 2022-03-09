package PathFinding;


public class PathFindingTest {
    public static void main(String[] args) {
        int[][] maze = {
                {0,0,1,0,1,0,1},
                {1,1,1,0,1,1,1},
                {0,1,0,1,1,1,0},
                {1,1,1,1,0,1,1},
        };

        boolean [][] boolMaze = new boolean[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if(maze[i][j] == 1)
                    boolMaze[i][j] = true;
            }
        }

        BreadthFirstMazeSolve test = new BreadthFirstMazeSolve(boolMaze, 1, 1, 0, 6);
        System.out.println(test.solveMaze());

    }
}
