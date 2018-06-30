package map.mapObjects;

import mainPackage.MainApplet;
import map.MapObject;

public class LadderUp extends MapObject {
	public LadderUp (int x, int y) {		
		super(x,y, "pics/fields/LadderUp.gif");
		this.option1 = "Climp up";
		this.examineTXT = "Used to reach higher levels";
	}
	
	public void use () {
		MainApplet.addInfo("You climb up the ladder.");
		MainApplet.map.changeLV((byte)1);
		MainApplet.actPlayer.move((short)4);
	}
}
