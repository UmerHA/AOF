package client.items; 

public class SlayersPlatelegs extends Item {
	public SlayersPlatelegs() {
		super("pics/items/Slayer's Platelegs.png");
		this.name = "Slayer's Platelegs";
		this.internalName = this.name;
		this.examineText = "These legendary platelegs still have blood marks on them ...";
		this.equipZone = 7;
		this.atkBonus = 1;
		this.defBonus = 65;
		this.option1 = "Equip";
		this.ID = 3001;
	}
}
