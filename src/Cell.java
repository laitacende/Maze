import java.awt.*;

public class Cell {

	private Boolean[] walls = new Boolean[4]; // top, left, bottom, right
	private final int row;
	private final int col;
	private boolean isVisited = false;
	private Color color;

	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
		for (int i = 0; i < 4; i++) {
			walls[i] = true; // in the beginning a cell has all its walls
		}
		color = Color.BLACK;
	}

	public boolean getWalls(int index) {

		return walls[index];
	}

	public void setWalls(int index) {

		walls[index] = false;
	}

	public void setVisited() {

		isVisited = true;
	}

	public boolean getIsVisited() {

		return isVisited;
	}

	public Point getPos() {

		return new Point(row, col);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color newColor) {
		color = newColor;
	}
}
