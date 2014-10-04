
import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;


public class gameApplet extends JApplet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2397850403794206025L;

	//Called when this applet is loaded into the browser.
    public void init() {
    	
    	SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// frame description
				// our Canvas
				GameCanvas canvas = new GameCanvas();
				add(canvas, BorderLayout.CENTER);
				// set it's size and make it visible
				setSize(510, 580);
				setVisible(true);	
				// now that is visible we can tell it that we will use 2 buffers to do the repaint
				// befor being able to do that, the Canvas as to be visible
				canvas.createBufferStrategy(2);
			}
		});
    }
}
