import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;


public class mySqlFetcher {
	PlayGame _root;
	public int page;
	public int catagory;
	public boolean nsfw;
	
	public String[] ids;
	public String[] authors;
	public String[] views;
	public String[] dates;
	public String[] rating;
	public String[] comments;
	public String[] titles;
	public String[] nsfws;
	public String[] urls = {
			"http://myrighttoplay.com/Arbitrio/Java/featured.php",
			"http://myrighttoplay.com/Arbitrio/Java/hot.php",
			"http://myrighttoplay.com/Arbitrio/Java/high_rating.php",
			"http://myrighttoplay.com/Arbitrio/Java/newest.php",
			"http://myrighttoplay.com/Arbitrio/Java/contriversal.php"
							};
	private boolean success=true;
	public mySqlFetcher(int catagory, int page, boolean nsfw, PlayGame playGame) {
		_root=playGame;
		this.catagory=catagory;
		this.page=page;
		this.nsfw=nsfw;
		System.out.println("Loading content..");
		
		try{
		    // open a connection to the site
		    URL url = new URL(urls[catagory]);
		    URLConnection con = url.openConnection();
		    // activate the output
		    con.setDoOutput(true);
		    PrintStream ps = new PrintStream(con.getOutputStream());
		    // send your parameters to your site
		    ps.print("&nsfw="+nsfw+"&page="+page);
		 
		    // we have to get the input stream in order to actually send the request
		    con.getInputStream();
		    // close the print stream
		    ps.close();
		    
		    
		    InputStream r = con.getInputStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(r));
		    int i=0;
		    for(String line; (line = reader.readLine()) != null;){
		    	//System.out.println(line);
		    	if(i==0){
		    		ids = line.split("/////");
		    	}
		    	if(i==1){
		    		titles=line.split("/////");
		    	}
		    	if(i==2){
		    		authors=line.split("/////");
		    	}
		    	if(i==3){
		    		dates=line.split("/////");
		    	}
		    	if(i==4){
		    		rating=line.split("/////");
		    	}
		    	if(i==5){
		    		views=line.split("/////");
		    	}
		    	if(i==6){
		    		nsfws=line.split("/////");
		    	}
		    	if(i==7){
		    		comments=line.split("/////");
		    	}
		    	i++;
		    }
		    String ID ="[";
		    String AUTH= "[";
		    String TITLE= "[";
		    String DATES= "[";
		    String VIEWS= "[";
		    String NSFWS= "[";
		    String COMM= "[";
		    for(int n=0;n<ids.length;n++){
		    	ID+=ids[n]+",";
		    	AUTH+=authors[n]+",";
		    	TITLE+=titles[n]+",";
		    	DATES+=dates[n]+",";
		    	VIEWS+=views[n]+",";
		    	NSFWS+=nsfws[n]+",";
		    	COMM+=comments[n]+",";
		    }
		    ID+="]";
	    	AUTH+="]";
	    	TITLE+="]";
	    	DATES+="]";
	    	VIEWS+="]";
	    	NSFWS+="]";
	    	COMM+="]";
	    	System.out.println(ID);
	    	System.out.println(AUTH);
	    	System.out.println(TITLE);
	    	System.out.println(DATES);
	    	System.out.println(VIEWS);
	    	System.out.println(NSFWS);
	    	System.out.println(COMM);
	    	
		}catch(Exception e){ 
			//Failed to connect to server
			System.out.println("Failed to load contents");
			success=false;
			_root._root.menu.noConnect=100;
			_root._root.menu.rise=true;
		}
		if(success){
		_root.generateFetch(ids.length,ids,authors,titles,dates,views,nsfws,comments,rating);
		_root._root.menu.fall=true;
		_root._root.menu.play_btn.createFlag=false;
		}
	}
	
}
