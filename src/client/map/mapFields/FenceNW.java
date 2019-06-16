package client.map.mapFields;

import client.map.MapField;

public class FenceNW extends MapField {
	public FenceNW (int x, int y) {		
		super(x,y, "fields/Fences/FenceNW.png");
		accessibleNorth = false;
		accessibleWest = false;
	}
}
