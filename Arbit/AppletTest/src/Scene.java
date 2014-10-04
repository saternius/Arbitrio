import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;


public class Scene {
	int stageWidth;
	int stageHeight;
	Rectangle close;
	Rectangle uploadImg;
	int x;
	int y;
	int ID;
	Image pane = Images.senario_pane;
	Image noPic = Images.noPic;
	Image sceneLink =Images.SceneLink;
	String story = "Type to create the senario.";
	String title = "Default";
	createScreen parent;
	Box box;
	public String photoLink;
	BufferedImage img;
	String imageStatus="No image selected";
	private int textShift=4;
	private Rectangle linkRect;
	public BoxLink boxLink;
	public Rectangle actionRect;
	public boolean vacancy=true;
	boolean wait =false;
	Texty textfield;
	public AbilityBox abilityBox;
	

	public Scene(int stageWidth, int stageHeight, String title, int ID,
			createScreen parent, Box box) {
		this.stageWidth = stageWidth;
		this.stageHeight = stageHeight;
		x = stageWidth / 2 - (pane.getWidth(null) / 2);
		y = stageHeight / 2 - (pane.getHeight(null) / 2);
		uploadImg = new Rectangle(x + 220, y + 10, 22, 22);
		close = new Rectangle(x + 372, y + 10, 25, 25);
		this.title = title;
		this.ID = ID;
		this.parent = parent;
		story = parent.stories.get(ID);
		this.box = box;
		int i = parent.boxes.indexOf(box);
		img=parent.images.get(i);
		if(img!=null){
			 imageStatus="Image uploaded";
			 textShift=8;
		}
		photoLink=parent.links.get(i);
		System.out.println(img);
		linkRect=new Rectangle(x+195,y+10,22,22);
		//actionRect = new Rectangle(x+195,y+10,22,22);
		textfield = new Texty(this,x,y,story);
	}

	
	public void draw(Graphics stage) {
		stage.drawImage(pane, x, y, null);
		// stage.fillRect(close.x, close.y, close.width, close.height);
		// stage.fillRect(uploadImg.x, uploadImg.y, uploadImg.width,
		// uploadImg.height);
		stage.setColor(new Color(69, 73, 86));
		stage.setFont(new Font("Biko", Font.BOLD, 20));
		stage.drawString(title, x + 15, y + 26);
		stage.setColor(new Color(85, 85, 85));
		stage.setFont(new Font("Lucida Console", Font.BOLD, 9));
		if (photoLink != null && photoLink.length() > 0) {
			if (photoLink.length() > 15) {
				photoLink = "..."
						+ photoLink.substring(photoLink.length() - 14,
								photoLink.length());
			}
			stage.drawString(photoLink, x + 250, y + 25);
		}
		if(img!=null){
			double imgW=img.getWidth();
			double imgH=img.getHeight();
			//System.out.println("Owidth: "+imgW+" Oheight:"+imgH);
			if (imgW>100||imgH>100) {
				double newratio=(imgW)/(imgH);
				double newratio2=(imgH)/(imgW);
				//System.out.println("ratio1: "+newratio+" ratio2:"+newratio2);

				if (newratio>newratio2) {
					imgW=100;
					imgH=100*newratio2;
				} else {
					imgH=100;
					imgW=newratio*100;
				}
			}
			//System.out.println("Nwidth: "+imgW+" Nheight:"+imgH);
			stage.drawImage(img, x+295+(50-(int)imgW/2), y+35+(50-(int)imgH/2), (int)imgW, (int)imgH, null);
			stage.drawImage(noPic, x+223, y + 10, null);
		}
		
		stage.drawString(imageStatus, x + 292+textShift, y + 150);
		
		//stage.fillRect(linkRect.x, linkRect.y, linkRect.width, linkRect.height);
		stage.drawImage(sceneLink,x+195,y+10,null);
		textfield.draw(stage);
		if(boxLink!=null){
			boxLink.draw(stage);
		}
		if(abilityBox!=null){
			abilityBox.draw(stage);
		}
		wait=true;
		//drawRect(uploadImg,stage);
		//drawRect(actionRect,stage);
		//drawRect(linkRect,stage);
	}


	public void mousePressed(MouseEvent m, createScreen parent) {
		int mouseX = m.getX();
		int mouseY = m.getY();
		if (hitTest(close,mouseX,mouseY)) {
			die(parent);
		}
		//trace(Integer.toString(wait));
		if(vacancy && hitTest(linkRect,mouseX,mouseY)){
			vacancy=false;
			boxLink = new BoxLink(this);
			textfield.active=false;
		}
		if (hitTest(uploadImg,mouseX,mouseY)) {
			int i = parent.boxes.indexOf(box);
			if (photoLink!=null && photoLink.length() > 0) {
				photoLink = "";
				imageStatus="No image selected";
				textShift=4;
				img=null;
			} else {
				TextTransfer textTransfer = new TextTransfer();
				System.out.println("Generating image, please wait..");
				photoLink = textTransfer.getClipboardContents();
				try{
				  img= ImageIO.read(new URL(photoLink));
				  imageStatus="Image uploaded";
				  textShift=8;
				  parent.images.set(i, img);
				}catch(Exception e){
					System.out.println("Yo image failed brah");
					imageStatus="Image upload failed";
					textShift=1;
				}
			}
			parent.links.set(i, photoLink);
		}
		/*
		if(vacancy && hitTest(actionRect,mouseX,mouseY)){
			vacancy=false;
			abilityBox = new AbilityBox(this);
			textfield.active=false;
		}
		*/
		
		if(boxLink!=null && abilityBox==null){
			boxLink.mousePressed(m);
		}else if(abilityBox!=null && boxLink==null){
			abilityBox.mousePressed(m);
		}else{
			textfield.mousePressed(m);
		}
		
		

	}


	private boolean hitTest(Rectangle rect, int mouseX, int mouseY) {
		return wait && mouseX>rect.x && mouseX<rect.x+rect.width && mouseY>rect.y && mouseY<rect.y+rect.height;
	}


	public void die(createScreen parent) {
		// TODO Auto-generated method stub
		parent.vacancy = true;
		// System.out.println("Bye: said the action");
		parent.scene = null;
		box.highlight = (!textfield.story.equals("Type to create the senario.") && textfield.story.length() > 0);
		parent.stories.set(ID, textfield.story);
	}

	public void submit(createScreen parent) {
		// System.out.println("Submitted");
		die(parent);
	}
	


}