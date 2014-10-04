import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;


public class UploadStatus {
	public int type = 0;
	public createScreen _root;
	Image opt_1 = Images.successful_upload;
	Image opt_2 = Images.unsuccessful_upload;
	Image opt_3 = Images.no_spam;
	Image[] panes = {opt_1,opt_2,opt_3};
	int x;
	int y;
	public int wait = 0;
	public UploadStatus(createScreen _root, int i) {
		this._root=_root;
		type=i;
		x=_root.stageWidth/2-(panes[i].getWidth(null)/2);
		y = _root.stageHeight/2-(panes[i].getHeight(null)/2);
	}

	public void draw(Graphics stage) {
		wait++;
		stage.drawImage(panes[type],x,y,null);
	}

	public void mousePressed(MouseEvent m) {
		if(wait>10){
		_root.uploadStatus=null;
		}
	}

}
