package map.mapFields;

import map.MapField;

public class FenceSouth extends MapField {
	public FenceSouth (int x, int y) {		
		super(x,y, "pics/fields/Fences/FenceSouth.png");
		accessibleSouth = false;
	}
}
