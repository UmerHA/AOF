package map.mapFields;

import map.MapField;

public class FenceSE extends MapField {
	public FenceSE (int x, int y) {		
		super(x,y, "pics/fields/Fences/FenceSE.png");
		accessibleSouth = false;
		accessibleEast = false;
	}
}
