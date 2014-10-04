import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;



public class Buttons {
	public Rectangle rect;
	public String text;
	public int x;
	public int y;
	public int width;
	public int height;
	public int textSize = 4;
	public Color pane_color = new Color(39,39,39);
	public boolean fall =false;
	public int yVel = 0;
	public boolean createFlag =true;
	public Color color = new Color(39,39,39);
	public Font font;
	public Color highlight;
	public Rectangle pane;
	public int ID;
	private Font font1;
	private Font font2;
	
	public Buttons(int x, int y, int width, int height, String text,Color color,Color highlight, Font font,int ID){
		rect = new Rectangle(x,y,width,height);
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.text=text;
		this.color=color;
		this.highlight=highlight;
		this.font=font;
		pane=new Rectangle(x,y,width,height);
		if(color!=null){
			pane_color=color;
		}else{
		pane_color = new Color(39,39,39);
		}
		this.ID=ID;
		if(font==null){
			System.out.println(new File(".").getAbsolutePath());
			this.font2 = Images.code_light;
			this.font2 = this.font2.deriveFont(Font.PLAIN, 22f);
			
			this.font1 = Images.code_bold;
			this.font1 = this.font1.deriveFont(Font.BOLD, 22f);
		}
    }
    public void draw(Graphics g, int xPos, int yPos){
    	if(font!=null){
    	g.setFont(font);
    	}
    	g.setColor(pane_color);
    	pane=new Rectangle(x+xPos,y+yPos,width,height);
    	g.fillRect(pane.x,pane.y,pane.width,pane.height);
    	g.setColor(new Color(204,204,204));
    	//g.setFont(new Font("Code Light", Font.PLAIN, 20));
    	if(font==null){
    		Font f;
    	if(text=="Play Stories"){
    		g.setFont(font2);
    		f=font2;
    	}else{
    		g.setFont(font1);
    		f=font1;
    	}
    	@SuppressWarnings("deprecation")
		FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(f);
    	int w = fm.stringWidth(text);
    	//System.out.println(text);
    	g.drawString(text, x+width/2-(w/2)+xPos, y+height/2+(textSize)+yPos+4);
    	}else{
    		@SuppressWarnings("deprecation")
			FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
        	int w = fm.stringWidth(text);
        	int h = fm.getHeight();
        	g.drawString(text, x+xPos-(w/2)+(width/2), y+yPos+8+(h/2));
        	
    	}
    }
	public void highlight(int mouseX, int mouseY){
    	if(mouseX>pane.x && mouseX<pane.x+pane.width && mouseY>pane.y && mouseY<pane.y+pane.height){
    		if(highlight!=null){
    			pane_color=highlight;
    		}else{
    		pane_color= new Color(78,78,78);
    		}
    	}else{
    		if(color!=null){
    			pane_color=color;
    		}else{
    		pane_color = new Color(39,39,39);
    		}
    	}
    }
	public boolean isClicked(int mouseX, int mouseY){
		return (mouseX>pane.x && mouseX<pane.x+pane.width && mouseY>pane.y && mouseY<pane.y+pane.height);
	}
	public void checkClicked(int mouseX,int mouseY, MenuScreen menu) {
		if(mouseX>x && mouseX<x+width && mouseY>y && mouseY<y+height && createFlag){
			createFlag=false;
			menu.fall=true;
			menu.gc.create = new createScreen(menu.gc.stageWidth, menu.gc.stageHeight,menu.gc,true);
		}
	}

}
