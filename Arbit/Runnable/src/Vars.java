
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class Vars {
	Image new_var = Images.create_var;
	Image var_set = Images.varSettings;
	Image glow_pane =Images.variablePaneGlow;
	Image name_glow =Images.varNameGlow;
	Image type_opts = Images.type_opts;
	Image var_value =Images.varValue;
	Image var_val_high =Images.VarvalHigh;
	public int x;
	public int y;
	public boolean focused;
	public Variables _root;
	public Image pane = new_var;
	public boolean newbie = true;
	private Functions funk = new Functions();
	Rectangle rect;
	Rectangle close;
	Rectangle name;
	Rectangle type;
	Rectangle value;
	String name_str = "Name";
	String type_str = "Boolean";
	String value_str = "True";
	Fonts font = new Fonts();
	private boolean name_highlight = false;
	private boolean type_highlight = false;
	String box1 = "Number";
	String box2 = "Text";
	Rectangle Box1;
	Rectangle Box2;
	int text_shift = 0;
	private boolean show_value_opts;
	private Rectangle boolean_Rect;
	private boolean highlight_value;
	
	private boolean Text = false;

	public Vars(int x, int y, Variables parent) {
		this.x = x;
		this.y = y;
		this._root = parent;
		rect = new Rectangle(x, y + 5, 380, 27);
		close = new Rectangle(x + 355, y + 5, 30, 27);
		name = new Rectangle(x + 2, y + 5, 95, 27);
		type = new Rectangle(x + 105, y + 5, 95, 27);
		value = new Rectangle(x + 210, y + 5, 140, 27);
		boolean_Rect = new Rectangle(x + 210, y + 5, 140, 28);
	}

	public void draw(Graphics stage) {
		if (newbie) {
			stage.drawImage(new_var, x, y, null);
		} else {
			if (focused) {
				stage.drawImage(glow_pane, x - 3, y - 3, null);
			} else {
				stage.drawImage(var_set, x, y, null);
			}

			if (name_highlight) {
				stage.drawImage(name_glow, x + 1, y, null);
			}
			if (highlight_value) {
				stage.drawImage(var_val_high, x + 207, y + 1, null);
			}
			if (type_highlight) {
				stage.drawImage(type_opts, x + 106, y + 24, null);
				stage.drawString(box1, x + 125, y + 41);
				stage.drawString(box2, x + 137 + text_shift, y + 63);
			}
			Fonts.setSize(16);
			stage.setFont(Fonts.regFont);

			@SuppressWarnings("deprecation")
			FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(
					Fonts.regFont);
			int w = fm.stringWidth(name_str);
			stage.drawString(name_str, x + 48 - (w / 2), y + 18);
			w = fm.stringWidth(type_str);
			stage.drawString(type_str, x + 153 - (w / 2), y + 18);
			w = fm.stringWidth(value_str);
			stage.drawString(value_str, x + 275 - (w / 2), y + 18);
		}

	}

	public void mousePressed(MouseEvent m) {
		int mouseX = m.getX();
		int mouseY = m.getY();

		Rectangle recty = new Rectangle(rect.x, rect.y, rect.width - 232,
				rect.height);
		if (newbie && funk.hitTest(recty, mouseX, mouseY)) {
			pane = var_set;
			newbie = false;
			y += 6;
			_root.clearFocused();
			focused = true;
		}
		if (!newbie) {
			if (funk.hitTest(value, mouseX, mouseY)) {
				if (type_str.equals("Boolean")) {
					show_value_opts = true;
				} else if (type_str.equals("Number")) {
					highlight_value = true;
					Text = false;
				} else {
					highlight_value = true;
					Text = true;
				}
			} else {
				highlight_value = false;
			}
			if (show_value_opts) {
				if (funk.hitTest(boolean_Rect, mouseX, mouseY)) {
					if (value_str.equals("True")) {
						value_str = "False";
					} else {
						value_str = "True";
					}
					show_value_opts = false;
				}
			} else if (type_highlight) {
				if (funk.hitTest(Box1, mouseX, mouseY)) {
					if (type_str.equals("Boolean")) {
						type_str = "Number";
						box1 = "Boolean";
						box2 = "Text";
						text_shift = 0;
						value_str = "0";
					} else if (type_str.equals("Number")) {
						type_str = "Boolean";
						box1 = "Number";
						box2 = "Text";
						text_shift = 0;
						value_str = "True";
					} else {
						type_str = "Boolean";
						box1 = "Number";
						box2 = "Text";
						text_shift = 0;
						value_str = "True";
					}
				}
				if (funk.hitTest(Box2, mouseX, mouseY)) {
					if (type_str.equals("Boolean")) {
						type_str = "Text";
						box1 = "Boolean";
						box2 = "Number";
						text_shift -= 11;
						value_str = "Something";
					} else if (type_str.equals("Number")) {
						type_str = "Text";
						box1 = "Boolean";
						box2 = "Number";
						text_shift -= 11;
						value_str = "Something";
					} else {
						type_str = "Number";
						box1 = "Boolean";
						box2 = "Text";
						text_shift = 0;
						value_str = "0";
					}
				}
			} else if (funk.hitTest(close, mouseX, mouseY)) {
				pane = new_var;
				newbie = true;
				focused = false;
				y -= 6;
			} else if (funk.hitTest(rect, mouseX, mouseY)) {
				_root.clearFocused();
				focused = true;
			} else {
				focused = false;
			}
			if (focused) {
				if (funk.hitTest(name, mouseX, mouseY)) {
					name_highlight = true;

				} else {
					name_highlight = false;
				}

				if (funk.hitTest(type, mouseX, mouseY)) {
					type_highlight = true;
					Box1 = new Rectangle(x + 106, y + 25, 100, 20);
					Box2 = new Rectangle(x + 106, y + 46, 100, 20);

				} else {
					type_highlight = false;
				}
			}
			if (!focused) {
				name_highlight = false;
				type_highlight = false;
				show_value_opts = false;
				highlight_value = false;
			}
		}

	}

	public void keyPressed(KeyEvent k) {
		if (highlight_value) {
			if (Text) {
				value_str = funk.editString(k, value_str, 90, Fonts.regFont,
						"Something", 39, 122, 9001,true);
			} else {
				value_str = funk.editString(k, value_str, 60, Fonts.regFont,
						"0", 48, 57, 8,false);
			}
		} else if (name_highlight) {
			name_str = funk.editString(k, name_str, 68, Fonts.regFont, "Name",
					39, 122, 9001,true);
		}
	}

}
