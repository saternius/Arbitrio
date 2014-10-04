import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;


public class saveButton {
	int x,y;
	String str;
	Rectangle loadMe;
	Rectangle deleteMe;
	private Color color;
	public saveButton(int x, int y, String str){
		this.x=x;
		this.y=y;
		this.str=str;
		this.loadMe = new Rectangle(x,y-3,100,10);
		this.deleteMe = new Rectangle(x-13,y,10,10);
		color = new Color(209,215,234);
	}
    public void draw(Graphics stage){
    	stage.setColor(color);
    	stage.fillRect(loadMe.x,loadMe.y,loadMe.width,loadMe.height);
    	 stage.setColor(new Color(184,111,110));
    	 stage.fillRect(x-14, y-1, 10, 10);
    	 stage.setColor(new Color(142,64,66));
    	 stage.drawRect(x-14, y-1, 10, 10);
    	 stage.drawLine(x-14, y, x+-5, y+8);
     	 stage.drawLine(x-14, y+8, x-5, y);
     	
    	 
    	 stage.setColor(new Color(77,82,96));
    	 stage.setFont(Images.biko_bold);
    	 stage.drawString(str, x, y+9); 
    	 
    }
	public void mousePressed(MouseEvent m, SaveLoad parent) {//what happens when the mouse is pressed
		int mouseX = m.getX();
		int mouseY = m.getY();
		if(mouseX>loadMe.x && mouseX<loadMe.x+loadMe.width && mouseY>loadMe.y && mouseY<loadMe.y+loadMe.height){
			parent.docName=str;
		}
		if(mouseX>deleteMe.x && mouseX<deleteMe.x+deleteMe.width && mouseY>deleteMe.y && mouseY<deleteMe.y+deleteMe.height){
			int i = parent.files.indexOf(str);
			parent.files.remove(i);
			parent.saveButtons.remove(i);
		}
	}
	public void mouseMoved(MouseEvent m) {
		int mouseX = m.getX();
		int mouseY = m.getY();
		if(mouseX>loadMe.x && mouseX<loadMe.x+loadMe.width && mouseY>loadMe.y && mouseY<loadMe.y+loadMe.height){
			color = new Color(186,196,224);
		}else{
			color = new Color(209,215,234);
		}
	}

}