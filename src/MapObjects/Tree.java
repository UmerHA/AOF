package MapObjects;

import MainPackage.MainApplet;

public class Tree extends MapObject {
	
	public Tree(int x, int y) {
		super(x, y, "pics/fields/Tree.gif");
		this.option1 = "Look at";
		this.examineTXT = "A tree.";
	}
	
	public void use () {
		MainApplet.addInfo("It looks kinda tree-like.");
	}
}
