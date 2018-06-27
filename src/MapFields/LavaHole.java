package MapFields;

import MainPackage.MainApplet;

public class LavaHole extends MapField {
	public LavaHole (int x, int y) {		
		super(x,y, "pics/fields/LavaHole.jpg");
		this.setOpenForNPC (false);
	}
	
	public void entered () {		
		MainApplet.addInfo("You step on lava and loose 5 HP");
		MainApplet.addInfo("There's a hole underneath the lava ...");
		MainApplet.actPlayer.changeHP ((short) -5);
		MainApplet.map.changeLV((byte)-1);
	}
}
