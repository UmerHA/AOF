package Items; 

public class SlayersHelmet extends Item {
	public SlayersHelmet() {
		super("pics/items/Slayer's Helmet.png");
		this.name = "Slayer's Helmet";
		this.internalName = this.name;
		this.examineText = "Perfect glasses for blood thirsty eyes.";
		this.equipZone = 0;
		this.defBonus = 35;
		this.option1 = "Equip";
		this.ID = 0001;
	}
}
