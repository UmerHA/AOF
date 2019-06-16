package client.map.mapObjects;

import client.App;
import client.map.MapObject;

public class TropicalTree extends MapObject {

	public TropicalTree() {
		super("fields/TropicalTree.gif");
		this.option1 = "Pick fruit";
		this.examineTXT = "A tropical tree.";
	}
	
	public void use () {
		App.actPlayer.addItem(new client.items.Coconut());
		App.addInfo("You pick a fresh coconut");
	}
}