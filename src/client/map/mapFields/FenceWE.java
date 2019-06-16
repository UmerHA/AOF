package client.map.mapFields;

import client.map.MapField;

public class FenceWE extends MapField {
	public FenceWE (int x, int y) {		
		super(x,y, "fields/Fences/FenceWE.png");
		accessibleEast = false;
		accessibleWest = false;
	}
}
