import java.awt.*;
import java.util.LinkedList;

public class MazeSolver extends Thread {
	private static int rows = 0;
	private static int cols = 0;
	private Cell[][] field;
	private boolean[][] visited;
	private LinkedList<Cell> solution = new LinkedList<>();
	private Point end;

	public MazeSolver(int rows, int cols, Cell[][] field) {
		this.rows = rows;
		this.cols = cols;
		this.field = field;
		visited = new boolean[rows][cols];
		end = new Point(rows - 1, cols - 1);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				visited[row][col] = false;
			}
		}
	}
	public void run() {
		if (solveMaze(0, 0)) { // starting point
			int length = solution.size();
			for (int i = 0; i < length; i++) {
				try {
					this.sleep(100);
				}
				catch (InterruptedException ex) {

				}
				solution.get(i).setColor(new Color(255, 255, 102));
			}
		}
	}

	private boolean solveMaze(int row, int col) { // true if there is a solution, false if there isn't
		if (row == end.x && col == end.y) { // solution
			solution.push(field[row][col]);
			return true;
		}
		if (!visited[row][col]) { // cell was not visited
			visited[row][col] = true; // mark cell as visited
			if (!field[row][col].getWalls(0)) { // doesn't have top wall
				if (solveMaze(row - 1, col)) { // right path
					solution.push(field[row][col]);
					return true;
				}
			}
			if (!field[row][col].getWalls(1)) { // doesn't have left wall
				if (solveMaze(row, col - 1)) { // right path
					solution.push(field[row][col]);
					return true;
				}
			}
			if (!field[row][col].getWalls(2)) { // doesn't have bottom wall
				if (solveMaze(row + 1, col)) { // right path
					solution.push(field[row][col]);
					return true;
				}
			}
			if (!field[row][col].getWalls(3)) { // doesn't have right wall
				if (solveMaze(row, col + 1)) { // right path
					solution.push(field[row][col]);
					return true;
				}
			}
		}
		return false; // there is no solution
	}
}
