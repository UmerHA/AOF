package map.mapFields;

import map.MapField;

public class FenceNE extends MapField {
	public FenceNE (int x, int y) {		
		super(x,y, "pics/fields/Fences/FenceNE.png");
		accessibleNorth = false;
		accessibleEast = false;
	}
}
