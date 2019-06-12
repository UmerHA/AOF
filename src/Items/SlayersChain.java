package items; 

public class SlayersChain extends Item {
	public SlayersChain() {
		super("pics/items/Slayer's Chain.png");
		this.name = "Slayer's Chain";
		this.internalName = this.name;
		this.examineText = "Dragon, demon, gargoyle and skeleton hanging around your neck ...";
		this.equipZone = 2;
		this.atkBonus = 3;
		this.defBonus = 15;
		this.option1 = "Equip";
		this.ID = 5001;
	}
}
