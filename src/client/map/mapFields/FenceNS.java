package client.map.mapFields;

import client.map.MapField;

public class FenceNS extends MapField {
	public FenceNS (int x, int y) {		
		super(x,y, "fields/Fences/FenceNS.png");
		accessibleNorth = false;
		accessibleSouth = false;
	}
}
