package client.items;

import client.App;

public class BigHealthPotion_2 extends Item {
	public BigHealthPotion_2 () {
		super("pics/items/HP2_2.png");
		this.name = "Big Health Potion";
		this.internalName = "Big Health Potion (2)";
		this.examineText = "2 doses of a health potion.";
		this.option1 = "Drink";
		this.weight = 0.5;
		this.ID = 12002;
	}
	
	public void use () {		
		App.addInfo("You drink a bit of the Health Potion.");
		App.addInfo ("You still have 1 dose left.");
		
		client.Player pl = App.actPlayer;
		pl.changeHP(10);
		pl.getInventoryManager().setItem(new BigHealthPotion_1(),this.slotID);
	}
}
