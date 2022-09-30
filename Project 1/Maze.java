import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {
    public StepQueue[][] mazeArray;
    public int rows;
    public int cols;

    public Maze(String textFile) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(textFile));
        this.rows = Integer.parseInt(sc.nextLine());
        this.cols = Integer.parseInt(sc.nextLine());
        this.mazeArray = new StepQueue[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mazeArray[i][j] = lineToSteps(sc.nextLine());
            }
        }
    }

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.mazeArray = new StepQueue[rows][cols];
    }

    private StepQueue lineToSteps(String line) {
        StepQueue q = new StepQueue();
        for (int i = 0; i < line.length(); i++) {
            switch (line.charAt(i)) {
                case 'U':
                    q.enqueue(Step.UP);
                    break;
                case 'D':
                    q.enqueue(Step.DOWN);
                    break;
                case 'L':
                    q.enqueue(Step.LEFT);
                    break;
                case 'R':
                    q.enqueue(Step.RIGHT);
                    break;
                default:
                    break;
            }
        }
        return q;
    }
}
