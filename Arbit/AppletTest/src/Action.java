import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;



public class Action {
	int stageWidth;
	int stageHeight;
	Rectangle close;
	Rectangle accept;
	int x;
	int y;
	Image pane = Images.action_pane;
	String title="Type to set Action";
	int ID;
	public Action(int stageWidth,int stageHeight, int ID){
		this.stageWidth = stageWidth;
		this.stageHeight= stageHeight;
		x=stageWidth/2-(pane.getWidth(null)/2);
		y=stageHeight/2-(pane.getHeight(null)/2);
		accept = new Rectangle(x+341,y+10,25,25);//creats the accept hitBox
		close = new Rectangle(x+372,y+10,25,25);//creates the close hitBox
		this.ID=ID;
		System.out.println("I have been summoned");
	}
    public void draw(Graphics stage){
    	stage.drawImage(pane,x,y,null);
    	//stage.fillRect(close.x, close.y, close.width, close.height);
    	//stage.fillRect(accept.x, accept.y, accept.width, accept.height);
    	stage.setColor(new Color(69,73,86));
    	stage.setFont(new Font("Lucida Console", Font.BOLD, 25));
    	stage.drawString(title, x+22, y+63);
    	
    }
	public void mousePressed(MouseEvent m, createScreen parent) {//what happens when the mouse is pressed
		int mouseX = m.getX();
		int mouseY = m.getY();
		if(mouseX>close.x && mouseX<close.x+close.width && mouseY>close.y && mouseY<close.y+close.height){
			die(parent);
		}
		if(mouseX>accept.x && mouseX<accept.x+accept.width && mouseY>accept.y && mouseY<close.y+close.height){
			submit(parent);
		}
		
	}
	private void die(createScreen parent) {
		// TODO Auto-generated method stub
		parent.vacancy=true;
		//System.out.println("Bye: said the action");
		parent.action=null;
	}
	public void submit(createScreen parent) {//what happens when you click on submit key
		if(!title.equals("Type to set Action") && title.length()>0){//creates new box, and shifts other box to fit the new box
		int shift=50;
		for(int i=0;i<parent.moms.size();i++){
			if(parent.moms.get(i)==ID){
				parent.boxes.get(i).x-=60;
				parent.allChildren(-60,i);
				shift+=60;
			}
		}
		int boxX = parent.boxes.get(ID).x+shift;
		int boxY = parent.boxes.get(ID).y+115;
		Box box= new Box(boxX,boxY,title,parent,parent.boxes.get(ID));
		parent.boxes.add(box);
		parent.stories.add("");
		parent.moms.add(ID);
		parent.titles.add(title);
		parent.links.add(null);
		parent.images.add(null);
		//System.out.println("Submitted");
		die(parent);
		//System.out.println("Boxes:+"+parent.boxes);
		//System.out.println("stories:+"+parent.stories);
		//System.out.println("Moms:+"+parent.moms);
		//System.out.println("titles:+"+parent.titles);
		}
	}

}