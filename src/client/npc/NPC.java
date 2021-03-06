package client.npc;

import java.awt.Image;

import client.App;
import client.IntPair;
import client.Map;
import client.Util;
import client.myObject;
import client.npc.monster.Ankou;
import client.npc.monster.BigGoblin;
import client.npc.monster.Big_kA;
import client.npc.monster.Chicken;
import client.npc.monster.Demon;
import client.npc.monster.Dog;
import client.npc.monster.Dragon;
import client.npc.monster.Dude;
import client.npc.monster.Goblin;
import client.npc.monster.Jad;
import client.npc.monster.MithrilDragon;
import client.npc.monster.Phoenix;
import client.npc.monster.Sheep;
import client.npc.monster.kA;
import client.npc.tnpc.Adventurer;
import client.npc.tnpc.Edward;
import client.npc.tnpc.Farmer;
import client.npc.tnpc.Joe;

public class NPC extends myObject {
	protected Image largeImage;
	protected Image Img100;
	protected Image Img30;
	public static Image deadImage = Util.getImage("monster/small/dead.png");
	
	// position on current player's map
	public int posX; 
	public int posY;
	// position in real map
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
	
	public NPC (int x,int y, int id, String imageName) {
		super();
		this.id = id;
		this.spawnX = x;
		this.spawnY = y;
		this.mapX = x;
		this.mapY = y;
		this.posX = x;
		this.posY = y;
		this.Img30 = Util.getImage("monster/small/" + imageName);
	}
	public void setDelay (int delay) {
		this.delay = delay;
	}	
	public void use () {}

	/* - */
	
	public void moveByServer (int x, int y) {
		App.map.fields.get(new IntPair(mapX, mapY)).free();
		
		mapX = x;
		mapY = y;
		
		recalcPosXY();

		App.map.fields.get(new IntPair(mapX, mapY)).take(id);
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
	
	public void recalcPosXY() {		
		posX = Map.transformToPosX(mapX);
		posY = Map.transformToPosY(mapY);
	}
}
