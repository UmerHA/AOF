package client.map.mapFields;

import client.map.MapField;

public class FenceSouth extends MapField {
	public FenceSouth (int x, int y) {		
		super(x,y, "fields/Fences/FenceSouth.png");
		accessibleSouth = false;
	}
}
