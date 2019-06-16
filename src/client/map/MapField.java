package client.map;

import java.awt.Image;

import client.Util;

public class MapField {
	String name;
	
	private Image mapImage;
	String mapImageName;
	
	protected boolean accessibleNorth = true;
	protected boolean accessibleSouth = true;
	protected boolean accessibleEast = true;
	protected boolean accessibleWest = true;
	
	public boolean isNotBasic = true;
	private boolean isTakeByNPC = false;
	protected boolean openForNPC = true;
	
	private int takenBy = -1;

	protected boolean isObject = false;
	
	public MapField (String picPath) {
		this.mapImageName = picPath;
		
		this.mapImage = Util.getImage(picPath);
	}
	
	public boolean checkAccessible (int dir) {
		return checkAccess((short) dir);
	}
	public boolean checkAccessible (short dir) {
		return checkAccess (dir);
	}
	public boolean checkAccessible (String dir) {
		short direction = 0;
		if (dir.equalsIgnoreCase("west"))
			direction = 1;
		if (dir.equalsIgnoreCase("north"))
			direction = 2;
		if (dir.equalsIgnoreCase("east"))
			direction = 3;
		if (dir.equalsIgnoreCase("south"))
			direction = 4;
		
		if (direction == 0)
			System.out.println ("No valid direction given");
		
		return checkAccess (direction);
	}
	
	private boolean checkAccess (short dir) {
		switch (dir) {	
			case 1 : if (accessibleWest) {return true;} else {return false;}
			case 2 : if (accessibleNorth) {return true;}else {return false;}
			case 3 : if (accessibleEast) {return true;} else {return false;}
			case 4 : if (accessibleSouth) {return true;}else {return false;}
			default : return false;
		}

	}
	
	public void entered () {		}
	public void exited () {}
	
	public Image getImage () {
		return mapImage;
	}
	public boolean isTaken () {
		return this.isTakeByNPC;
	}
	public void take (int taker_id) {
		this.isTakeByNPC = true;
		this.takenBy = taker_id;
	}
	public void free () {
		this.isTakeByNPC = false;
		this.takenBy = -1; // default, -1 heiﬂt nicht besetzt
	}
	
	public boolean isOpenForNPC() {
		return openForNPC;
	}
	public void setOpenForNPC( boolean state) {
		this.openForNPC = state;
	}
	public int getOwnerID () {
		return this.takenBy;
	}
	public String getName () {
		String[] tmp = this.getClass().toString().split("\\.");
		return tmp[tmp.length -1];
	}
	public boolean isObject() {
		return this.isObject ;
	}
}
