package client.map.mapObjects;

import client.map.MapObject;

public class Rock extends MapObject {

	public Rock(int x, int y) {
		super(x, y, "fields/Rock.png");
		examineTXT = "It's a simple rocky rock. By the way, it kinda looks like a rock.";
	}

	public void entered() {
		System.out.println("Rock entered ?! Strange ...");
	}

	public void exited() {
		System.out.println("Rock says : Don't leave me ");
	}
}
