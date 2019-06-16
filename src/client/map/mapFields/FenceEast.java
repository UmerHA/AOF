package client.map.mapFields;

import client.map.MapField;

public class FenceEast extends MapField {
	public FenceEast (int x, int y) {		
		super(x,y, "fields/Fences/FenceEast.png");
		accessibleEast = false;
	}
}


