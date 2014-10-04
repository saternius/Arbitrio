import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;



public class AbilityBox {
	int x;
	int y;
	Scene _root;
	Image pane = Images.ability;
	Rectangle close;
	Rectangle confirm;
	private String abilities="";
	Fonts font = new Fonts();
	public AbilityBox(Scene scene) {
		_root=scene;
		x=(_root.stageWidth/2)-(pane.getWidth(null)/2);
		y=(_root.stageHeight/2)-(pane.getHeight(null)/2);
		confirm = new Rectangle(x+340,y+8,25,25);
		close = new Rectangle(x+370,y+8,25,25);
	}
	public void draw(Graphics stage){
		stage.drawImage(pane,x,y,null);
		drawRect(close,stage);
		drawRect(confirm,stage);
		Fonts.setSize(17);
		stage.setFont(Fonts.regFont);
		stage.drawString(abilities,x+20,y+54);
	}
	private void drawRect(Rectangle rect, Graphics stage) {
		stage.drawRect(rect.x,rect.y,rect.width,rect.height);
	}
	public void mousePressed(MouseEvent m) {
		int mouseX = m.getX();
		int mouseY = m.getY();
		if(hitTest(close,mouseX,mouseY)){
			die();
		}
	}
	private void die() {
		_root.vacancy=true;
		_root.abilityBox=null;
	}
	private boolean hitTest(Rectangle rect, int mouseX, int mouseY) {
		return mouseX>rect.x && mouseX<rect.x+rect.width && mouseY>rect.y && mouseY<rect.y+rect.height;
	}
	public void keyPressed(KeyEvent k) {
		if (abilities.length() < 20) {
			if(k.getKeyChar()==',' || k.getKeyCode() == 32){
				abilities+=k.getKeyChar();
			}
			if (k.getKeyCode() == 8 && abilities.length() > 0) {
				abilities = abilities.substring(0,
						abilities.length() - 1);
			} else if (k.getKeyChar() >= 48 && k.getKeyChar() <= 122) {
				abilities += k.getKeyChar();
			}
		}
	}

}
