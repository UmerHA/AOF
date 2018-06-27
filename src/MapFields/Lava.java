package MapFields;

import MainPackage.MainApplet;

public class Lava extends MapField {
	public Lava (int x, int y) {		
		super(x,y, "pics/fields/Lava.jpg");
		this.setOpenForNPC (false);
	}
	
	public void entered () {		
		MainApplet.addInfo("You step on lava ...");
		MainApplet.addInfo("You lose 15 HP");
		MainApplet.actPlayer.changeHP ((short) -15);
	}
}
