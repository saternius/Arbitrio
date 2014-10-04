import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;


public class ShowImage {
	Game _root;
	private Rectangle bg;
	private int alpha=0;
	private Image img;
	private int x=0;
	private int y=0;
	private int width;
	private int height;
	private int yDes;
	private int wait;
	private boolean fadeIn=true;
	private boolean fadeOut=false;
	private boolean death=false;
	public ShowImage(Game game,Image img) {
		_root=game;
		bg = new Rectangle(0,0,_root._root.stageWidth,_root._root.stageHeight);
		this.img = img;
		
		double imgW = img.getWidth(null);
		double imgH = img.getHeight(null);
		// System.out.println("Owidth: "+imgW+" Oheight:"+imgH);
		if (imgW > 500 || imgH > 500) {
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
		width = (int) imgW;
		height = (int) imgH;
		x=(_root._root.stageWidth/2)-(width/2)-5;
		yDes = (_root._root.stageHeight/2)-(height/2);
		y=-height;
		alpha=0;
	}

	public void draw(Graphics stage) {
		if(fadeIn && alpha<160){
			alpha+=5;
		}
		if(fadeOut && alpha>=5){
			alpha-=5;
		}
		y+=(yDes-y)/10;
		stage.setColor(new Color(0,0,0,alpha));
		stage.fillRect(0,0,bg.width,bg.height);
		stage.drawImage(img,x,y,width,height,null);
		wait++;
		if(death && alpha<5){
			_root.vacancy=true;
			_root.showImage=null;
		}
	}

	public void mousePressed(MouseEvent m) {
		if(wait>16){
			yDes=-height-40;
			fadeIn=false;
			fadeOut=true;
			death=true;
		}
	}

}
