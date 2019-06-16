package client.map.mapFields;

import client.map.MapField;

public class FenceNWE extends MapField {
	public FenceNWE () {		
		super("fields/Fences/FenceNWE.png");
		accessibleNorth = false;
		accessibleWest = false;
		accessibleEast = false;
	}
}
