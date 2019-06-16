package client.map.mapFields;

import client.map.MapField;

public class FenceNSW extends MapField {
	public FenceNSW (int x, int y) {		
		super(x,y, "fields/Fences/FenceNSW.png");
		accessibleNorth = false;
		accessibleSouth = false;
		accessibleWest = false;
	}
}