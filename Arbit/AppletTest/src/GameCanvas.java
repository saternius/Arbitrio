import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import javax.swing.Timer;

// OK this the class where we will draw everything on the screen and take actions
// This is the grand mother of all classes as it manages what goes on where
public class GameCanvas extends Canvas implements MouseMotionListener,
		MouseListener, MouseWheelListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8465176307306678754L;
	public int stageWidth = 520;// The width of the screen
	public int stageHeight = 620;// The height of the screen
	public MenuScreen menu;// creates the menu screen
	boolean repaintInProgress = false;// checks if painting is going on
	public createScreen create;// creates the edit screen
	public int clickedX;// x coordinate of where the mouse clicks
	public int clickedY;// y coordinate of where the mouse clicks
	public int xDrag;// x coordinates of where the mouse drags
	public int yDrag;// y coordinates of where the mouse drags
	public PlayGame playGame;
	public Rectangle clickableRect;
	public Curser cursor = new Curser();
	private boolean doOnce=true;

	GameCanvas() { // creates the gameCanvas
		
		setIgnoreRepaint(true); // Ignor this, established game engine
		Chrono chrono = new Chrono(this);
		new Timer(18, chrono).start();
		this.addMouseListener(this);// adds all the eventListener
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.addKeyListener(this);
		clickableRect = new Rectangle(0, 0, 520, 620);
		
		
	}

	public void myRepaint() { // Repaints
		if (repaintInProgress)
			return;
		repaintInProgress = true;
		// ok doing the repaint on the not showed page
		BufferStrategy strategy = getBufferStrategy();// Prevents flickering
		Graphics stage = strategy.getDrawGraphics();
		
		stage.setColor(Color.black);
		stage.drawString("Your game is currently loading", 150, 250);
		stage.fillRect(138,260,Images.loader*2,10);
		stage.drawRect(138,260,186,10);
		//System.out.println(Images.loader*2);

		if (create != null) {// if the editScreen exists, paint it
			create.draw(stage);
		}
		if (playGame != null) {
			playGame.draw(stage);
		}
		if(menu!=null){
		menu.draw(stage);// draw the menu scren
		}
		cursor.draw(stage);
		if(doOnce){
			doOnce=false;
			final GameCanvas here = this;
			Thread one = new Thread() {
			    public void run() {
			    	System.out.println("TIS HAPPENING!");
			    	Images.Load();
			    	menu = new MenuScreen(here);
			    }
			};
			one.start();
			    	
		}
		
		if (stage != null)
			stage.dispose();
		// show next buffer
		strategy.show();
		// synchronized the blitter page shown
		Toolkit.getDefaultToolkit().sync();
		repaintInProgress = false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent m) {// When the mouse is pressed
		// TODO Auto-generated method stub
		cursor.mousePressed(m);
		int mouseX = m.getX();
		int mouseY = m.getY();
		if (mouseX > clickableRect.x
				&& mouseX < clickableRect.x + clickableRect.width
				&& mouseY > clickableRect.y
				&& mouseY < clickableRect.y + clickableRect.height) {

			if (m.getModifiers() == 16) {// if regular left click
				if (menu.create_btn.createFlag && create == null
						&& playGame == null) { // if the createButton is clicked
					menu.position = "Pressed";
					menu.create_btn.checkClicked(m.getX(), m.getY(), menu);
				}
				if (create != null) {
					create.mousePressed(m);
				}
				if (menu.play_btn.isClicked(m.getX(), m.getY())
						&& menu.play_btn.createFlag && create == null
						&& playGame == null) {
					menu.isConnecting=true;
					
				}
				if (playGame != null) {
					playGame.mouseClicked(m);
				}
			} else if (m.getModifiers() == 4) {// is rightclick
				if (create != null) {
					clickedX = m.getX();
					clickedY = m.getY();
					create.rightMousePressed(m);
				}
			}
			if (create != null && create.game != null) {
				create.game.mousePressed(m);
			}
			if (playGame != null && playGame.game != null) {
				playGame.game.mousePressed(m);
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent m) {// What happens when the mouse is
		cursor.mouseReleased(m);									// released
		// TODO Auto-generated method stub
		menu.position = "Released";
		if (m.getModifiers() == 4 && create != null) {// if its a right click
			for (Box box : create.boxes) {// set all the boxes x += to drag and
											// set drag=0;
				box.x += xDrag;
				box.y += yDrag;
				box.xShift = 0;
				box.yShift = 0;
			}
		}
		xDrag = 0;
		yDrag = 0;
	}

	@Override
	public void mouseDragged(MouseEvent m) {
		cursor.mouseDragged(m);
		// TODO Auto-generated method stub
		if (m.getModifiers() == 4 && create != null
				&& !(create.tut != null && create.tut.frame > 1)) {
			// System.out.println("rightDragBrah");
			// System.out.println(m);
			xDrag = m.getX() - clickedX;
			yDrag = m.getY() - clickedY;
			for (Box box : create.boxes) {
				box.xShift = xDrag;
				box.yShift = yDrag;
			}

		}

	}

	@Override
	public void mouseMoved(MouseEvent m) {
		cursor.mouseMoved(m);
		if(menu!=null){
		if (menu.yPos < 610) {
			menu.position = "Mouse is moving";
			menu.play_btn.highlight(m.getX(), m.getY());
			menu.create_btn.highlight(m.getX(), m.getY());
		}
		if (create != null) {// highlights the icons when mouse drags over it
			create.highlightElements(m.getX(), m.getY());
		}
		if (create != null && create.game != null) {
			create.game.mouseMoved(m);
		}
		if (playGame != null && playGame.game != null) {
			playGame.game.mouseMoved(m);
		}
		if (create != null) {
			create.mouseMoved(m);
		}
		}
	}

	@Override
	public void keyPressed(KeyEvent k) {// What happens when you drag a key. I
										// will have to redo this code to make
										// it more object oriented
		// TODO Auto-generated method stub

		if (create != null) {
			create.keyPressed(k);
			if (create.tut != null) {
				create.tut.keyPressed(k);
			}
		}
		if (playGame != null){
			playGame.keyPressed(k);
			if(playGame.game != null) {
			playGame.game.keyPressed(k);
			}
		}
		if (create != null && create.action != null) {
			if (create.action.title.equals("Type to set Action")) {
				create.action.title = "";
			}
			if (!create.action.title.equals("Type to set Action")
					&& create.action.title.length() > 0 && k.getKeyCode() == 10) {
				create.action.submit(create);
			}
			try {
				if (create.action.title.length() < 20) {
					if (k.getKeyCode() == 32) {
						create.action.title += "_";
					}
					if (k.getKeyCode() == 8 && create.action.title.length() > 0) {
						create.action.title = create.action.title.substring(0,
								create.action.title.length() - 1);
					} else if (k.getKeyChar() >= 48 && k.getKeyChar() <= 122) {
						create.action.title += k.getKeyChar();
					}
				}
			} catch (Exception e) {
			}
		}
		if (create != null && create.save_n_load != null) {
			create.save_n_load.keyPressed(k);
		}
		if (create != null) {
			Scene scene = create.scene;

			if (scene != null) {
				scene.textfield.keyPressed(k);
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
