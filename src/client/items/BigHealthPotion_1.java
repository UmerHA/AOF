package client.items;

import client.App;

public class BigHealthPotion_1 extends Item {
	public BigHealthPotion_1() {
		super("pics/items/HP2_1.png");
		this.name = "Big Health Potion";
		this.internalName = "Big Health Potion (1)";
		this.examineText = "1 dose of a health potion.";
		this.option1 = "Drink";
		this.weight = 0.3;
		this.ID = 12001;
	}

	public void use() {
		App.addInfo("You drink a bit of the Health Potion.");
		App.addInfo("You have used up your potion.");

		client.Player pl = App.actPlayer;
		pl.changeHP(10);
		pl.getInventoryManager().destroyItem(this.slotID);
	}
}
