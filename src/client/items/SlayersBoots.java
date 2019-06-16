package client.items;

public class SlayersBoots extends Item {

	public SlayersBoots () {
		super("pics/items/Slayer's Boots.png");
		this.name = "Slayer's Boots";
		this.internalName = this.name;
		this.examineText = "Some red boots";
		this.equipZone = 9;
		this.atkBonus = 3;
		this.defBonus = 5;
		this.option1 = "Equip";
		this.ID = 1001;
	}
}
