package map.mapObjects;

import mainPackage.MainApplet;
import map.MapObject;

public class TropicalTree extends MapObject {

	public TropicalTree(int x, int y) {
		super(x, y, "pics/fields/TropicalTree.gif");
		this.option1 = "Pick fruit";
		this.examineTXT = "A tropical tree.";
	}
	
	public void use () {
		MainApplet.actPlayer.addItem(new items.Coconut());
		MainApplet.addInfo("You pick a fresh coconut");
	}
}