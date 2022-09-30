public class MazeSolver {
    public static StepStack findPath(Maze maze) throws MazeHasNoSolutionException {
        StepStack path = new StepStack();
        int[] pos = { 0, 0 };
        boolean[][] visited = new boolean[maze.rows][maze.cols];
        while ((pos[0] != maze.rows - 1) || (pos[1] != maze.cols - 1)) {
            visited[pos[0]][pos[1]] = true;
            boolean backtrack = false;
            StepQueue currQueue = maze.mazeArray[pos[0]][pos[1]];
            Step currStep = null;
            boolean visit = visited[pos[0]][pos[1]];
            try {
                do {
                    currStep = currQueue.dequeue();
                    int[] newpos = { pos[0], pos[1] };
                    moveInMaze(newpos, currStep);
                    visit = visited[newpos[0]][newpos[1]];

                } while (visit);
            } catch (EmptyQueueException e) {
                try {
                    Step stepback = path.pop();
                    backtrack = true;
                    revert(pos, stepback);
                    visited[pos[0]][pos[1]] = false;
                } catch (EmptyStackException f) {
                    throw new MazeHasNoSolutionException();
                }
            }
            if (!backtrack) {
                path.push(currStep);
                moveInMaze(pos, currStep);
            }
        }
        return path;
    }

    static void moveInMaze(int[] position, Step step) {
        if (step.toString().equals("U")) {
            position[0] -= 1;
        } else if (step.toString().equals("D")) {
            position[0] += 1;
        } else if (step.toString().equals("R")) {
            position[1] += 1;
        } else if (step.toString().equals("L")) {
            position[1] -= 1;
        }
    }

    static void revert(int[] position, Step stepback) {
        if (stepback.toString().equals("U")) {
            position[0] += 1;
        } else if (stepback.toString().equals("D")) {
            position[0] -= 1;
        } else if (stepback.toString().equals("R")) {
            position[1] -= 1;
        } else if (stepback.toString().equals("L")) {
            position[1] += 1;
        }
    }
}