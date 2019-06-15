package items;

import mainPackage.MainApplet;

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
		MainApplet.addInfo("You drink a bit of the Health Potion.");
		MainApplet.addInfo("You have used up your potion.");

		mainPackage.Player pl = MainApplet.actPlayer;
		pl.changeHP(10);
		pl.getInventoryManager().destroyItem(this.slotID);
	}
}
