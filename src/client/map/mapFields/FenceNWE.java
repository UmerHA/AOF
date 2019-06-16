package client.map.mapFields;

import client.map.MapField;

public class FenceNWE extends MapField {
	public FenceNWE (int x, int y) {		
		super(x,y, "fields/Fences/FenceNWE.png");
		accessibleNorth = false;
		accessibleWest = false;
		accessibleEast = false;
	}
}
