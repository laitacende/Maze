import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeActions extends Thread {
	private static int rows = 0;
	private static int cols = 0;
	private Cell current;
	private Cell next;
	private Cell[][] field;
	private Stack<Cell> visited = new Stack<>();
	private ArrayList<Cell> neighbours = new ArrayList<>();
	private final Color visitedColor = new Color(3, 196, 210);
	private final Color currentColor = new Color(150,250,200);
	private boolean isFinished = false;


	public boolean getIsFinished() {
		return isFinished;
	}

	public void setIsFinished() {
		isFinished = false;
	}

	public MazeActions(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		field = new Cell[rows][cols];
		for (int row = 0 ; row < rows; row++) { // grid
			for (int col = 0; col < cols; col++) {
				field[row][col] = new Cell(row, col);
			}
		}
	}

	public Color getColor(int row, int col) {
		return field[row][col].getColor();
	}

	public boolean getWalls(int row, int col, int index) {
		return field[row][col].getWalls(index);
	}

	public void run() {
		createMaze();
		int value = JOptionPane.showConfirmDialog(null, "Czy chcesz zobaczyc rozwiazanie labiryntu?", "Rozwiazywanie labiryntu", JOptionPane.YES_NO_OPTION);
		if (value == JOptionPane.YES_OPTION) {
			MazeSolver ms = new MazeSolver(rows, cols, field);
			ms.start();
		}
	}

	private void createMaze() {
		// step 1
		current = field[0][0];
		current.setVisited(); // cell was visited
		visited.push(current);

		// step 2
		while (!visited.isEmpty()) {
			/*try {
				this.sleep(100);
			}
			catch (InterruptedException ex) {

			}*/
			
			// step 2.1
			current.setColor(currentColor);

			// step 2.2
			if (checkNeighbours(current.getPos())) { // there are unvisited neighbours
				int index = chooseNeighbour();
				next = neighbours.get(index);

				// step 2.3
				removeWall(current, next);

				// step 2.4
				next.setVisited();
				current.setColor(visitedColor);
				next.setColor(currentColor);
				visited.push(next);
				current = next;
			} else {
				/// pop from stack, backtracking
				current.setColor(visitedColor);
				current = visited.pop();
			}
		}
		isFinished = true;
	}

	private boolean checkNeighbours(Point pos) {
		neighbours.clear();
		if (pos.x - 1 >= 0 && !field[pos.x - 1][pos.y].getIsVisited()) { // top neighbour
			neighbours.add(field[pos.x - 1][pos.y]);
		}
		if (pos.x + 1 < rows  && !field[pos.x + 1][pos.y].getIsVisited()) { // bottom neighbour
			neighbours.add(field[pos.x + 1][pos.y]);
		}
		if (pos.y + 1  < cols  && !field[pos.x][pos.y + 1].getIsVisited()) { // right neighbour
			neighbours.add(field[pos.x][pos.y + 1]);
		}
		if (pos.y - 1  >= 0 && !field[pos.x][pos.y - 1].getIsVisited()) { // left neighbour
			neighbours.add(field[pos.x][pos.y - 1]);
		}
		return !neighbours.isEmpty();
	}

	private int chooseNeighbour() {
		Random rand = new Random();
		int upperbound = neighbours.size();
		return rand.nextInt(upperbound);
	}

	private void removeWall(Cell current, Cell next) {
		if (next.getPos().x - current.getPos().x != 0) { // the neighbour is either top or bottom
			if (next.getPos().x - current.getPos().x == -1) { // top
				next.setWalls(2);
				current.setWalls(0);
			} else if (next.getPos().x - current.getPos().x == 1) { // bottom
				next.setWalls(0);
				current.setWalls(2);
			}
		}
		if (next.getPos().y - current.getPos().y != 0) { // the neighbour is either right or left
			if (next.getPos().y - current.getPos().y == -1) { // left
				next.setWalls(3);
				current.setWalls(1);
			} else if (next.getPos().y - current.getPos().y == 1) { // right
				next.setWalls(1);
				current.setWalls(3);
			}
		}
	}
}
