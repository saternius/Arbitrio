import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;



public class BoxLink {
	Scene _root;
	Image pane = Images.boxLink;
	Fonts font = new Fonts();
	String text ="0";
	int x;
	int y;
	Rectangle close;
	Rectangle confirm;
	public BoxLink(Scene scene) {
		_root = scene;
		x=(_root.stageWidth/2)-95;
		y=(_root.stageHeight/2)-45;
		confirm = new Rectangle(x+111,y+9,25,25);
		close = new Rectangle(x+142,y+9,25,25);
	}
	public void draw(Graphics stage){
		stage.drawImage(pane,x,y,null);
		Fonts.setSize(25);
		stage.setFont(Fonts.regFont);
		stage.drawString(text, x+20, y+63);
		//stage.fillRect(close.x,close.y,close.width,close.height);
		//stage.fillRect(confirm.x,confirm.y,confirm.width,confirm.height);
	}
	public void keyPressed(KeyEvent k) {
		if (k.getKeyCode() == 8 && text.length() > 0) {
			text = text.substring(0, text.length() - 1);
		} else {
			if(k.getKeyCode()>47 && k.getKeyCode()<58 && text.length()<3){
				text+= k.getKeyChar();
				checkValueOverMax();
			}
		}
	}
	private void checkValueOverMax() {
		int value = Integer.parseInt(text);
		int maxValue = _root.parent.boxes.size()-1;
		if(value>maxValue){
			text=Integer.toString(maxValue);
		}
	}
	public void mousePressed(MouseEvent m) {
		int mouseX = m.getX();
		int mouseY = m.getY();
		if(clicksRect(close,mouseX,mouseY)){
			_root.vacancy=true;
			_root.boxLink=null;
			_root.textfield.active=true;
		}
		if(clicksRect(confirm,mouseX,mouseY)){
			_root.story="<link>"+text;
			_root.vacancy=true;
			_root.boxLink=null;
			_root.die(_root.parent);
		}
		
	}
	private boolean clicksRect(Rectangle rect, int mouseX, int mouseY) {
		
		return (mouseX>rect.x && mouseX<rect.x+rect.width && mouseY>rect.y && mouseY<rect.y+rect.height);
	}

}
