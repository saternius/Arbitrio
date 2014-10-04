import java.awt.BorderLayout;
import javax.swing.*;

//Initializes the game, and establishes the stage of the game.
//So far, there really is no need to modify this part
public class GameFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GameFrame() {
		
		// frame description
		super("Arbitrio");
		//close properlly
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// our Canvas
		GameCanvas canvas = new GameCanvas();
		add(canvas, BorderLayout.CENTER);
		// set it's size and make it visible
		setSize(520, 620);
		setVisible(true);		
		// now that is visible we can tell it that we will use 2 buffers to do the repaint
		// befor being able to do that, the Canvas as to be visible
		canvas.createBufferStrategy(2);
	}
	// just to start the application
	public static void main(String[] args) {
		// instance of our stuff
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GameFrame();
			}
		});
	}
}