package client.map.mapObjects;

import client.App;
import client.map.MapObject;

public class LadderUp extends MapObject {
	public LadderUp (int x, int y) {		
		super(x,y, "fields/LadderUp.gif");
		this.option1 = "Climp up";
		this.examineTXT = "Used to reach higher levels";
	}
	
	public void use () {
		App.addInfo("You climb up the ladder.");
		App.map.changeLV((byte)1);
		App.actPlayer.move((short)4);
	}
}
