package client.items;

import client.App;

public class BigHealthPotion_3 extends Item {
	public BigHealthPotion_3 () {
		super("pics/items/HP2_3.png");
		this.name = "Big Health Potion";
		this.internalName = "Big Health Potion (3)";
		this.examineText = "3 doses of a health potion.";
		this.option1 = "Drink";
		this.weight = 0.7;
		this.ID = 12003;
	}
	
	public void use () {		
		App.addInfo("You drink a bit of the Health Potion.");
		App.addInfo ("You still have 2 doses left.");
		
		client.Player pl = App.actPlayer;
		pl.changeHP(10);
		pl.getInventoryManager().setItem(new BigHealthPotion_2(),this.slotID);
	}
}
