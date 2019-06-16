package client.items; 

public class SlayersScythe extends Item {
	public SlayersScythe() {
		super("pics/items/Slayer's Scythe.png");
		this.name = "Slayer's Scythe";
		this.internalName = this.name;
		this.examineText = "Probably the last thing my enemy will see in his life ...";
		this.equipZone = 3;
		this.atkBonus = 160;
		this.option1 = "Equip";
		this.isTwoHanded = true;
		this.ID = 9001;
	}
}
