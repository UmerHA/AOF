package client.map.mapObjects;

import client.App;
import client.map.MapObject;

public class LadderDown extends MapObject {
	public LadderDown (int x, int y) {		
		super(x,y, "fields/LadderDown.gif");
		this.option1 = "Climb down";
		this.examineTXT = "Gives access to the level below.";
	}
	
	public void use () {
		App.addInfo("You climb down the ladder.");
		App.map.changeLV((byte)-1);
		App.actPlayer.move((short)4);
	}
}
