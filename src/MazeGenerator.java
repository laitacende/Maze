import javax.swing.*;
import java.awt.*;
public class MazeGenerator {

	private static JFrame frame;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MazeGenerator());
	}

	public MazeGenerator() {
		frame = new JFrame("Maze Generator");

		// modifying frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.WHITE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setMinimumSize(new Dimension(100, 100));

		Panel panel = new Panel(frame);
		frame.add(panel);

		frame.pack();
		frame.setVisible(true);
	}
}
