package client.items;

import client.App;

public class HealthPotion extends Item {
	
	public HealthPotion () {
		super ("pics/items/HP.gif");
		this.name = "Health Potion";
		this.internalName = this.name;
		this.examineText = "Might help me when I'm in trouble ...";
		this.option1 = "Drink";
		this.weight = 0.2;
		this.ID = 12004;
	}
	
	public void use () {
		super.use();
		short mom_hp = App.actPlayer.getMomHP();
		short max_hp = App.actPlayer.getMaxHP();
		App.addInfo("You drink a health potion ...");
		if ( mom_hp == max_hp) {
			App.actPlayer.changeHP ((short) 10);
		} else if ( mom_hp > max_hp-15) {
			int diff = max_hp-mom_hp;
			App.addInfo("You gain "+diff+" HP");
			App.actPlayer.changeHP ((short) diff);
		} else {
			App.addInfo("You gain 15 HP");
			App.actPlayer.changeHP ((short) 15);
		}
		App.actPlayer.getInventoryManager().destroyItem(this.slotID);
	}
}
