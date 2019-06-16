package client.items; 

public class SlayersRing extends Item {
	public SlayersRing() {
		super("pics/items/Slayer's Ring.png");
		this.name = "Slayer's Ring";
		this.internalName = this.name;
		this.examineText = "A slayer does not need any jewellery.";
		this.equipZone = 6;
		this.atkBonus = 5;
		this.defBonus = 5;
		this.option1 = "Equip";
		this.ID = 6001;
	}
}
