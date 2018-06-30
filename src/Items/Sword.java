package items;

public class Sword extends Item {
	
	public Sword () {
		super ("pics/items/sword.jpg");
		this.name = "Sword";
		this.internalName = this.name;
		this.examineText = "Sharper than a razor !";
		this.option1 = "Equip";
		this.equipZone = 3;
		this.atkBonus = 7;
		this.weight = 3;
		this.ID = 9002;
	}
}
