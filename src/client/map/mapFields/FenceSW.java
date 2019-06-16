package client.map.mapFields;

import client.map.MapField;

public class FenceSW extends MapField {
	public FenceSW (int x, int y) {		
		super(x,y, "fields/Fences/FenceSW.png");
		accessibleSouth = false;
		accessibleWest = false;
	}
}
