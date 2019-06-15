package items; 

public class SlayersGloves extends Item {
	public SlayersGloves() {
		super("pics/items/Slayer's Gloves.png");
		this.name = "Slayer's Gloves";
		this.internalName = this.name;
		this.examineText = "Why do I have a feeling these gloves want to grab deep, deep into my enemy's flesh?";
		this.equipZone = 8;
		this.atkBonus = 1;
		this.defBonus = 15;
		this.option1 = "Equip";
		this.ID = 7001;
	}
}
