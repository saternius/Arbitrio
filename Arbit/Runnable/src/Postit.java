import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class Postit {
	Image pane = Images.PostOnline;
	Image highlight =Images.long_highlight;
	Image nsfwIcon = Images.selected;
	createScreen _root;
	Rectangle close;
	Rectangle storyTitleRect;
	Rectangle authorRect;
	Rectangle submit;
	Rectangle nsfwRect;
	boolean nsfw;
	int x;
	int y;
	private boolean highlightTitle=false;
	private boolean TitleSelected=false;
	private boolean highlightAuthor=false;
	private boolean AuthorSelected=false;
	String author="";
	String title="";
	public Postit(createScreen parent){
		this._root = parent;
		x=_root.stageWidth/2-(pane.getWidth(null)/2);
		y=_root.stageHeight/2-(pane.getHeight(null)/2);
		close = new Rectangle(x+345,y+11,25,25);
		storyTitleRect = new Rectangle(x+105,y+42,250,20);
		authorRect = new Rectangle(x+105,y+70,250,20);
		submit = new Rectangle(x+222,y+98,135,25);
		nsfwRect = new Rectangle(x+100,y+100,17,17);
	}
	
    public void draw(Graphics stage){  
    	stage.drawImage(pane,x,y,null);
    	//stage.fillRect(close.x, close.y, close.width, close.height);
    	//stage.fillRect(storyTitleRect.x,storyTitleRect.y,storyTitleRect.width,storyTitleRect.height);
    	//stage.fillRect(authorRect.x,authorRect.y,authorRect.width,authorRect.height);
    	//stage.fillRect(submit.x,submit.y,submit.width,submit.height);
    	//stage.fillRect(nsfwRect.x,nsfwRect.y,nsfwRect.width,nsfwRect.height);
    	
    	if(highlightTitle||TitleSelected){
    		stage.drawImage(highlight,x+100,y+37,null);
    	}
    	if(highlightAuthor || AuthorSelected){
    		stage.drawImage(highlight,x+100,y+65,null);
    	}
    	stage.setColor(new Color(102,109,128));
    	stage.setFont(new Font("Biko", Font.PLAIN, 17));
    	stage.drawString(title, x+108, y+58);
    	stage.drawString(author, x+108, y+86);
    	stage.setFont(new Font("Biko", Font.PLAIN, 17));
    	if(nsfw){
    		stage.drawImage(nsfwIcon,x+97,y+97,null);
    	}
    }
    
	public void mousePressed(MouseEvent m) {//what happens when the mouse is pressed
		int mouseX = m.getX();
		int mouseY = m.getY();
		if(mouseX>close.x && mouseX<close.x+close.width && mouseY>close.y && mouseY<close.y+close.height){
			_root.vacancy=true;
			_root.postit=null;
		}
		if(mouseX>nsfwRect.x && mouseX<nsfwRect.x+nsfwRect.width && mouseY>nsfwRect.y && mouseY<nsfwRect.y+nsfwRect.height){
			nsfw=!nsfw;
		}
		TitleSelected=false;
		AuthorSelected=false;
		if(highlightTitle){
			TitleSelected=true;
		}
		if(highlightAuthor){
			AuthorSelected=true;
		}
		if(mouseX>submit.x && mouseX<submit.x+submit.width && mouseY>submit.y && mouseY<submit.y+submit.height){
			if(author.length()>0 && title.length()>0){
			MySqlRunner runner = new MySqlRunner();
			runner.store(title,author,_root,nsfw);
			_root.vacancy=true;
			_root.postit=null;
			}else{
				//alert to put Author/title
			}
		}

	}
	
	public void mouseMoved(MouseEvent m) {
		int mouseX = m.getX();
		int mouseY = m.getY();
		highlightTitle=(mouseX>storyTitleRect.x && mouseX<storyTitleRect.x+storyTitleRect.width && mouseY>storyTitleRect.y && mouseY<storyTitleRect.y+storyTitleRect.height);
		highlightAuthor=(mouseX>authorRect.x && mouseX<authorRect.x+authorRect.width && mouseY>authorRect.y && mouseY<authorRect.y+authorRect.height);
	
	}

	public void keyPressed(KeyEvent k) {
		if(TitleSelected){
		if (k.getKeyCode() == 8 && title.length() > 0) {
			title = title
					.substring(0, title.length() - 1);
		} else {
			if (((k.getKeyChar() >= 39 && k.getKeyChar() <= 122) || k
					.getKeyChar() == ' ') && title.length() < 20) {
				title += k.getKeyChar();
			}
		}
		}else
		if(AuthorSelected){
			if (k.getKeyCode() == 8 && author.length() > 0) {
				author = author
						.substring(0, author.length() - 1);
			} else {
				if (((k.getKeyChar() >= 39 && k.getKeyChar() <= 122) || k
						.getKeyChar() == ' ') && author.length() < 20) {
					author += k.getKeyChar();
				}
			}
		}
	}
}