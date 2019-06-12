package items;

import mainPackage.MainApplet;

public class EnergyPotion extends Item {
	
	public EnergyPotion () {
		super ("pics/items/EnergyPotion.gif");
		this.name = "Energy Potion";
		this.internalName = this.name;
		this.examineText = "This little potion will help me to be unbelieveable fast for 10 seconds !";
		this.option1 = "Drink";
		this.weight = 0.2;
		this.ID = 12005;
	}
	
	public void use () {
		super.use();
		MainApplet.actPlayer.setPauseBeetweenMove(0, 10000, this);
		MainApplet.actPlayer.getInventoryManager().destroyItem(this.slotID);
	}
}
