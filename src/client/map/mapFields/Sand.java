package client.map.mapFields;

import client.map.MapField;

public class Sand extends MapField {

	public Sand(int x, int y) {
		super(x, y, "fields/Sand.jpg");
		accessibleNorth = false;
		accessibleSouth = false;
		accessibleEast = false;
		accessibleWest = false;
	}
}
