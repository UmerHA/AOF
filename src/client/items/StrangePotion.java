package client.items; 

import client.App;

public class StrangePotion extends Item {
	
	public StrangePotion () {
		super ("pics/items/StrangePotion.png");
		this.name = "Strange Potion";
		this.internalName = this.name;
		this.examineText = "I wonder what will happen if I drink that ...";
		this.option1 = "Drink";
		this.weight = 0.1;
		this.ID = 12006;
	}
	
	public void use () {
		super.use();
		App.addInfo("You drink the strange potion ...");
		App.actPlayer.changeHP ((short) -20);
		App.actPlayer.getInventoryManager().destroyItem(this.slotID);
	}
}
