
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class SaveWarn {
	private boolean leave;
	private createScreen _root;
	Image pane = Images.save_warning;
	public int pane_width = pane.getWidth(null);
	public int pane_height = pane.getHeight(null);
	public Rectangle yes;
	public Rectangle no;
	public Rectangle cancel;
	public int x;
	public int y;

	public SaveWarn(createScreen createScreen, boolean type) {
		_root = createScreen;
		leave = type;
		x = _root.stageWidth / 2 - (pane_width / 2);
		y = _root.stageHeight / 2 - (pane_height / 2);
		yes = new Rectangle(x + 42, y + 60, 61, 30);
		no = new Rectangle(x + 126, y + 60, 62, 30);
		cancel = new Rectangle(x + 210, y + 60, 94, 30);
	}

	public void draw(Graphics stage) {
		stage.drawImage(pane, x, y, null);
	}

	public void mousePressed(MouseEvent m) {
		int mouseX = m.getX();
		int mouseY = m.getY();
		if (hitTest(mouseX, mouseY, yes)) {
			trace("saving file..");
			_root.functionality=true;
			_root.saveWarn=null;
			_root.save_n_load = new SaveLoad(_root, true);
		}
		if (hitTest(mouseX, mouseY, no)) {
			trace("continuing..");
			if(leave){
				_root.leader.menu.rise = true;
			}else{
				_root.leader.create = new createScreen(_root.stageWidth, _root.stageHeight,
						_root.leader, false);
			}
		}
		if (hitTest(mouseX, mouseY, cancel)) {
			trace("canceled.");
			_root.vacancy=true;
			_root.functionality=true;
			_root.saveWarn=null;
		}
	}

	private boolean hitTest(int mouseX, int mouseY, Rectangle rect) {
		if (mouseX > rect.x && mouseX < rect.x + rect.width && mouseY > rect.y && mouseY < rect.y+rect.height) {
			return true;
		}
		return false;
	}

	private void trace(String string) {
		System.out.println(string);
	}

}
