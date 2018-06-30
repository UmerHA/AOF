package map.mapFields;

import map.MapField;

public class Sand extends MapField {

	public Sand(int x, int y) {
		super(x, y, "pics/fields/Sand.jpg");
		accessibleNorth = false;
		accessibleSouth = false;
		accessibleEast = false;
		accessibleWest = false;
	}
}
