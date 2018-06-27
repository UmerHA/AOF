package MainPackage;

import java.awt.Graphics;
import java.awt.Image;

public class ExternalPlayer {
	private Image Img100;
	private Image Img30;
	
	private String name;
	private int mapX;
	private int mapY;
	private int mapLV;
	private String option1 = "Whisper"; 
	private String option2 = "Examine";
	
	public boolean inAction = false; // fighting or talking
	
	public int id;
	
	public short dir = 0; // dirs : 0=not_moving;1=left;2=up;3=right;4=down
	
	public String getName() {return this.name;}
	public int getMapX () {return this.mapX;}
	public int getMapY () {return this.mapY;}
	public int getMapLV() {return this.mapLV;}
	
	public void setInAction (boolean state) {
		this.inAction = state;
	}
	public boolean isInAction () {
		return this.inAction;
	}
	
	public Image getImage30 () {return this.Img30;}
	public Image getImage100 () {return this.Img100;}
	public String getOption1 () {return this.option1;}
	public String getOption2 () {return this.option2;}	
	

	public static void createNew(String name/*, String x, String y*/) {
		System.out.println("ExternalPlayer.creatingNew :: extPlNum = "+extPlNum);
		
		extPlayer[extPlNum] = new ExternalPlayer(name, 0, 0, 0);
		extPlNum++;
		
		System.out.println("ExternalPlayer.createdNew :: extPlNum = "+extPlNum);
		
		//MainApplet.map.repaint();
	}
	public ExternalPlayer (String name, int x, int y, int mapLV) {
		this.name = name;
		this.mapX = x;
		this.mapY = y;
		this.mapLV = mapLV;
		
		map = MainApplet.map;
		
		this.Img100 = MainApplet.applet.getImage(MainApplet.applet.getCodeBase(),"pics/player/niels.png");
	    if (this.name.equals("umer"))
	    	 this.Img100 = MainApplet.applet.getImage(MainApplet.applet.getCodeBase(),"pics/player/Eye_of_the_Sword.png");
	    
	    this.Img30 = MainApplet.applet.getImage(MainApplet.applet.getCodeBase(),"pics/player/moving/person_vorne.gif");
	}
	public static void destroy (String name) {
		ExternalPlayer temp = ExternalPlayer.getPlayerByName(name);
		if (name == null || name.trim().equals(""))
			return;//if the extPlayer with that name does not exist
		
		temp.mapX = -1;
		temp.mapY = -1;
		temp.name = "";
		//MainApplet.map.repaint();
	}
	public static void setPosition(String name, String x, String y, String mapLV) {
		ExternalPlayer temp = getPlayerByName(name);
		if (temp == null) {
			createNew (name);
			temp = getPlayerByName(name);
		}
		
		temp.mapX = Integer.parseInt(x);
		temp.mapY = Integer.parseInt(y);
		temp.mapLV = Integer.parseInt(mapLV);
		//map.repaint();
		
		//debug
		MainApplet.getGamePanel().optScreen().refresh();
	}
	
	/*
	 * to get an external player by his/her name  
	 */
	private static Map map;
	private static ExternalPlayer[] extPlayer = new ExternalPlayer[500];
	private static int extPlNum = 0;//number of external players
	public static ExternalPlayer getPlayerByID (int ID) {
		return extPlayer[ID];
	}
	public static ExternalPlayer getPlayerByName(String name) {
		//check if a player using that name is registered
		for (int i=0;i<extPlNum;i++) 
			if (extPlayer[i].getName().equals(name))
				return extPlayer[i];
		
		//otherwise return false
		return null;
	}
	public static ExternalPlayer getPlayerByPos (int x, int y, int map) {
		for (int i=0;i<extPlNum;i++) {
			ExternalPlayer temp = extPlayer[i];
			if (temp.mapLV == map && temp.mapX == x && temp.mapY == y)
				return temp;
		}
		
		return null;
	} 
	public static String getPlayerNameByPos (int x, int y, int map) {
		for (int i=0;i<extPlNum;i++) {
			ExternalPlayer temp = extPlayer[i];
			if (temp.mapLV == map && temp.mapX == x && temp.mapY == y)
				return temp.name;
		}
		
		return null;
	} 
	
	public static void paintAll (Graphics g) {
		//System.out.println("ExternalPlayer.paintAll :: extPlayerNum = "+extPlNum);
		
		/*
		 * create NullPointerException to check where this method is called from
		int[] a = new int[1];
		int b = a[7];
		*/
		
		for (int i=0;i<extPlNum;i++) {
			try {
				ExternalPlayer temp = extPlayer[i];
				
				if (extPlayer[i].mapLV == map.getMapLV()) {
					g.drawImage(temp.Img30,(temp.mapX-map.getMapX()-1)*30,(temp.mapY-map.getMapY()-1)*30,MainApplet.applet);
					//System.out.println("ExternalPlayer.paintAll : name = "+temp.getName()+" ; x = "+temp.mapX+" ; y = "+temp.mapY+" ; mapX = "+map.getMapX()+" ; mapY = "+map.getMapY());
					//System.out.println("ExternalPlayer.paintAll :: painting ; i = "+i);
				}
			} catch (NullPointerException npe) {
				//just do nohting
			}
		}
	}
}
