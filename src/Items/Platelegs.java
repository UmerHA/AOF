package items;

public class Platelegs extends Item {	
	public Platelegs () {
		super ("pics/items/platelegs.png");
		this.name = "Platelegs";
		this.internalName = this.name;
		this.examineText = "A pair of platelegs made out of steel.";
		this.option1 = "Equip";
		this.equipZone = 7;
		this.defBonus = 6;
		this.weight = 3.5;
		this.ID = 3002;
	}
}