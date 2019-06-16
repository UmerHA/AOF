package client.map.mapFields;

import client.map.MapField;

public class FenceNSE extends MapField {
	public FenceNSE (int x, int y) {		
		super(x,y, "fields/Fences/FenceNSE.png");
		accessibleNorth = false;
		accessibleSouth = false;
		accessibleEast = false;
	}
}

