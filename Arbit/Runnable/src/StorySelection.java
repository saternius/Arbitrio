import java.awt.Color;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class StorySelection {
	String id;
	String title;
	String views;
	String date;
	String rating;
	String author;
	String nsfw;
	int x=35;
	int y=0;
	PlayGame _root;
	@SuppressWarnings("unused")
	private Fonts font = new Fonts();
	private Rectangle hitRect;
	Image sticker = Images.nsfw_sticker;
	Image loading = Images.Loading_Data;
	private String[] stories={};
	private String[] titles={};
	private String[] moms={};
	private String[] links={};
	public static boolean vacancy=true;
	private boolean isloading=false;
	private int wait=0;
	private String progress="Connecting to servers..";
	private int loaded=0;
	
	public StorySelection(String ids, String titles, String views, String dates, String rating, String authors, String nsfws, int yPos, PlayGame playGame){
		this.id=ids;
		this.title=titles;
		this.views=views;
		this.date=dates;
		this.rating=rating;
		if(this.rating.length()>4){
			this.rating = this.rating.substring(0,4);
		}
		this.author=authors;
		this.nsfw=nsfws;
		this.y=yPos;
		this._root=playGame;
		hitRect = new Rectangle(x,y+5,400,40);
	}
	public void draw(Graphics stage) {

		stage.setColor(new Color(0,0,0));
		stage.setFont(Fonts.regFont);
		stage.drawString(title,x,y+30);
		
		@SuppressWarnings("deprecation")
		FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(Fonts.regFont);
    	int view_w = fm.stringWidth(views);
    	int date_w = fm.stringWidth(date);
    	int rating_w = fm.stringWidth(rating);
    	int author_w = fm.stringWidth(author);
		stage.drawString(views,x+105-(view_w/2),y+30);
		stage.drawString(date,x+184-(date_w/2),y+30);
		stage.drawString(rating,x+257-(rating_w/2),y+30);
		stage.drawString(author,x+394-(author_w),y+30);
		//stage.fillRect(hitRect.x,hitRect.y,hitRect.width,hitRect.height);
		if(nsfw.equals("true")){
		stage.drawImage(sticker,x-21,y-4,null);
		}
		if(isloading){
			Fonts.setSize(13);
			stage.setFont(Fonts.regFont);
			stage.drawImage(loading,45,145,null);
			stage.setColor(new Color(204,204,204));
			stage.drawRect(66,182,358,22);
			stage.fillRect(66, 182, loaded, 22);
			stage.drawString(progress,300,222);
			wait++;
			if(wait==2){
			generateGameInit();
			}
		}
	}
	public void checkClicked(MouseEvent m) {
		int mouseX=m.getX();
		int mouseY=m.getY();
		if(mouseX>hitRect.x && mouseX<hitRect.x+hitRect.width && mouseY>hitRect.y && mouseY<hitRect.y+hitRect.height&& _root._root.menu.yPos>500){
			if(vacancy){
				isloading=true;
			}
		}
	}
	private void generateGameInit() {
		initiateNewThread();
	}
		
	private void genTheGame() {
		 	
	    	//System.out.println(TITLES);
	    	//System.out.println(LINKS);
	    	//System.out.println(MOMS);
	    	//System.out.println(STORIES);
		
		
		
		createScreen create = new createScreen();
		ArrayList<String> Atitles = new ArrayList<String>();
		ArrayList<Integer> Amoms = new ArrayList<Integer>();
		ArrayList<String> Alinks = new ArrayList<String>();
		ArrayList<String> Astories = new ArrayList<String>();
		for(int i=0;i<moms.length;i++){
			Atitles.add(titles[i]);
			if(moms[i].equals("-1")){
				//System.out.println("CAUGHT IT");
				Amoms.add(-1);
			}else{
			Amoms.add(Integer.parseInt(moms[i]));
			}
			Alinks.add(links[i]);
			//System.out.println("link added:"+links[i]);
			Astories.add(stories[i]);
		}
		create.titles=Atitles;
		create.moms=Amoms;
		create.links=Alinks;
		create.stories=Astories;
		
		progress="Fetching Images";
		//generates new createScreen Boxes
		for(int i=0;i<create.titles.size();i++){
			loaded = (int) ((((double)i/(double)create.titles.size()))*358);
			progress="Fetching image "+(i+1)+" of"+(create.titles.size()+1);
			create.images.add(null);
			//GENERATES IMAGES
			if(!create.links.get(i).equals("null") && create.links.get(i).length()>0){
				String photoLink = create.links.get(i);
				
					System.out.println("Generating image, please wait..");		
					try{
					  BufferedImage img= ImageIO.read(new URL(photoLink));
					  create.images.set(i, img);
					}catch(Exception e){
						System.out.println("Yo image failed brah");
					
				}
			
			}
		}
		isloading=false;
		Game game = new Game(create,true,_root,id);
		_root.makeGame(game);
		
	}
	private void initiateNewThread() {
		Thread one = new Thread() {
		
		    public void run() {
		        if(vacancy){
					vacancy=false;
				try{
				    // open a connection to the site
				    URL url = new URL("http://myrighttoplay.com/Arbitrio/Java/fetchGame.php");
				    URLConnection con = url.openConnection();
				    // activate the output
				    con.setDoOutput(true);
				    PrintStream ps = new PrintStream(con.getOutputStream());
				    // send your parameters to your site
				    ps.print("&id="+id);
				 
				    // we have to get the input stream in order to actually send the request
				    con.getInputStream();
				    // close the print stream
				    ps.close();
				    
				    
				    InputStream r = con.getInputStream();
				    BufferedReader reader = new BufferedReader(new InputStreamReader(r));
				    int i=0;
				    for(String line; (line = reader.readLine()) != null;){
				    	//System.out.println(i+":"+line);
				    	if(i==1){
				    		//name
				    	}
				    	if(i==2){
				    		//author
				    	}
				    	if(i==3){
				    		//nsfw
				    	}
				    	if(i==4){
				    		//stories
				    		stories=line.split("/////");
				    	}
				    	if(i==5){
				    		//options
				    		titles=line.split("/////");
				    	}
				    	if(i==6){
				    		//moms
				    		moms=line.split("/////");
				    	}
				    	if(i==7){
				    		//links
				    		links=line.split("/////");
				    	}
				    	i++;
				    }
				    
				}catch(Exception e){ System.out.println("It failed");}
				//System.out.println("story:"+stories.length);
				//System.out.println("titles:"+titles.length);
				//System.out.println("moms:"+moms.length);
				//System.out.println("links:"+links.length);
				progress="Fetching Game Data";
				genTheGame();
				}
			
		    }  
		};

		one.start();
	}
	
}
