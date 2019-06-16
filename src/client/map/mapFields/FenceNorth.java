package client.map.mapFields;

import client.map.MapField;

public class FenceNorth extends MapField {
	public FenceNorth (int x, int y) {		
		super(x,y, "fields/Fences/FenceNorth.png");
		accessibleNorth = false;
	}
}
