package mainPackage;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import connection.Connector;
import items.Item;
import npc.Monster;
import panels.InventoryManager;

public class Player {
	String fileName;
	
	private short mapX;
	private short mapY;
	private short posX = 8;
	private short posY = 6;
	
	private short pointingDir = 0; // pointing direction
	
	private String name = "";
	short lv;	// not used till now ...
	int xp;
	long points;
	
	private short max_hp;
	private short mom_hp;
	
	private short defence; // defence LV
	private short attack;  // attack  LV
	
	private long defenceXP;
	private long attackXP;
	
	private int coins;
	
	private InventoryManager inv;
	
	// for pausing between moves :
	private long timeOfLastMove = 0;
	private short pauseBeetweenMove = 150;
	
	private boolean walkedOutOfMap = false;
	private short direction;//the direction the player wants to go
	
	// for dynamic map :
	public int mapXofMap;
	public int mapYofMap;
	
	private Image pic;
	
	// for fighting
	@SuppressWarnings("unused")
	private CombatWindow cbWin;
	private boolean isInAction = false;
	
	// for LVup-messages :
	private boolean[] lvdUp = new boolean[3];
	private short skillAmount = 2;
	
	/* Quest Variabels */
	public byte cocoMilk;
	
	/* other */
	private boolean nameSet = false;//for update
	private byte stepX, stepY;
	//private byte stepSize = 6;//must be a factor of 30 (field size)
	

	public void setName(String name) {
		this.name = name;
	}
	public String getName () {	
		return this.name;
	}
	public Image getImage () {
		return this.pic;
	}
	public short getLV () {	
		return lv;
	}
	public long getPoints () {	
		return points;
	}
	public short getMaxHP () {	
		return max_hp;
	}
	public short getMomHP () {	
		return mom_hp;
	}
	public short getDef () {	
		return defence;
	}
	public int getTotalDef () {
		return (defence+inv.getTotalDefenceBonus());
	}
	public short getAtk () {	
		return attack;
	}
	public int getTotalAtk () {
		return (attack+inv.getTotalAttackBonus());
	}
	public int getCoins () {
		return coins;
	}

	public InventoryManager getInventoryManager () {
		return this.inv;
	}
	
	public int getMapX () {
		return mapX;
	}public
	int getMapY () {
		return mapY;
	}
	public byte getStepX () {
		return this.stepX;
	}
	public byte getStepY () {
		return this.stepY;
	}
	
	//constructor
	public Player () {
		inv = new InventoryManager ();
	    for (int i=0;i<30;i++)
	    	inv.setItem("",i);
	    
	    for (int i=0;i<10;i++) {
	    	try {
	    		inv.setItemEq("",i);
	    		//System.out.println ("player.construct :: data[44+i] = "+data[44+i]);
	    	} catch (Exception e) {
	    		System.out.println ("Error in player.consrtuct :: i = "+i);
	    		System.out.println (e.toString());
	    		inv.setItemEq("",i);
	    	}
	    }  
	}
	
	public boolean setIdentitiy (String username, String password) {
		//not used when using server/client-model
		fileName = "data/UserData/"+ username + ".txt";
		File userFile = MainApplet.getFile(fileName);
		
		if ((!userFile.exists()) || (!userFile.isFile())) {
			MainApplet.applet.alert("Player doesn't exist");
			return false;
		}
		
		String[] data = new String[100];
		
	    try{
	    	FileInputStream fileStream = new FileInputStream(fileName);
	    	DataInputStream dataStream = new DataInputStream(fileStream);
	    	BufferedReader fromFile = new BufferedReader(new InputStreamReader(dataStream));
	    	   
	    	String strLine; 
	    	short i = 0;
	    	//Read File Line By Line
	    	
	    	while ((strLine = fromFile.readLine()) != null)   {
	    		data[i] = strLine;
	    		i++ ;
	    	}

	    	fromFile.close();
	    }catch (Exception e){
	    	System.err.println("Error: " + e.getMessage());
	    }
	    
	    //System.out.println ("Password (entered) : \"" + password + "\"");
	    //System.out.println ("Password (real)    : \"" + data[1] + "\"");
	    
	    if (!password.equals(data[1])) {
	    	MainApplet.applet.alert("Wrong Password");
	    	MainApplet.playerValid = false;
	    	return false;
	    }	
		
	    name = data[0];
	    lv = 1;
	    points = Long.parseLong(data[4]);
	    max_hp = Short.parseShort(data[2]);
	    mom_hp = Short.parseShort(data[3]);
	    attackXP = Long.parseLong(data[5]);
	    defenceXP = Long.parseLong(data[6]);
	    coins = Integer.parseInt(data[7]);
	    mapX = Short.parseShort(data[8]);
	    mapY = Short.parseShort(data[9]);
	    cocoMilk = Byte.parseByte(data[51]);
	    
	    attack = (short) XPLVconverter.getLV(attackXP);
	    defence = (short) XPLVconverter.getLV(defenceXP);
	    
	    this.pic = MainApplet.getImage("pics/player/niels.gif");
	    if (this.name.equals("umer"))
	    	 this.pic = MainApplet.getImage("pics/player/Eye_of_the_Sword.gif");
	    
	    inv = new InventoryManager ();
	    for (int i=0;i<30;i++)
	    	inv.setItem(data[11+i],i);
	    
	    for (int i=0;i<10;i++) {
	    	try {
	    		inv.setItemEq(data[41+i],i);;
	    	} catch (Exception e) {
	    		System.out.println ("Error in player.consrtuct :: i = "+i);
	    		System.out.println (e.toString());
	    		inv.setItemEq("",i);
	    	}
	    }
	    recalcPosXY();
	    
	    MainApplet.getGamePanel().invScreen().setManager(inv);
	    MainApplet.getGamePanel().equipScreen().setManager(inv);
	    MainApplet.getGamePanel().infoScreen().getNameLabel().setText(username);
	    MainApplet.map.setLV(Byte.parseByte(data[10]));
	    MainApplet.map.setCurrentField(mapX,mapY);
	    //MainApplet.map.repaint();
	    
		boolean admin = false;
	    if (this.name.equalsIgnoreCase("umer"))
	    	admin = true;
	    if (this.name.equalsIgnoreCase("niels"))
	    	admin = true;
	    String greet = "Welcome to <game's name>, ";
	    if (admin)
	    	greet+=" Admin ";
	    
	    MainApplet.addInfo(greet+this.name);
		return true;
	}
	public void setData(String x, String y, String mapLV) {
		this.mapX = Short.parseShort(x);
		this.mapY = Short.parseShort(y);
		
		if (Byte.parseByte(mapLV) != MainApplet.map.getMapLV())//only reload map, when mapLV changed
			MainApplet.map.setLV(Byte.parseByte(mapLV));
		
		//MainApplet.map.repaint();
		if (!nameSet) {
			nameSet = true;
			return;
		}
		
		moveByServer();
		
	}
	public void recalcPosXY () {
		int difX = 0;
		int difY = 0;
		
		if (mapX<8) {
			posX=mapX;
		} else {
			posX = 8;
			difX = mapX-posX;
		}
		if (mapY<6) {
			posY=mapY;
		} else {
			posY = 6;
			difY = mapY-posY;
		}	// (8|6) is the fieldscreen's middle
		MainApplet.map.changeMapCoords(difX, difY);
	}
	
	public void logOut () {
		MainApplet.getGamePanel().chatbox().output().discardAllInfo();
		MainApplet.applet.setContentPanel(0);
	}


	public void move (short dir) {
		//System.out.println("Player.move :: moving... ; x = "+posX+" ; y = "+posY);
		if (MainApplet.getGamePanel().chatbox().inConversation())
			MainApplet.CVManager().stopConversation();
		
		if (isInAction)
			return;
				
	    Calendar now = Calendar.getInstance();
	    
	    long tempL = now.getTimeInMillis();
	    if (!(tempL >= timeOfLastMove + pauseBeetweenMove)) {
	    	//System.out.println("Player.move :: You may not move this often; returning...");
	    	return;
	    }
		
	    timeOfLastMove = tempL;
	    
	    short movePossible = Map.checkMove(mapX, mapY, dir);
	    //System.out.println("Player.move :: movePossbile : "+movePossible);
		if (movePossible != 1) {
			if (movePossible == -1) {
				if (!walkedOutOfMap)
					MainApplet.addInfo("There's no way I can go there ...");
				walkedOutOfMap = true;
			}
			return;
		}
			
		direction = dir;
		//System.out.println("map : ("+mapX+"|"+mapY+")  ;  pos : ("+posX+"|"+posY+")");
		int tempX = mapX;
		int tempY = mapY;
		switch (dir) {
		case 1:tempX -= 1;break; // left
		case 2:tempY -= 1;break; // up
		case 3:tempX += 1;break; // right
		case 4:tempY += 1;break; // down
		default:System.err.println("Player.move :: dir is not valid ; dir = "+dir);
		}
		
		//System.out.println("Player.move :: (old) x = "+mapX+" ; y = "+mapY+" ;; (new) x = "+tempX+" ; y = "+tempY);
		
		Connector.Send("mve~"+tempX+"~"+tempY+"~"+MainApplet.map.getMapLV()+"#");
	}
	public void moveByServer () {
		recalcPosXY();
	
		pointingDir = direction;
		MainApplet.map.setCurrentField(mapX,mapY);
	
		//MainApplet.map.repaint();
	
		walkedOutOfMap = false;
		
		//System.out.println("Player.moveByServer :: (new) x = "+mapX+" ; y = "+mapY);
		
		
		switch (direction) {
		case 1 : stepX =  30; break; // left
		case 2 : stepY =  30; break; // up
		case 3 : stepX = -30; break; // right
		case 4 : stepY = -30; break; // down
		default : return;
		}
		
	}
	
	public void startFightWith (Monster monster) {

		//System.out.println(tempNPC);
		cbWin = new CombatWindow(this, monster);
		isInAction = true;
		// --
		for (short i=1; i<= skillAmount; i++)
			lvdUp[i] = false;	
	}
	
	public void changeHP (short difference) {
		addToHP (difference);
	}
	
	public void changeHP (int difference) {
		addToHP ((short) difference);
	}
	private void addToHP (short difference) {
		if ((difference > 0)&&(mom_hp == max_hp)) {
			mom_hp = max_hp;
			MainApplet.addInfo("You already have full HP");
			return;
		}
		mom_hp += difference;
		if (mom_hp <= 0)
			die();

		//System.out.println ("Current HP : " + mom_hp);
		MainApplet.getGamePanel().infoScreen().refresh();
		//MainApplet.map.repaint();		
	}

	public void changeHP_noCheck (int difference, Object sender) {
		String cos = sender.getClass().toString(); // cos = class of sender
		cos = cos.substring(6,cos.length());
		//System.out.println("player.kill : cos = " + cos);
		// Sender wird überprüft, damit nicht nicht jede Klasse den player killen kann, sonst wärs ja zu easy für Hacker (dich,Niels)
		if (!cos.equals("MainPackage.CombatWindow$actLis"))
			return;
		
		if ((difference > 0)&&(mom_hp == max_hp)) {
			mom_hp = max_hp;
			MainApplet.addInfo("You already have full HP");
			return;
		}
		mom_hp += difference;	
		
		//System.out.println ("Current HP : " + mom_hp);		
		//MainApplet.map.repaint();	
	}
	
	public void setInAction (boolean state) {
		this.isInAction = state;
	}
	
	private void die () {
		mom_hp = max_hp;
		mapX = 3;
		mapY = 3;
		pointingDir = 0;
		
		short i = 0;
		while (i < 3) {
			int position = MainApplet.getRandom(0, 29);
			if (this.inv.destroyItem(position))
				i++;
		}
		
		
		MainApplet.map.changeMapCoords(0, 0);
		MainApplet.addInfo("You died and lost 3 items...");
		MainApplet.applet.alert("You died");
	}
	public void kill (Object sender) {
		String cos = sender.getClass().toString(); // cos = class of sender
		cos = cos.substring(6,cos.length());
		//System.out.println("player.kill : cos = " + cos);
		// Sender wird überprüft, damit nicht nicht jede Klasse den player killen kann, sonst wärs ja zu easy für Hacker (dich,Niels ^^)
		if (cos.equals("MainPackage.CombatWindow$actLis"));
			die();
	}
	
	public void destroyCbWin () {
		this.cbWin = null;
		this.isInAction = false;
		this.showLVupMessages ();
	}
	
	public void paint (Graphics g) {
		short x = (short) ((posX-1)*30);
		short y = (short) ((posY-1)*30);
		
		//System.out.println(posX + "|"+ posY);
		
		Image temp;
		String tempS = "pics/player/moving/";
		
		//System.out.println ("pointing direction : " + pointingDir);
		
		switch (pointingDir) {
			case 1  : temp = MainApplet.applet.getImage(MainApplet.applet.getCodeBase(),tempS+"person_links.gif");break; 		// links
			case 2  : temp = MainApplet.applet.getImage(MainApplet.applet.getCodeBase(),tempS+"person_oben.gif");break;  	 	// oben
			case 3  : temp = MainApplet.applet.getImage(MainApplet.applet.getCodeBase(),tempS+"person_rechts.gif");break; 		// rechts
			case 4  : temp = MainApplet.applet.getImage(MainApplet.applet.getCodeBase(),tempS+"person_unten.gif");break;   		// unten
			default : temp = MainApplet.applet.getImage(MainApplet.applet.getCodeBase(),tempS+"person_vorne.gif");break;	 	// default
		}
		
		//g.drawImage(temp, x+stepX, y+stepY, MainApplet.applet);
		g.drawImage(temp, x, y, MainApplet.applet);
		
		/*
		if (stepX != 0 || stepY != 0) {
			System.out.println("Player.paint :: step ("+stepX+"|"+stepY+")");
			switch (pointingDir) {
				case 1 : stepX-=stepSize;break;
				case 2 : stepY-=stepSize;break;
				case 3 : stepX+=stepSize;break;
				case 4 : stepY+=stepSize;break;
			}
		}
		*/
	}

	public boolean addXP (String skill, int xp) {
		
		// returns true when player lvsUp, otherwise not
		
		System.out.println("player.addXP : skill = "+skill+" ; xp="+xp);
		int curAtk = this.attack;
		int curDef = this.defence;
		
		if (xp<1)		// You may not add decrease XP by giving a number < 0 / if xp == 0, it doesn't change anyways...
			return false;
		
		if (skill.equals("Attack"))
			attackXP += xp;
		if (skill.equals("Defence"))
			attackXP += xp;
		
		refreshLVs();
		
		boolean lvUp = false;
		if (attack != curAtk)
			addLVupMessage (1);
		if (defence != curDef)
			addLVupMessage (2);
		return lvUp;
	}
	
	private void refreshLVs () {
		attack =  (short) XPLVconverter.getLV (attackXP);
		defence =  (short) XPLVconverter.getLV (defenceXP);
	}
	public void refreshFriendsList () {
		connection.Connector.Send("frlg~#");
	}
	public void updateFriendsList (String[] friends) {//called by server
		MainApplet.getGamePanel().chatScreen().setFriendData(friends);
	}
	
	
	public void addLVupMessage (String skill) {
		addLVupMessage (getSkillCode(skill));
	}
	public void addLVupMessage (int skillCode) {
		if (skillCode > skillAmount)
			return;
		
		lvdUp[skillCode] = true;
	}
	
	private void showLVupMessages () {
		for (short i = 1; i <= skillAmount; i++) {

			if (lvdUp[i]) {
				int tempLV = getLVbySkill (getSkillName(i));
				MainApplet.applet.alert("Congratulations !\nYou just leveled Up in " + getSkillName((int) i) + "!\n\nYou have now reached lv " + tempLV + " !");
			}
		}
	}	
	
	private int getLVbySkill (String skill) {
		if (skill.equalsIgnoreCase("attack"))
			return attack;
		if (skill.equalsIgnoreCase("defence"))
			return defence;
		
		return 0;
	}
	
	
	private int getSkillCode (String skill) {
		if (skill.equalsIgnoreCase("attack"))
			return 1;
		if (skill.equalsIgnoreCase("defence"))
			return 2;
		return 0; 	// default
	}
	private String getSkillName (int code) {
		if (code > skillAmount)
			return "";
		if (code < 1)
			return "";
		
		switch (code) {
			case 1 : return "Attack";
			case 2 : return "Defence";
			default : return "";
		}
	}
	
	public void addPoints (int x) {
		this.points += x;
		if (this.points < 0)
			this.points = 0;
	}

	public void addCoins (int x) {
		this.coins += x ;
	}
	
	public void addItem (Item item) {
		//System.out.println ("OP_GB.player.addItem :: item : "+item.getName());
		boolean itemAdded = this.inv.addItem (item);
		if (!itemAdded)
			MainApplet.addInfo("You don't have enough space to pick up another "+item.getName()+".");
	}
	
	
	// for a special item (Energy Potion):
	public void setPauseBeetweenMove (int pause, long time, Object sender) {
		String cos = sender.getClass().toString(); // cos = class of sender
		cos = cos.substring(6,cos.length());
		// Hacker-Prevention
		if (cos.equals("Items.EnergyPotion")) {
			//System.out.println ("OP_GB.player.setPauseBeetwenMove :: OK , sender is valid ");
			pauseBeetweenMove = (short) pause;
			new aThread(time).start();
		}
	}
	private void setPauseBeetweenMoveToDefault (aThread sender) {
		pauseBeetweenMove = 150;
		sender = null;	// destroy thread afterwards
	}
	private class aThread extends Thread {
		private long time;
		
		public aThread (long time) {
			super();
			this.time = time;
		}
			
		public void run () {
			try { sleep(time); } catch (Exception e) {}
			setPauseBeetweenMoveToDefault(this);
		}
	}
}