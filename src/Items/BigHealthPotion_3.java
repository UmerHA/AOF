package Items;

import MainPackage.MainApplet;

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
		MainApplet.addInfo("You drink a bit of the Health Potion.");
		MainApplet.addInfo ("You still have 2 doses left.");
		
		MainPackage.Player pl = MainApplet.actPlayer;
		pl.changeHP(10);
		pl.getInventoryManager().setItem(new BigHealthPotion_2(),this.slotID);
	}
}
