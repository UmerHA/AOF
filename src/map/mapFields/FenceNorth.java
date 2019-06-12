package map.mapFields;

import map.MapField;

public class FenceNorth extends MapField {
	public FenceNorth (int x, int y) {		
		super(x,y, "pics/fields/Fences/FenceNorth.png");
		accessibleNorth = false;
	}
}
