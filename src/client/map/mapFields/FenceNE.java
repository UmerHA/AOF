package client.map.mapFields;

import client.map.MapField;

public class FenceNE extends MapField {
	public FenceNE (int x, int y) {		
		super(x,y, "fields/Fences/FenceNE.png");
		accessibleNorth = false;
		accessibleEast = false;
	}
}
