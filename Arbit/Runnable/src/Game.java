import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Game {
	createScreen _root;
	Rectangle topBox;
	Rectangle bottomBox;
	Rectangle background;
	Rectangle imageBox;
	String tale = "There is no story to tell.";
	String disTale = "";
	String[] printStory = { "", "", "", "", "", "", "", "", "", "" };
	int stageWidth;
	int stageHeight;
	int bgX;
	int topY;
	int bottomY;
	int topDes;
	int bottomDes;
	int bgDes = 0;
	int taleX;
	int taleY;
	int bgY;
	int focus = 0;
	int textPos = 0;
	int imageW = 0;
	int imageH = 0;
	boolean textAnim = true;
	public ArrayList<Buttons> options = new ArrayList<Buttons>();
	private boolean flag1 = true;
	int nxt_focus = 0;
	Color barColor = new Color(0, 32, 16);
	boolean type = false;
	PlayGame playGame;
	private boolean started = false;
	private Opinion opinion;
	private static boolean noOpinion = false;
	public String id;
	boolean vacancy=true;
	ShowImage showImage;
	private BufferedImage img;

	public Game(createScreen _root, boolean type, PlayGame playGame, String id) {
		this.stageWidth = _root.stageWidth;
		this.stageHeight = _root.stageHeight;
		this._root = _root;
		bgX = stageWidth;
		bottomY = stageHeight;
		topDes = 230;
		bottomDes = stageHeight - 230;
		tale = _root.stories.get(0);
		this.playGame = playGame;
		this.type = type;
		this.id = id;
	}

	public void draw(Graphics stage) {
		onEnterFrame();

		stage.setColor(new Color(0, 89, 45));
		stage.fillRect(background.x, background.y, background.width,
				background.height);

		// draws options
		for (Buttons option : options) {
			option.draw(stage, 0, bgY);
		}
		stage.setColor(new Color(0, 70, 35));
		stage.fillRect(imageBox.x, imageBox.y, imageBox.width, imageBox.height);
		stage.setColor(new Color(210, 255, 233));
		// Draws Image
		if (_root.images.get(focus) != null) {
			drawImage(stage);
		}
		// Draws Text

		stage.setFont(new Font("Biko", Font.PLAIN, 12));
		// stage.drawString(disTale,taleX,taleY);
		for (int s = 0; s < printStory.length; s++) {
			stage.drawString(printStory[s], taleX, taleY + (s * 11));
		}
		// Draws Top/Bottom Pane
		stage.setColor(barColor);
		stage.fillRect(topBox.x, topBox.y, topBox.width, topBox.height);
		stage.fillRect(bottomBox.x, bottomBox.y, bottomBox.width,
				bottomBox.height);
		stage.setColor(new Color(210, 255, 233));
		stage.setFont(new Font("Biko", Font.BOLD, 15));
		stage.drawString(
				"Press ESC to exit | Press B to backtrack | Press R to restart",
				background.x + 55, 575);
		if(showImage!=null){
			showImage.draw(stage);
		}
		if (opinion != null) {
			opinion.draw(stage);
		}
	}

	private void drawImage(Graphics stage) {
		BufferedImage img = _root.images.get(focus);
		double imgW = img.getWidth();
		double imgH = img.getHeight();
		// System.out.println("Owidth: "+imgW+" Oheight:"+imgH);
		if (imgW > 150 || imgH > 150) {
			double newratio = (imgW) / (imgH);
			double newratio2 = (imgH) / (imgW);
			// System.out.println("ratio1: "+newratio+" ratio2:"+newratio2);

			if (newratio > newratio2) {
				imgW = 150;
				imgH = 150 * newratio2;
			} else {
				imgH = 150;
				imgW = newratio * 150;
			}
		}
		this.img = img;
		stage.drawImage(img, imageBox.x + 5, imageBox.y + 5, (int) imgW,
				(int) imgH, null);
		imageW = (int) imgW;
		imageH = (int) imgH;
	}

	private void onEnterFrame() {
		int stageMidX = stageWidth / 2;
		int stageMidY = stageHeight / 2;
		bgX -= ((bgX) / 12) + 1;
		bgY -= (bgY - bgDes) / 18;
		topY += ((topDes - topY) / 18);
		bottomY += ((bottomDes - bottomY) / 18);
		background = new Rectangle(0 + bgX, 0, stageWidth + 5,
				stageHeight + 350);
		topBox = new Rectangle(0, 0, stageWidth, topY);
		bottomBox = new Rectangle(0, bottomY, stageWidth, stageHeight);
		int spread = 0;
		if (_root.images.get(focus) != null) {
			spread = 10;
		}
		imageBox = new Rectangle(stageMidX - 232 + bgX, (stageMidY) - 80 + bgY,
				imageW + spread, imageH + spread);
		taleX = bgX + 48 + imageW;
		taleY = bgY + 245;
		if (bgX < 0 && textAnim) {
			animateText();
		}

		if (topY > 350) {
			topDes = 200;
			bottomDes = stageHeight - 200;
			focus = nxt_focus;
			tale = _root.stories.get(focus);
			disTale = "";
			textPos = 0;
			trace("its changed");
			bgDes = 16;
			flag1 = true;
			imageW = 0;
			imageH = 0;

			if (tale.indexOf("<link>") > -1) {
				trace("REDIRECTING...");
				String reference = tale.substring(6, tale.length());
				trace(reference);
				moveTo();
				nxt_focus = Integer.parseInt(reference);
				options = new ArrayList<Buttons>();

			}
		}
	}

	private void animateText() {
		if (textPos < tale.length()) {
			started = true;
			disTale += tale.charAt(textPos);
			textPos++;
			String drawString = disTale;
			int i = 0;
			while (drawString.length() > 54 && i < 9) {
				try {
					int splitPoint = drawString.lastIndexOf(" ", 53);
					printStory[i] = drawString.substring(0, splitPoint);
					drawString = drawString.substring(splitPoint + 1,
							drawString.length());
					i++;
				} catch (Exception e) {
					// fix the spamscribble
				}
			}
			printStory[i] = drawString;
			i++;
			for (; i < printStory.length; i++) {
				printStory[i] = "";
			}

		} else if (flag1) {
			getOptions(focus);
			flag1 = false;
		}
	}

	public void keyPressed(KeyEvent k) {
		if (opinion == null) {
			if (k.getKeyCode() == 27) {
				if (!type) {
					_root.removeGame();
				} else {
					trace("opinion" + noOpinion);
					if (!noOpinion) {
						opinion = new Opinion(this);
					} else {
						playGame.removeGame();
					}
				}
			}
			if (k.getKeyCode() == 66 && focus != 0) {
				moveTo();
				nxt_focus = _root.moms.get(focus);
				options = new ArrayList<Buttons>();
			}
			if (k.getKeyCode() == 82 && focus != 0) {
				trace("we needa restart");
				moveTo();
				nxt_focus = 0;
				options = new ArrayList<Buttons>();
			}
		} else {
			opinion.keyPressed(k);
		}
	}

	public void getOptions(int m) {
		for (int i = 0; i < _root.moms.size(); i++) {
			if (_root.moms.get(i) == m) {
				trace("son is found: " + _root.titles.get(i));
				Color color = new Color(0, 70, 35);
				Color highlight = new Color(0, 113, 56);
				Font font = new Font("Biko", Font.BOLD, 21);
				Buttons opt = new Buttons(0, 400 + options.size() * 31,
						_root.stageWidth, 30, _root.titles.get(i), color,
						highlight, font, i);
				options.add(opt);
			}
		}
		animateOptions();
	}

	private void animateOptions() {
		int yShift = (options.size()) * 31 + 40;
		topDes -= yShift * 3 / 5;
		bottomDes += yShift * 3 / 5;
		bgDes -= yShift / 2;
	}

	private void trace(String string) {
		System.out.println(string);
	}


	public void mouseMoved(MouseEvent m) {
		// TODO Auto-generated method stub
		if (opinion == null) {
			int mouseX = m.getX();
			int mouseY = m.getY();
			for (Buttons opt : options) {
				opt.highlight(mouseX, mouseY);
			}
		} else {
			opinion.mouseMoved(m);
		}
	}

	public void mousePressed(MouseEvent m) {
		if (opinion == null) {
			if (started) {
				disTale = tale;
				textPos = tale.length() - 1;
			}
			int mouseX = m.getX();
			int mouseY = m.getY();
			for (Buttons opt : options) {
				if (opt.isClicked(mouseX, mouseY)) {
					moveTo();
					nxt_focus = opt.ID;
					options = new ArrayList<Buttons>();
					break;
				}
			}
			
			if(vacancy && mouseX>imageBox.x && mouseX<imageBox.x+imageBox.width && mouseY>imageBox.y && mouseY<imageBox.y+imageBox.height){
				vacancy=false;
				showImage = new ShowImage(this,img);
			}
			if(showImage!=null){
				showImage.mousePressed(m);
			}
		} else {
			opinion.mousePressed(m);
		}

	}

	private void moveTo() {
		// TODO Auto-generated method stub
		topDes = 400;
		bottomDes = stageHeight - 400;
	}

	public static void noMoreOpinions() {
		noOpinion = true;
	}

}