package map.mapFields;

import map.MapField;

public class FenceNSE extends MapField {
	public FenceNSE (int x, int y) {		
		super(x,y, "pics/fields/Fences/FenceNSE.png");
		accessibleNorth = false;
		accessibleSouth = false;
		accessibleEast = false;
	}
}

