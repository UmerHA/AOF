package map.mapFields;

import mainPackage.MainApplet;
import map.MapField;


public class Water extends MapField {
	public Water (int x, int y) {		
		super(x,y, "pics/fields/Water.jpg");
		this.setOpenForNPC(false);
	}
	
	public void entered () {
		short mom_hp = MainApplet.actPlayer.getMomHP();
		short max_hp = MainApplet.actPlayer.getMaxHP();
		MainApplet.addInfo("You drink some water ...");
		if ( mom_hp == max_hp) {
			MainApplet.actPlayer.changeHP ((short) 10);
		} else if ( mom_hp > max_hp-10) {
			int diff = max_hp-mom_hp;
			MainApplet.addInfo("You gain "+diff+" HP");
			MainApplet.actPlayer.changeHP ((short) diff);
		} else {
			MainApplet.addInfo("You gain 10 HP");
			MainApplet.actPlayer.changeHP ((short) 10);
		}
	}
}
