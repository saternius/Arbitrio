import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;


public class Box {
	public int x;
	public int y;
	public int xShift=0;
	public int yShift=0;
	public int width=100;
	public int height=60;
	public String title ="Default";	
	public createScreen parent;
	public int ID = -1;
	public Rectangle hitBox;
	public Box mother;
	public boolean highlight=false;
	public int alpha=255;
	Image linkIcon = Images.link;
	Image dropShadow = Images.boxDropShadow;
	Image eye = Images.eye;
	Image eye_2 = Images.eye_2;
	Rectangle actionRect;
	Rectangle eyeRect;
	private boolean deletable=false;
	public boolean invisibleChildren=false;
	public Box(int x, int y, String s, createScreen createScreen, Box mother){
		this.x=x-(width/2);
		this.y=y-(height/2);
		title=s;
		parent = createScreen;
		this.mother = mother;
		if(mother!=null && mother.invisibleChildren){
			invisibleChildren=true;
			alpha=0;
		}
	}
    public void draw(Graphics stage){
    	if(alpha!=0){
    	actionRect = new Rectangle(x+xShift+80,y+yShift+40,16,16);
    	eyeRect = new Rectangle(x+xShift,y+yShift,22,15);
    	//dropShadow
    	stage.drawImage(dropShadow, x-2+xShift,y-2+yShift,null);
    	
    	ID=parent.boxes.indexOf(this);
    	hitBox = new Rectangle(this.x+xShift,this.y+yShift,width-20,height);
    	if(stage instanceof Graphics2D){
			Graphics2D g2 = (Graphics2D)stage;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
    	}
    	if(highlight){
    		stage.setColor(new Color(98,147,147,alpha));
    	}else{
    	stage.setColor(new Color(74,111,111,alpha));
    	}
    	stage.fillRoundRect(x+xShift, y+yShift, width, height,5,5);
    	stage.setColor(new Color(39,50,50,alpha));
    	stage.drawRoundRect(x+xShift, y+yShift, width, height, 5, 5);
    	//Draw Title/texts
    	Font font = new Font("Lucida Console", Font.BOLD, 11);
    	stage.setFont(font);
    	@SuppressWarnings("deprecation")
		FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
    	int w = fm.stringWidth(title);
    	
    	stage.drawString(title, x+xShift+(width/2)-(w/2), y+yShift+(height/2)+1);
    	//addText
    	font = new Font("Lucida Console", Font.BOLD, 9);
    	stage.setFont(font);
    	stage.drawString("Add", x+xShift+width-40, y+yShift+(height/2)+22);
    	
    	//Draw Exit
    	
    	if(ID!=0 && hasNoChildren()){
    	deletable=true;
    	stage.setColor(new Color(166,89,89,alpha));
    	stage.fillRect(x+xShift+87,y+yShift+2,12,12);
    	stage.setColor(new Color(106,57,57,alpha));
    	stage.drawRect(x+xShift+86,y+yShift+2,12,12);
    	stage.drawLine(x+xShift+86+3, y+yShift+2+3, x+xShift+86+10, y+yShift+2+10);
    	stage.drawLine(x+xShift+86+3, y+yShift+2+10, x+xShift+86+10, y+yShift+2+3);
    	}else{
    		deletable=false;
    	}
    	//System.out.println(deletable);
    	//Draw add Box Icon
    	stage.setColor(new Color(80,90,89,alpha));
    	stage.fillRect(x+xShift+80, y+yShift+40, 16, 16);
    	stage.setColor(new Color(39,50,50,alpha));
    	stage.drawRect(x+xShift+80, y+yShift+40, 16, 16);
    	
    	stage.drawLine(x+xShift+88, y+yShift+44, x+xShift+88, y+yShift+52);
    	stage.drawLine(x+xShift+84, y+yShift+48, x+xShift+92, y+yShift+48);
    	stage.drawImage(linkIcon, x+xShift+10,y+yShift+41,null);
    	stage.setColor(new Color(39,50,50,255));
    	stage.drawString(Integer.toString(ID),x+xShift+26,y+yShift+52);
    	
    //	stage.fillRect(eyeRect.x,eyeRect.y,eyeRect.width,eyeRect.height);
    	if(alpha==255){
    	stage.drawImage(eye,x+2+xShift,y+2+yShift,null);
    	}
    	if(invisibleChildren){
    		stage.drawImage(eye_2,x+2+xShift,y+2+yShift,null);
    	}
    	}
    	
    	//stage.fillRect(hitBox.x,hitBox.y,hitBox.width,hitBox.height);
    	//stage.drawRect(actionRect.x, actionRect.y, actionRect.width, actionRect.height);
    	
    }
	private boolean hasNoChildren() {
		for(int i=0;i<parent.moms.size();i++){
			if(parent.moms.get(i)==ID){
				return false;
			}
		}
		return true;
	}
	public void mousePressed(MouseEvent m) {
		ID=parent.boxes.indexOf(this);
	//	System.out.println("passing:"+ID);
		//System.out.println("ID:"+ID);
		int mouseX = m.getX();
		int mouseY = m.getY();
		
		if(parent.vacancy && mouseX>eyeRect.x && mouseX<eyeRect.x+eyeRect.width && mouseY>eyeRect.y && mouseY<eyeRect.y + eyeRect.height){
			invisibleChildren = !invisibleChildren;
			if(invisibleChildren){
			parent.hideAllChildren(ID,true);
			}else{
			parent.showAllChildren(ID,true);
			}
		}else
		if(deletable && ID!=0 && mouseX>x+xShift+87 && mouseX<x+xShift+87+12 && mouseY>y+yShift+2 && mouseY<y+yShift+14){
			System.out.println("'Goodbye': said the Box");
			parent.deleteBox(this);
		}else
	
		if(mouseX>x+xShift+80 && mouseX<x+xShift+96 && mouseY>y+yShift+40 && mouseY<y+yShift+56 && parent.vacancy){
			System.out.println("'Hello': said the action pane");
			parent.vacancy=false;
			parent.action = new Action(parent.stageWidth,parent.stageHeight,ID);
		}else
		if(mouseX>hitBox.x+xShift && mouseX<hitBox.x+xShift+hitBox.width && mouseY>hitBox.y+yShift && mouseY<hitBox.y+yShift+hitBox.height && parent.vacancy){
			System.out.println("Hello said the senario pane");
			System.out.println(parent);
			System.out.println(ID);
			parent.vacancy=false;
			parent.scene = new Scene(parent.stageWidth,parent.stageHeight,title,ID,parent,this);
		}
		
	}
	public void trace(String s){
		System.out.println(s);
	}
    

}
