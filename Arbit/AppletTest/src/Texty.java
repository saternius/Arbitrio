import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
  
  
  
public class Texty {
    Object _root;
    String[] printStory = { "", "", "", "", "", "", "", "", "", "" };
    int x=0;
    int y=0;
    public boolean active=true;
    public String story;
    public int desired_length = 45;
    public int max_length=300;
    public Font font = new Font("Lucida Console", Font.BOLD, 9);
    public Rectangle blink;
	private int blinkX=200;
	private int blinkY=200;
	private int blinkTimer=0;
    public Texty(Object parent,int x,int y, String story){
        _root=parent;
        this.x=x;
        this.y=y;
        this.story=story;
        System.out.println("x:" + x + "y:" + y + parent + story);
    }
    public Texty(){
        _root=null;
        x=0;
        y=0;
        story="";
    }
    public void draw(Graphics stage){
        enterFrame();
        stage.setFont(font);
        for (int s = 0; s < printStory.length; s++) {
            stage.drawString(printStory[s], x + 18, y + 48 + s * 10);
        }
        blink = new Rectangle(blinkX,blinkY,1,10);
        blinkTimer++;
        if(blinkTimer>50){
        //stage.drawRect(blink.x, blink.y, blink.width, blink.height);
        }
        if(blinkTimer>100){
        	blinkTimer=0;
        }
    }
      
    private void enterFrame() {
        // wrap it before you tap it(the key that it);
                String drawString = story;
                int i = 0;
  
                while (drawString.length() > desired_length  && i < 9) {
                    try {
                        int splitPoint = drawString.lastIndexOf(" ", desired_length-1);
                        printStory[i] = drawString.substring(0, splitPoint);
                        drawString = drawString.substring(splitPoint + 1,
                                drawString.length());
                        i++;
  
                    } catch (Exception e) {
                        // fix the spamscribble
                    }
                }
                printStory[i] = drawString;
                i++;
                for (; i < printStory.length; i++) {
                    printStory[i] = "";
                }
  
                  
          
    }
    public void keyPressed(KeyEvent k) {
        if (active) {
            if (k.getKeyCode() == 8 && story.length() > 0) {
                story = story.substring(0,
                        story.length() - 1);
            } else {
                if (story.equals("Type to create the senario.")) {
                    story = "";
                }
                int[] a = new int[100];
                int j = 0;
                for(int i = 0; i<story.length(); i++){
                    if(story.charAt(i) == (char)32){a[j] = i;
                    j++;}
                }
                int index = 0;
                for(int i = 0; i<a.length&&index<a[i] ; i++){
                    index = a[i];
                }
                  
                System.out.println(index);
                  
                if (((k.getKeyChar() >= 39 && k.getKeyChar() <= 122) || k
                        .getKeyChar() == ' ')
                        && story.length() < max_length) {
                    story += k.getKeyChar();
                }
                if(_root instanceof Scene){
                    Scene root = ((Scene)_root);
                if (k.getKeyCode() == 10
                        && !(root.parent.tut.frame > 2 && root.parent.tut.frame < 6)) {
                    root.die(root.parent);
                }
                if(story.length()>30 && (story.substring(index+1,story.length()-1)).length() > (desired_length-4)) {story += (char)32;}
                }
            }
        } else if(_root instanceof Scene) {
        	if(((Scene) _root).boxLink!=null){
            ((Scene) _root).boxLink.keyPressed(k);
        	}else{
        		((Scene) _root).abilityBox.keyPressed(k);
        	}
        }
    }
    @SuppressWarnings("unused")
	private void trace(String story) {
        System.out.println(story);
    }
	@SuppressWarnings("unused")
	public void mousePressed(MouseEvent m) {
		int mouseX=m.getX();
		int mouseY=m.getY();
	}
} 