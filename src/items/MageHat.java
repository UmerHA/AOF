package items;

public class MageHat extends Item {
	
	public MageHat () {
		super ("pics/items/MageHat.png");
		this.name = "Mage Hat";
		this.internalName = this.name;
		this.examineText = "This looks cool on a mage's head.";
		this.option1 = "Equip";
		this.equipZone = 0;
		this.defBonus = 3;
		this.weight = 0.4;
		this.ID = 0002;
	}
}