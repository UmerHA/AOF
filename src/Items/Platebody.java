package Items;

public class Platebody extends Item {
	
	public Platebody () {
		super ("pics/items/platebody.png");
		this.name = "Platebody";
		this.internalName = this.name;
		this.examineText = "A platebody made out of steel.";
		this.option1 = "Equip";
		this.equipZone = 4;
		this.defBonus = 10;
		this.weight = 5.8;
		this.ID = 2002;
	}
}