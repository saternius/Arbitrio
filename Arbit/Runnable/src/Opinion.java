import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;


public class Opinion {
	Game _root;
	private Image rateComment = Images.rateComment;
	private Rectangle noOpinionRect;
	private Rectangle close;
	private int x;
	private int y;
	private Image angry_dark = Images.angry_dark;
	private Image angry_light = Images.angry_light;
	private Image estatic_dark = Images.estatic_dark;
	private Image estatic_light = Images.estatic_light;
	private Image happy_dark = Images.happy_dark;
	private Image happy_light = Images.happy_light;
	private Image meh_dark = Images.meh_dark;
	private Image meh_light = Images.meh_light;
	private Image sad_dark = Images.sad_dark;
	private Image sad_light = Images.sad_light;
	private Image thanks = Images.thanks;
	private Image thanks_comment = Images.comment_thanks;
	private Image[] dark_faces = {angry_dark,sad_dark,meh_dark,happy_dark,estatic_dark};
	private Image[] light_faces = {angry_light,sad_light,meh_light,happy_light,estatic_light};
	private Image[] faces = {angry_dark,sad_dark,meh_dark,happy_dark,estatic_dark};
	private Rectangle[] rateRects = new Rectangle[5];
	public boolean rated = false;
	public Rectangle submit;
	Texty comment;
	private boolean submitted;
	public Opinion(Game game) {
		_root = game;
		x=60;
		y=310-132;
		noOpinionRect = new Rectangle(x+245,y+5,22,22);
		close = new Rectangle(x+368,y+5,22,22);
		rateRects[0]= new Rectangle(x+87,y+35,51,44);
		rateRects[1]= new Rectangle(x+140,y+35,51,44);
		rateRects[2]= new Rectangle(x+194,y+35,51,44);
		rateRects[3]= new Rectangle(x+246,y+35,51,44);
		rateRects[4]= new Rectangle(x+298,y+35,51,44);
		comment = new Texty(this,x,y+75,"");
		comment.desired_length=58;
		comment.max_length=500;
		submit = new Rectangle(318+x,230+y,69,26);
		getPreviousComment();
	}
	public void draw(Graphics stage){
		stage.drawImage(rateComment,x,y,null);
		if(!rated){
		for(int i=0;i<faces.length;i++){
			stage.drawImage(faces[i],x+92+(i*53),y+35,null);
		}
		}else{
			stage.drawImage(thanks,x+82,y+32,null);
		}
		comment.draw(stage);
		if(submitted){
			stage.drawImage(thanks_comment,x+9,y+104,null);
		}
	}
	
	@SuppressWarnings("unused")
	private void drawRect(Rectangle rect, Graphics stage) {
		stage.fillRect(rect.x, rect.y, rect.width, rect.height);
	}
	public void trace(String string){
		System.out.println(string);
	}
	public void keyPressed(KeyEvent k) {
		comment.keyPressed(k);
		if(k.getKeyCode()==27){
			_root.playGame.removeGame();
		}
	}
	public void mousePressed(MouseEvent m) {
		int mouseX = m.getX();
		int mouseY = m.getY();
		if(hitTest(close,mouseX,mouseY)){
			_root.playGame.removeGame();
		}
		if(hitTest(noOpinionRect,mouseX,mouseY)){
			Game.noMoreOpinions();
			_root.playGame.removeGame();
		}
		for(int i=0;i<rateRects.length;i++){
			if(hitTest(rateRects[i],mouseX,mouseY) && !rated){
				rated=true;
				submitRate(i);
			}
		}
		if(hitTest(submit,mouseX,mouseY) && !submitted){
			trace("submiting comment");
			submitted = true;
			submitcomment();
		}
	}
	private boolean hitTest(Rectangle rect, int mouseX, int mouseY) {
		return mouseX>rect.x && mouseX<rect.x+rect.width && mouseY>rect.y && mouseY<rect.y+rect.height;
	}
	public void mouseMoved(MouseEvent m) {
		int mouseX = m.getX();
		int mouseY = m.getY();
		for(int i=0;i<rateRects.length;i++){
			if(hitTest(rateRects[i],mouseX,mouseY)){
				faces[i] = light_faces[i];
			}else{
				faces[i] = dark_faces[i];
			}
		}
	}
	private void submitRate(int i) {
		try{
		    URL url = new URL("http://myrighttoplay.com/Arbitrio/Java/rate.php?rate="+i+"&id="+_root.id);
		    URLConnection con = url.openConnection();
		    con.setDoOutput(true);
		    PrintStream ps = new PrintStream(con.getOutputStream());
		    con.getInputStream();
		    ps.close();
		    InputStream r = con.getInputStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(r));
		    for(String line; (line = reader.readLine()) != null;){
		    	trace(line);
		    }
		}catch(Exception e){ 
			System.out.println("Failed to rate");
		}
	}
	private void submitcomment() {
		try{
			String url_string = "http://myrighttoplay.com/Arbitrio/Java/comment.php";
			trace(url_string);
			URL url = new URL(url_string);
		    URLConnection con = url.openConnection();
		    con.setDoOutput(true);
		    PrintStream ps = new PrintStream(con.getOutputStream());
		    ps.print("comment="+comment.story+"&id="+_root.id);
		    con.getInputStream();
		    ps.close();
		    InputStream r = con.getInputStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(r));
		    for(String line; (line = reader.readLine()) != null;){
		    	trace(line);
		    }
		}catch(Exception e){ 
			System.out.println("Failed to update comment");
		}
	}
	private void getPreviousComment() {
		try{
			String url_string = "http://myrighttoplay.com/Arbitrio/Java/previous_comment.php";
			trace(url_string);
			URL url = new URL(url_string);
		    URLConnection con = url.openConnection();
		    con.setDoOutput(true);
		    PrintStream ps = new PrintStream(con.getOutputStream());
		    ps.print("id="+_root.id);
		    con.getInputStream();
		    ps.close();
		    InputStream r = con.getInputStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(r));
		    for(String line; (line = reader.readLine()) != null;){
		    	comment.story=line;
		    }
		}catch(Exception e){ 
			System.out.println("Failed to update comment");
		}
	}
}
