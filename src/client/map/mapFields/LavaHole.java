package client.map.mapFields;

import client.App;
import client.map.MapField;

public class LavaHole extends MapField {
	public LavaHole () {		
		super("fields/LavaHole.jpg");
		this.setOpenForNPC (false);
	}
	
	public void entered () {		
		App.addInfo("You step on lava and loose 5 HP");
		App.addInfo("There's a hole underneath the lava ...");
		App.actPlayer.changeHP ((short) -5);
		App.map.changeLV((byte)-1);
	}
}
