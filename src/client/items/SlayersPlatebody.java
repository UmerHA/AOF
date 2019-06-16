package client.items; 

public class SlayersPlatebody extends Item {
	public SlayersPlatebody() {
		super("pics/items/Slayer's Platebody.png");
		this.name = "Slayer's Platebody";
		this.internalName = this.name;
		this.examineText = "This legendary platebody is said to be made out of pure blood.";
		this.equipZone = 4;
		this.atkBonus = 1;
		this.defBonus = 80;
		this.option1 = "Equip";
		this.ID = 2001;
	}
}
