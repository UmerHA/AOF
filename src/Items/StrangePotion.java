package Items; 

import MainPackage.MainApplet;

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
		MainApplet.addInfo("You drink the strange potion ...");
		MainApplet.actPlayer.changeHP ((short) -20);
		MainApplet.actPlayer.getInventoryManager().destroyItem(this.slotID);
	}
}
