package client.map.mapFields;

import client.App;
import client.map.MapField;

public class Lava extends MapField {
	public Lava (int x, int y) {		
		super(x,y, "fields/Lava.jpg");
		this.setOpenForNPC (false);
	}
	
	public void entered () {		
		App.addInfo("You step on lava ...");
		App.addInfo("You lose 15 HP");
		App.actPlayer.changeHP ((short) -15);
	}
}
