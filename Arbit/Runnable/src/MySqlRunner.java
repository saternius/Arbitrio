import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;



public class MySqlRunner {
	public MySqlRunner(){
	}

	public void store(String title, String author, createScreen _root, boolean nsfw) {
		trace("uploading..");
		try{
			String data="";
			data+="[Name]"+title;
			data+="[Author]"+author;
			data+="[NSFW]"+nsfw;
			String stories="";
			for(String story:_root.stories){
				if(story.equals("")){
					story =" ";
				}
				stories+=story+"|";
			}
			data+="[Stories]"+stories;
			String titles="";
			for(String titler:_root.titles){
				titles+=titler+"|";
			}
			data+="[Titles]"+titles;
			String moms="";
			for(int mom:_root.moms){
				moms+=""+mom+"|";
			}
			data+="[Moms]"+moms;
			String links ="";
			for(String link:_root.links){
				links+=link+"|";
			}
			data+="[Links]"+links;
			
			data=data.replace("\'", "\\'");

			trace("<--------------------------------------->");
			trace(Integer.toString(data.length()));
			trace(data);
			trace("<--------------------------------------->");
			
		    // open a connection to the site
		    URL url = new URL("http://myrighttoplay.com/Arbitrio/test.php");
		    URLConnection con = url.openConnection();
		    // activate the output
		    con.setDoOutput(true);
		    PrintStream ps = new PrintStream(con.getOutputStream());
		    // send your parameters to your site
		   ps.print("data="+data);
		    
		 
		    // we have to get the input stream in order to actually send the request
		    con.getInputStream();
		    // close the print stream
		    ps.close();
		    
		    
		    InputStream r = con.getInputStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(r));
		    for(String line; (line = reader.readLine()) != null;) {
		    	System.out.println(line);
		    	if(line.equals("Upload Successful")){
		    		System.out.println("twas successful");
		    		_root.uploadStatus = new UploadStatus(_root,0);
		    	}else if(line.equals("Upload Unsuccessful")){
		    		System.out.println("twas unsuccessful");
		    		_root.uploadStatus = new UploadStatus(_root,1);
		    	}else{
		    		System.out.println("twas spam");
		    		_root.uploadStatus = new UploadStatus(_root,2);
		    	}
		    	
		    }
		  
		}catch(Exception e){ 
			_root.uploadStatus = new UploadStatus(_root,1);
			trace("It failed");
			}
		
	}
	
	
	public void trace(String s){
		System.out.println(s);
	}
}
