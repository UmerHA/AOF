package client.map.mapFields;

import client.map.MapField;

public class Sand extends MapField {

	public Sand() {
		super("fields/Sand.jpg");
		accessibleNorth = false;
		accessibleSouth = false;
		accessibleEast = false;
		accessibleWest = false;
	}
}
