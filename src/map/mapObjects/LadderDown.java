package map.mapObjects;

import mainPackage.MainApplet;
import map.MapObject;

public class LadderDown extends MapObject {
	public LadderDown (int x, int y) {		
		super(x,y, "pics/fields/LadderDown.gif");
		this.option1 = "Climb down";
		this.examineTXT = "Gives access to the level below.";
	}
	
	public void use () {
		MainApplet.addInfo("You climb down the ladder.");
		MainApplet.map.changeLV((byte)-1);
		MainApplet.actPlayer.move((short)4);
	}
}
