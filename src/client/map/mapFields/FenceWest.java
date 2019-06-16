package client.map.mapFields;

import client.map.MapField;

public class FenceWest extends MapField {
	public FenceWest (int x, int y) {		
		super(x,y, "fields/Fences/FenceWest.png");
		accessibleWest = false;
	}
}
