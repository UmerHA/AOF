package client.items; 

public class WoodShield extends Item {
	public WoodShield() {
		super("pics/items/Wood Shield.png");
		this.name = "Wood Shield";
		this.internalName = this.name;
		this.examineText = "A simple wooden shield.";
		this.equipZone = 5;
		this.defBonus = 2;
		this.option1 = "Equip";
		this.ID = 4001;
	}
}
