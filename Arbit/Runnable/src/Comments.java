import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;

 


public class Comments {
	private Image pane = Images.comments;
	private int x;
	private int y;
	private int yDes;
	private String id;
	private String[] comments = new String[0];
	private int page=0;
	@SuppressWarnings("unused")
	private Fonts font = new Fonts();
	private Texty[] texts ={new Texty(), new Texty(),new Texty(),new Texty(),new Texty(), new Texty()};
	private Rectangle close;
	private PlayGame _root;
	
	private Image arrowLeft = Images.small_left_arrow;
	private Image arrowRight = Images.small_right_arrow;
	private Image arrowLeftF = Images.small_left_arrow_faded;
	private Image arrowRightF = Images.small_right_arrow_faded;
	
	private Image rightArrow = arrowRightF;
	private Image leftArrow = arrowLeftF;
	private Rectangle leftRect = new Rectangle(400,555,21,19);
	private Rectangle rightRect = new Rectangle(433,555,21,19);
	
	public Comments(PlayGame _root, String id) {
		x=_root._root.stageWidth/2-(pane.getWidth(null)/2);
		y=_root._root.stageHeight;
		yDes=_root._root.stageHeight/2-(pane.getHeight(null)/2)-30;
		this.id = id;
		loadComments();
		for(int i=0;i<texts.length;i++){
			Fonts.setSize(12);
			texts[i].font=Fonts.regFont;
			texts[i].desired_length=75;
			texts[i].max_length=500;
		}
		this._root = _root;
	}

	private void loadComments() {
		try{
			String url_string = "http://myrighttoplay.com/Arbitrio/Java/get_comments.php";
			URL url = new URL(url_string);
		    URLConnection con = url.openConnection();
		    con.setDoOutput(true);
		    PrintStream ps = new PrintStream(con.getOutputStream());
		    ps.print("id="+id+"&page="+page);
		    con.getInputStream();
		    ps.close();
		    InputStream r = con.getInputStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(r));
		    for(String line; (line = reader.readLine()) != null;){
		    	//trace(line);
		    	comments = line.split("/////");
		    }
		}catch(Exception e){ 
			System.out.println("Failed to fetch comments");
		}
		trace("loaded Comments");
	}

	public void draw(Graphics stage) {
		if(page>=5){
			leftArrow = arrowLeft;
		}else{
			leftArrow = arrowLeftF;
		}
		if(comments.length==6){
			rightArrow =arrowRight;
		}else{
			rightArrow = arrowRightF;
		}
		
		close = new Rectangle(x+396,y+7,16,16);
		y-=(y-yDes)/10;
		stage.setColor(new Color(235,235,235));
		Fonts.setSize(14);
		stage.setFont(Fonts.regFont);
		stage.drawImage(pane,x,y,null);
		for(int i=0;i<comments.length;i++){
			texts[i].story=comments[i];
			texts[i].draw(stage);
			texts[i].x=35;
			texts[i].y=y-4+i*85;
		}
		//stage.fillRect(close.x,close.y,close.width,close.height);
		stage.drawImage(leftArrow,401,y+555,null);
		stage.drawImage(rightArrow,433,y+555,null);
	}


	private void trace(String string) {
		System.out.println(string);
	}

	public void mouseClicked(MouseEvent m) {
		int mouseX = m.getX();
		int mouseY = m.getY();
		if(mouseX>close.x && mouseX<close.x+close.width && mouseY>close.y && mouseY<close.y+close.height){
			_root.comments = null;
			StorySelection.vacancy=true;
		}
		if(hitTest(leftRect,mouseX,mouseY) && page>=5){
			page-=5;
			loadComments();
		}
		if(hitTest(rightRect,mouseX,mouseY) && comments.length==6){
			page+=5;
			loadComments();
		}
	}

	private boolean hitTest(Rectangle rect, int mouseX, int mouseY) {
		if(mouseX>rect.x && mouseX<rect.x+rect.width && mouseY>rect.y && mouseY<rect.y+rect.height){
			return true;
		}
		return false;
	}

}
