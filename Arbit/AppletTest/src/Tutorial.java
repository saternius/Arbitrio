import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/*establish a clickable only location on the creatscreen
 * to force the user to follow the linear layout of the tutorial
 * if they wish to exit this deterministic playthough they have to
 * press esc in which a new createScreen would be generated without
 * a tutorial.
 */
public class Tutorial {
	Image first = Images.tutorial_prompt1;
	Image pane_1 = Images.tutorial_pane_1;
	Image pane_2 = Images.tutorial_pane_2;
	Image pane_3 = Images.tutorial_pane_3;
	Image pane_4 = Images.tutorial_pane_4;
	Image pane_5 = Images.tutorial_pane_5;
	Image pane_6 = Images.tutorial_pane_6;
	Image pane_7 = Images.tutorial_pane_7;
	Image[] panes = {first,pane_1,pane_2,pane_1,pane_3,pane_2,pane_3,pane_4,pane_5,pane_5,pane_5,pane_5,pane_5,pane_6,pane_7,pane_4};
	int[] xPos={80,80,53,85,145,134,114,80,-5,28,58,91,125,160,169,80};
	int[] yPos={200,125,360,69,70,331,114,75,23,23,23,23,23,23,23,75};
	String[] title ={"","Step 1: Creating a starting senario","Step 2: Writing the starting senario","Step 3: Adding images","Step 4: Closing the window","Step 5: Adding an action","Step 6: Confirming the action","You did it!, Here's more info","Extra information","Extra information","Extra information","Extra information","Extra information","Extra information","Extra information","Final words"};
	String[] line1={"","The first thing you would want to do is to make the","You can instantly start writing the senario by typing","You can add images by copying an images url to your","Close the window by clicking on the red X","You can initiate a possible action to your senario by","Type to write the title of the action.","That's pretty much all there is too it. You can add more","You can move the boxes by right clicking them.","You can load projects you previously worked on by","You can save your current project by clicking","You can preview your work by clicking on this","You can restart by pressing this button","You can post your work online for the world to","You can return to the menu by clicking on","So those are just the basics. For a full in-depth tutorial"};
	String[] line2={"","starting senario. To do this, first click on the","away. Note that there is a 300 character limit.","clipboard(as in just copy it) and then pressing the","All your modifications would be saved","clicking on the plus button","Click the green checkmark to generate the action.","senarios and actions to create a highly complex story.","You can recenter them by pressing this button","clicking on this button","on this button","button","*This mechanic has been disabled for the tutorial*","see by clicking this button","this button","on how to do more complex mechanics please refer"};
	String[] line3={"","'set senario' box below.","[Click this box to continue the tutorial]"," this button. Smaller images typically ~250x250 are ","","","This will create a new box for the reaction towards","You are only limited by your imagination.","*This mechanic has been disabled for the tutorial*","*This mechanic has been disabled for the tutorial*","*This mechanic has been disabled for the tutorial*","*This mechanic has been disabled for the tutorial*","","*This mechanic has been disabled for the tutorial*","*This mechanic has been disabled for the tutorial*","to the official web-manual"};
	String[] line4={"","","","highly recommended for faster load time","","","this action","*For additional instructions refer to the web-manual","","","","","","","","Now create that epic story of yours"};
	String[] line5={"","(You can end this tutorial by pressing Shift+ESC key)","(You can end this tutorial by pressing Shift+ESC key)","[Click this box to continue the tutorial]","(You can end this tutorial by pressing Shift+ESC key)","(You can end this tutorial by pressing Shift+ESC key)","(You can end this tutorial by pressing Shift+ESC key)","[Click this box to continue the tutorial]","[Click this box to continue the tutorial]","[Click this box to continue the tutorial]","[Click this box to continue the tutorial]","[Click this box to continue the tutorial]","[Click this box to continue the tutorial]","[Click this box to continue the tutorial]","[Click this box to continue the tutorial]","[Click this box to end the tutorial]"};
	int[] clickX={0,214,57,31,423,291,(423-31),86,23,56,87,109,153,190,175,86};
	int[] clickY={0,285,414,62,235,322,274,80,87,87,87,87,89,89,89,80};
	int[] clickWidth={0,72,335,350,25,15,25,333,327,327,327,327,327,297,305,333};
	int[] clickHeight={0,54,103,200,25,15,25,105,100,100,100,100,100,100,100,105};
	int[] extraX={0,0,0,0,0,0,0,0,30,30,30,30,30,30,2,0};
	int[] extraY={0,0,53,0,0,53,0,0,60,60,60,60,60,60,60,0};
	double alpha=0;
	int focus=0;
	Rectangle yes;
	Rectangle no;
	boolean getVisible=true;
	int frame = 1;
	createScreen _root;
	private boolean dying=false;
	boolean playing=false;
	@SuppressWarnings("unused")
	private Fonts fonts = new Fonts();
	Rectangle clickRect;
	private boolean isVisible=true;
	public Tutorial(createScreen createScreen){
		yes = new Rectangle(107,237,145,25);
		no = new Rectangle(263,236,145,25);
		_root=createScreen;
		clickRect=_root.leader.clickableRect;
	}
	public void draw(Graphics stage) {
		if(isVisible){
		//trace(""+focus);
		enterframe();
		// TODO Auto-generated method stub
		if(getVisible && alpha<.92){
			alpha+=.05;
		}
		if(!getVisible && alpha>.08){
			alpha-=.05;
		}
		((Graphics2D) stage).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float) alpha));
		stage.drawImage(panes[focus],xPos[focus],yPos[focus],null);
		Fonts.setSize(20);
		stage.setFont(Fonts.regFont);
		stage.drawString(title[focus],xPos[focus]+8+extraX[focus],yPos[focus]+24+extraY[focus]);
		Fonts.setSize(14);
		stage.setFont(Fonts.regFont);
		stage.drawString(line1[focus],xPos[focus]+9+extraX[focus],yPos[focus]+42+extraY[focus]);
		stage.drawString(line2[focus],xPos[focus]+9+extraX[focus],yPos[focus]+58+extraY[focus]);
		stage.drawString(line3[focus],xPos[focus]+9+extraX[focus],yPos[focus]+74+extraY[focus]);
		stage.drawString(line4[focus],xPos[focus]+9+extraX[focus],yPos[focus]+90+extraY[focus]);
		
		stage.drawString(line5[focus],xPos[focus]+13+extraX[focus],yPos[focus]+104+extraY[focus]);
		((Graphics2D) stage).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		//drawRect(stage,yes);
		//drawRect(stage,no);
		if(frame>1){
			//drawRect(stage,_root.leader.clickableRect);
		}
		}
	}
	private void enterframe() {
		if(alpha<.08 && playing){
			if(dying){
			_root.tut=null;
			}else{
				focus++;
				getVisible=true;
				_root.leader.clickableRect=new Rectangle(clickX[focus],clickY[focus],clickWidth[focus],clickHeight[focus]);
			}
		}
	}
	public void drawRect(Graphics stage, Rectangle rect){
		stage.fillRect(rect.x,rect.y,rect.width,rect.height);
	}
	public void mousePressed(MouseEvent m) {
		trace(""+frame);
		int mouseX = m.getX();
		int mouseY = m.getY();
		if(alpha>.92){
			if(frame>1){
				changeit(mouseX,mouseY);
			}
			if(frame==1){
				if(checkClicked(yes,mouseX,mouseY)){
					playing=true;
					frame++;
					getVisible=false;
					_root.centerBoxes();
					
					
				}else if(checkClicked(no,mouseX,mouseY)){
					trace("no");
					die();
				}
			}
		}
	}
	private void changeit(int mouseX, int mouseY) {
		if(checkClicked(clickRect,mouseX,mouseY)){
			trace("changing");
			frame++;
			getVisible=false;
		}
		if(frame==17){
			_root.leader.clickableRect.x=0;
			_root.leader.clickableRect.y=0;
			_root.leader.clickableRect.width=520;
			_root.leader.clickableRect.height=620;
			_root.leader.create= new createScreen(_root.stageWidth,_root.stageHeight,_root.leader,false);
		}
	}
	private void trace(String string) {
		System.out.println(string);
	}
	public void die() {
		//delete's system32
		getVisible=false;
		dying=true;
		_root.leader.clickableRect.x=0;
		_root.leader.clickableRect.y=0;
		_root.leader.clickableRect.width=520;
		_root.leader.clickableRect.height=620;
	}
	private boolean checkClicked(Rectangle rect, int mouseX, int mouseY) {
		return mouseX>rect.x && mouseX<rect.x+rect.width && mouseY>rect.y && mouseY<rect.y+rect.width;
	}
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode()==27 && k.isShiftDown()){
			die();
		}
		if(frame==7 && k.getKeyCode()==10){
			frame++;
			getVisible=false;
		}
	}
}