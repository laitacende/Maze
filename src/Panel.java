import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Panel extends JPanel {

	private final JFrame frame;
	private Graphics2D img;
	private static final int rows = 100;
	private static final int cols = 100;
	private final Rectangle2D[][] grid = new Rectangle2D[rows][cols];
	private static MazeActions ai = new MazeActions(rows, cols);

	public Panel(JFrame frame) {
		this.frame = frame;
		ai.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		img = (Graphics2D)g.create();
		img.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // setting rendering to achieve better looking shapes
		drawGrid();
	}


	private void setGrid() {
		double size = frame.getContentPane().getHeight() / rows; // grid is made of squares
		double x;
		double y = 0;
		for (int row = 0; row < rows; row++) {
			x = frame.getContentPane().getWidth() / 2 - size * rows / 2;
			for (int col = 0; col < cols; col++) {
				grid[row][col] = new Rectangle2D.Double(x, y, size, size);
				x += size;
			}
			y += size;
		}
	}

	private void drawGrid() {
		setGrid();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				img.setColor(ai.getColor(row, col));
				img.fill(grid[row][col]);
				if (ai.getColor(row, col) == Color.BLACK) {
					img.draw(grid[row][col]);
				}

				// drawing lines
				double size = frame.getContentPane().getHeight() / rows;
				img.setColor(Color.WHITE);
				img.setStroke(new BasicStroke(2));
				if (ai.getWalls(row, col, 0)) { // top
					Line2D line = new Line2D.Double(grid[row][col].getMinX(), grid[row][col].getMinY(), grid[row][col].getMinX() + size, grid[row][col].getMinY());
					img.draw(line);
				}
				if (ai.getWalls(row, col, 1)) { // left
					Line2D line = new Line2D.Double(grid[row][col].getMinX(), grid[row][col].getMinY(), grid[row][col].getMinX(), grid[row][col].getMinY() + size);
					img.draw(line);
				}
				if (ai.getWalls(row, col, 2)) { // bottom
					Line2D line = new Line2D.Double(grid[row][col].getMinX(), grid[row][col].getMinY() + size, grid[row][col].getMinX() + size, grid[row][col].getMinY() + size);
					img.draw(line);
				}
				if (ai.getWalls(row, col, 3)) { // right
					Line2D line = new Line2D.Double(grid[row][col].getMinX() + size, grid[row][col].getMinY(), grid[row][col].getMinX() + size, grid[row][col].getMinY() + size);
					img.draw(line);
				}
			}
		}
		repaint();
	}
}
