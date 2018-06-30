package npc;

import java.awt.Image;
import java.awt.Toolkit;

import mainPackage.MainApplet;
import mainPackage.myObject;
import npc.monster.*;
import npc.tnpc.*;

public class NPC extends myObject {
	protected Image largeImage;
	protected Image Img100;
	protected Image Img30;
	public static Image deadImage = Toolkit.getDefaultToolkit().getImage("pics/monster/small/dead.png");
	protected int walkingRadius;
	protected int sleepingTime;
	
	public int posX;
	public int posY;
	protected int mapX;
	protected int mapY;
	protected int spawnX;
	protected int spawnY;
	protected int delay = 0;
	protected float cow = 0.8F; //chance of walking
	protected String option1; 
	protected String option2 = "Examine";
	
	public boolean inAction = false; // fighting for example
	public boolean active = false;
	
	public int id;
	
	public short dir = 0; // dirs : 0=not_moving;1=left;2=up;3=right;4=down
	
	public int getMapX () {return this.mapX;}
	public int getMapY () {return this.mapY;}
	public int getPosX () {return this.posX;}
	public int getPosY () {return this.posY;}
	public void setInAction (boolean state) {
		this.inAction = state;
	}
	public boolean isInAction () {
		return this.inAction;
	}
	
	public Image getImage30 () {
		return this.Img30;
	}
	public Image getImage100 () {return this.Img100;}
	
	public String getOption1 () {return this.option1;}
	public String getOption2 () {return this.option2;}	
	
	public NPC (int x,int y, int id, String picPath) {
		this.id = id;
		this.spawnX = x;
		this.spawnY = y;
		this.mapX = x;
		this.mapY = y;
		this.posX = x;
		this.posY = y;
		
		try {
			//vom Applet laden
			this.Img30 = MainApplet.applet.getImage(MainApplet.applet.getCodeBase(), picPath);
		} catch (NullPointerException e) {
			//normal laden , da der VMB ausgeführt wird
			this.Img30 = java.awt.Toolkit.getDefaultToolkit().getImage("bin/"+picPath);
		}
	}
	public void setRadius (int i) {
		this.walkingRadius = i;
	}
	public void setSpeed (int pause) {
		this.sleepingTime = pause;
	}
	public void setDelay (int delay) {
		this.delay = delay;
	}	
	public void use () {}

	/* - */
	
	public void moveByServer (int x, int y) {
		System.out.println("["+id+"] moving to "+x+"|"+y);
		mapX = x;
		mapY = y;
		//MainApplet.getGamePanel().repaint();
	}


	public static NPC createNpcByName(String name, int x, int y, int id) {
		//TNPC
		if (name.equals("Joe"))
			return new Joe(x, y, id);	
		if (name.equals("Edward"))
			return new Edward(x, y, id);
		if (name.equals("Adventurer"))
			return new Adventurer(x,y, id);
		if (name.equals("Farmer"))
			return new Farmer(x,y, id);
		
		//monster
		if (name.equals("Dragon"))
			return new Dragon(x, y, id);
		if (name.equals("Demon"))
			return new Demon(x, y, id);
		if (name.equals("Dude"))
			return new Dude(x, y, id);;
		if (name.equals("Goblin"))
			return new Goblin(x, y, id);
		if (name.equals("Chicken"))
			return new Chicken(x, y, id);
		if (name.equals("kA"))
			return new kA(x, y, id);
		if (name.equals("MithrilDragon"))
			return new MithrilDragon(x, y, id);
		if (name.equals("Dog"))
			return new Dog(x, y, id);
		if (name.equals("Phoenix"))
			return new Phoenix(x, y, id);
		if (name.equals("BigGoblin"))
			return new BigGoblin(x, y, id);
		if (name.equals("Ankou"))
			return new Ankou(x, y, id);;
		if (name.equals("Big_kA"))
			return new Big_kA(x, y, id);
		if (name.equals("Jad"))
			return new Jad(x,y, id);
		if (name.equals("Sheep"))
			return new Sheep(x,y, id);
		
		return null;
	}
}
