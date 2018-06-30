package items;

import mainPackage.MainApplet;

public class Coconut extends Item {
	
	public Coconut () {
		super ("pics/items/Coconut.gif");
		this.name = "Coconut";
		this.internalName = this.name;
		this.examineText = "A true fact : Around 25 people die each year because a coconat fell on their head.";
		this.option1 = "Eat";
		this.weight = 0.7;
		this.ID = 13001;
	}
	
	public void use() {
		MainApplet.addInfo("You try to eat the coconut");
		MainApplet.addInfo("It's to big to eat in one piece.");
		super.use();
	}
}