import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class Variables {
	Image pane = Images.variablePane;
	int pane_width = pane.getWidth(null);
	int pane_height = pane.getHeight(null);
	createScreen _root;
	int x;
	int y;
	Rectangle closeRect;
	Rectangle confirmRect;
	Functions funk = new Functions();
	Vars vars[] = new Vars[8];
	Fonts font = new Fonts();
	public Variables(createScreen parent){
		_root = parent;
		x=(_root.stageWidth/2)-(pane_width/2);
		y=(_root.stageHeight/2)-(pane_width/2);
		closeRect = new Rectangle(422,106,30,30);
		confirmRect = new Rectangle(390,106,30,30);
		for(int i=0; i<vars.length; i++){
			vars[i] = new Vars(x+15,y+34+(i*32),this);
		}
	}
	
	public void draw(Graphics stage) {
		stage.drawImage(pane,x,y,null);
		boolean noNoobs = true;
		Vars focused = new Vars(0,0,this);
		for(Vars var: vars){
			var.draw(stage);
			if(!var.newbie){
				noNoobs=false;
			}
			if(var.focused){
				focused=var;
			}
		}
		if(focused.y>10){
		focused.draw(stage);
		}
		if(!noNoobs){
			Fonts.setSize(12);
			stage.setFont(Fonts.regFont);
			stage.drawString("Initial value", x+250, y+35);
		}
	}

	public void mousePressed(MouseEvent m) {
		int mouseX = m.getX();
		int mouseY = m.getY();
		if(funk.hitTest(closeRect,mouseX,mouseY)){
			die();
		}
		if(funk.hitTest(confirmRect,mouseX,mouseY)){
			String[] transfer_names = new String[8];
			String[] transfer_type = new String[8];
			Object[] transfer_value = new Object[8];
			for(int i=0;i<8;i++){
				if(!vars[i].newbie){
				if(vars[i].type_str.equals("Boolean")){
					funk.trace("theres a boolean:"+ vars[i].value_str);
					transfer_names[i]= vars[i].name_str;
					transfer_type[i] = vars[i].type_str;
					transfer_value[i] = vars[i].value;
				}else if(vars[i].type_str.equals("Number")){
					funk.trace("Theres a number:"+vars[i].value_str);
					transfer_names[i]= vars[i].name_str;
					transfer_type[i] = vars[i].type_str;
					transfer_value[i] = vars[i].value;
				}else if(vars[i].type_str.equals("Text")){
					funk.trace("Theres a string:"+vars[i].value_str);
					transfer_names[i]= vars[i].name_str;
					transfer_type[i] = vars[i].type_str;
					transfer_value[i] = vars[i].value;
				}
				}else{
					transfer_names[i]= null;
					transfer_type[i] = null;
					transfer_value[i]= null;
				}
				_root.var_names=transfer_names;
				_root.var_type=transfer_type;
				_root.var_value=transfer_value;
				die();
			}
		}
		for(Vars var: vars){
			var.mousePressed(m);
		}
	}
	public void die(){
		_root.vacancy=true;
		_root.functionality=true;
		_root.variables=null;
	}

	public void keyPressed(KeyEvent k) {
		for(Vars var:vars){
			var.keyPressed(k);
		}
	}

	public void clearFocused() {
		for(Vars var:vars){
			var.focused=false;
		}
	}

}
