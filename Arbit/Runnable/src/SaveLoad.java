import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SaveLoad {
	int stageWidth;
	int stageHeight;
	Rectangle close;
	Rectangle accept;
	int x;
	int y;
	Image pane = Images.save_n_load;
	Image loading = Images.Loading_Data;
	boolean save = false;
	String title = "Load Document";
	String docName = "untitled";
	createScreen _root;
	ArrayList<String> files = new ArrayList<String>();
	ArrayList<saveButton> saveButtons = new ArrayList<saveButton>();
	private boolean isloading = false;
	private int wait = 0;
	private int loaded = 0;
	private String progress = "Connecting to the server..";

	@SuppressWarnings("unchecked")
	public SaveLoad(createScreen _root, boolean save) {
		this.stageHeight = _root.stageHeight;
		this.stageWidth = _root.stageWidth;
		x = stageWidth / 2 - (pane.getWidth(null) / 2);
		y = stageHeight / 2 - (pane.getHeight(null) / 2);
		accept = new Rectangle(x + 341, y + 10, 25, 25);// creats the accept
														// hitBox
		close = new Rectangle(x + 372, y + 10, 25, 25);// creates the close
														// hitBox
		this.save = save;

		if (save) {
			title = "Save Document";
		}
		this._root = _root;

		try {
			// Open file to read from, named SavedObj.sav.
			FileInputStream saveFile = new FileInputStream("Files.arbity");
			ObjectInputStream saver = new ObjectInputStream(saveFile);
			files = (ArrayList<String>) saver.readObject();
			System.out.println(files);
			saver.close();
		} catch (Exception e) {
		}

		// create saveButtons
		int yPos = 302;
		int xPos = 85;
		for (int i = 0; i < files.size(); i++) {
			yPos += 17;
			if (yPos > 360) {
				xPos += 126;
				yPos = 318;
			}
			saveButton sb = new saveButton(xPos, yPos, files.get(i));
			saveButtons.add(sb);
		}
	}

	public void draw(Graphics stage) {
		onEnterFrame();
		stage.drawImage(pane, x, y, null);
		// stage.fillRect(close.x, close.y, close.width, close.height);
		// stage.fillRect(accept.x, accept.y, accept.width, accept.height);
		stage.setColor(new Color(77, 82, 96));
		stage.setFont(new Font("Biko", Font.PLAIN, 20));
		stage.drawString(title, x + 15, y + 26);
		stage.setColor(new Color(96, 102, 119));
		stage.setFont(new Font("Lucida Console", Font.BOLD, 25));
		stage.drawString(docName, x + 22, y + 63);
		for (saveButton sb : saveButtons) {
			sb.draw(stage);
		}

		if (isloading) {
			stage.drawImage(loading, 45, 145, null);
			Fonts.setSize(14);
			stage.setFont(Fonts.regFont);
			stage.drawImage(loading,45,145,null);
			stage.setColor(new Color(204,204,204));
			stage.drawRect(66,182,358,22);
			stage.fillRect(66, 182, loaded, 22);
			stage.drawString(progress,300,222);

			wait++;
			if (wait == 2) {
				submit();
			}
		}

	}

	private void onEnterFrame() {

	}

	public void keyPressed(KeyEvent k) {
		if (k.getKeyCode() == 8 && docName.length() > 0) {
			docName = docName.substring(0, docName.length() - 1);
		} else {
			if (docName.equals("untitled")) {
				docName = "";
			}
			if (k.getKeyChar() == ' ' && docName.length() < 12) {
				docName += "_";
			}
			if (((k.getKeyChar() >= 48 && k.getKeyChar() <= 122))
					&& docName.length() < 12) {
				docName += k.getKeyChar();
			}
			if (k.getKeyCode() == 10) {
				isloading = true;
				// submit();
			}
		}

	}

	private void submit() {
		if (save) {
			try { // Catch errors in I/O if necessary.
					// Open a file to write to, named SavedObj.sav.

				ArrayList<Integer> box_x = new ArrayList<Integer>();
				ArrayList<Integer> box_y = new ArrayList<Integer>();
				for (Box box : _root.boxes) {
					box_x.add(box.x);
					box_y.add(box.y);
				}

				FileOutputStream saveFile = new FileOutputStream(docName
						+ ".arbit");

				// Create an ObjectOutputStream to put objects into save file.
				ObjectOutputStream save = new ObjectOutputStream(saveFile);

				// Now we do the save.
				save.writeObject(_root.titles);
				save.writeObject(_root.moms);
				save.writeObject(_root.links);
				save.writeObject(_root.stories);
				save.writeObject(box_x);
				save.writeObject(box_y);

				// save.writeObject(setting);
				// save.writeObject(plant);
				// Close the file.
				if (files.indexOf(docName) < 0) {
					files.add(docName);
				}
				save.close(); // This also closes saveFile.
				System.out.println("You just saved this shindizz brah");
				die();
			} catch (Exception exc) {
				exc.printStackTrace(); // If there was an error, print the info.
			}
		} else {
			initiateNewThread();

		}
		

	}

	private void initiateNewThread() {
		Thread one = new Thread() {
			@SuppressWarnings("unchecked")
			public void run() {
				progress="Fetching game data..";
				 try {
						// Open file to read from, named SavedObj.sav.
						FileInputStream saveFile = new FileInputStream(docName+".arbit");

						// Create an ObjectInputStream to get objects from save file.
						ObjectInputStream save = new ObjectInputStream(saveFile);

						// Now we do the restore.
						// readObject() returns a generic Object, we cast those back
						// into their original class type.
						// For primitive types, use the corresponding reference class.
						/*
						powerSwitch = (Boolean) save.readObject();
						x = (Integer) save.readObject();
						y = (Integer) save.readObject();
						z = (Integer) save.readObject();
						name = (String) save.readObject();
						setting = (String) save.readObject();
						plant = (String) save.readObject();*/
						_root.titles=(ArrayList<String>) save.readObject();
						_root.moms=(ArrayList<Integer>) save.readObject();
						_root.links=(ArrayList<String>) save.readObject();
						_root.stories=(ArrayList<String>) save.readObject();
						 ArrayList<Integer> box_x = (ArrayList<Integer>) save.readObject();
						 ArrayList<Integer> box_y = (ArrayList<Integer>) save.readObject();
						System.out.println(_root.titles);
						System.out.println(_root.moms);
						System.out.println(_root.links);
						System.out.println(_root.stories);
						System.out.println(box_x);
						System.out.println(box_y);
						
						
						//generates new createScreen Boxes
						_root.boxes = new ArrayList<Box>();
						for(int i=0;i<_root.titles.size();i++){
							Box mother =null;
							if(_root.moms.get(i)>-1){
								mother=_root.boxes.get(_root.moms.get(i));
							}
							Box box = new Box(box_x.get(i),box_y.get(i),_root.titles.get(i),_root,mother);
							_root.boxes.add(box);
							_root.images.add(null);
							if(_root.stories.get(i).length()>0){
								box.highlight=true;
							}
							
							
							//GENERATES IMAGES
							if(_root.links.get(i)!=null && _root.links.get(i).length()>0){
								loaded = (int) ((((double)i/(double)_root.titles.size()))*358);
								progress = "Fetching image "+(i+1)+" of "+(_root.titles.size()+1);
								String photoLink = _root.links.get(i);
								
									System.out.println("Generating image, please wait..");		
									try{
									  BufferedImage img= ImageIO.read(new URL(photoLink));
									  _root.images.set(i, img);
									}catch(Exception e){
										System.out.println("Yo image failed brah");
									
								}
							
							}
							
							
						
							
							
						}
						if(files.indexOf(docName)<0){
							files.add(docName);
						}
						
						// Close the file.
						save.close(); // This also closes saveFile.
					} catch (Exception exc) {
						exc.printStackTrace(); // If there was an error, print the info.
					}
				 die();
			}
		};

		one.start();

	}

	public void mousePressed(MouseEvent m) {
		System.out.println("CLICKY");
		int mouseX = m.getX();
		int mouseY = m.getY();
		if (mouseX > close.x && mouseX < close.x + close.width
				&& mouseY > close.y && mouseY < close.y + close.height) {
			die();
		}
		if (mouseX > accept.x && mouseX < accept.x + accept.width
				&& mouseY > accept.y && mouseY < close.y + close.height) {
			isloading = true;
			// submit();
		}
		try {
			for (saveButton sb : saveButtons) {

				sb.mousePressed(m, this);

			}
		} catch (Exception e) {
		}

	}

	private void die() {
		isloading = false;
		try {
			FileOutputStream saveFile = new FileOutputStream("Files.arbity");
			ObjectOutputStream save = new ObjectOutputStream(saveFile);

			// Now we do the save.
			save.writeObject(files);
			save.close();
		} catch (Exception e) {
		}
		_root.vacancy = true;
		_root.centerBoxes();
		_root.save_n_load = null;
	}

	public void mouseMoved(MouseEvent m) {
		for (saveButton sb : saveButtons) {
			sb.mouseMoved(m);
		}
	}

}