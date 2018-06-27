package Items;

import MainPackage.MainApplet;

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
		short mom_hp = MainApplet.actPlayer.getMomHP();
		short max_hp = MainApplet.actPlayer.getMaxHP();
		MainApplet.addInfo("You drink a health potion ...");
		if ( mom_hp == max_hp) {
			MainApplet.actPlayer.changeHP ((short) 10);
		} else if ( mom_hp > max_hp-15) {
			int diff = max_hp-mom_hp;
			MainApplet.addInfo("You gain "+diff+" HP");
			MainApplet.actPlayer.changeHP ((short) diff);
		} else {
			MainApplet.addInfo("You gain 15 HP");
			MainApplet.actPlayer.changeHP ((short) 15);
		}
		MainApplet.actPlayer.getInventoryManager().destroyItem(this.slotID);
	}
}
