package client.map.mapFields;

import client.map.MapField;

public class FenceSE extends MapField {
	public FenceSE (int x, int y) {		
		super(x,y, "fields/Fences/FenceSE.png");
		accessibleSouth = false;
		accessibleEast = false;
	}
}
