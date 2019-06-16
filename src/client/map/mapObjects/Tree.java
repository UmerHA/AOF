package client.map.mapObjects;

import client.App;
import client.map.MapObject;

public class Tree extends MapObject {
	
	public Tree(int x, int y) {
		super(x, y, "fields/Tree.gif");
		this.option1 = "Look at";
		this.examineTXT = "A tree.";
	}
	
	public void use () {
		App.addInfo("It looks kinda tree-like.");
	}
}
