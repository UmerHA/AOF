package client.map.mapFields;

import client.map.MapField;

public class FenceSWE extends MapField {
	public FenceSWE (int x, int y) {		
		super(x,y, "fields/Fences/FenceSWE.png");
		accessibleSouth = false;
		accessibleWest = false;
		accessibleEast = false;
	}
}