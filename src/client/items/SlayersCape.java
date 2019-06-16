package client.items;

public class SlayersCape extends Item {
	public SlayersCape() {
		super("pics/items/Slayer's Cape.png");
		this.name = "Slayer's Cape";
		this.internalName = this.name;
		this.examineText = "Legends say this cape once was white ...";
		this.equipZone = 1;
		this.atkBonus = 1;
		this.defBonus = 15;
		this.option1 = "Equip";
		this.ID = 1001;
	}
}
