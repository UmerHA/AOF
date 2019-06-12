package map.mapFields;

import map.MapField;

public class FenceWest extends MapField {
	public FenceWest (int x, int y) {		
		super(x,y, "pics/fields/Fences/FenceWest.png");
		accessibleWest = false;
	}
}
