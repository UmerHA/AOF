package client.map.mapFields;

import client.App;
import client.map.MapField;


public class Water extends MapField {
	public Water (int x, int y) {		
		super(x,y, "fields/Water.jpg");
		this.setOpenForNPC(false);
	}
	
	public void entered () {
		short mom_hp = App.actPlayer.getMomHP();
		short max_hp = App.actPlayer.getMaxHP();
		App.addInfo("You drink some water ...");
		if ( mom_hp == max_hp) {
			App.actPlayer.changeHP ((short) 10);
		} else if ( mom_hp > max_hp-10) {
			int diff = max_hp-mom_hp;
			App.addInfo("You gain "+diff+" HP");
			App.actPlayer.changeHP ((short) diff);
		} else {
			App.addInfo("You gain 10 HP");
			App.actPlayer.changeHP ((short) 10);
		}
	}
}
