package client.items;

import client.App;

public class FistOfZen extends Item {
	
	public FistOfZen() {
		super("pics/items/FistOfZen.jpg");
		this.name = "Fist Of Zen";
		this.internalName = this.name;
		this.examineText = "The greatest show ever !";
		this.equipZone = 3;
		this.atkBonus = 20;
		this.option1 = "Equip";
		this.weight = 0;
		this.ID = 9003;
	}
	
	public void use () {
		App.addRedInfo("Don't use this holy symbol !!");
		App.addBlueInfo("Otherwise I'll beat you until you have the same color as this text.");
	}
}
