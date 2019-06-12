package items;

import mainPackage.MainApplet;

public class HalfCoconut extends Item {
	
	public HalfCoconut () {
		super ("pics/items/HalfCoconut.gif");
		this.name = "Half Coconut";
		this.internalName = this.name;
		this.examineText = "Two of these falling from a coconut tree could kill a person.";
		this.option1 = "Eat";
		this.weight = 0.35;
		this.ID = 13002;
	}
	
	public void use() {
		MainApplet.addInfo("You eat the half coconut");
		MainApplet.addInfo("You gain 5 HP");
		MainApplet.actPlayer.changeHP(5);
		super.use();
	}
}