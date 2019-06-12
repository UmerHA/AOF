package map.mapFields;

import map.MapField;

public class FenceSWE extends MapField {
	public FenceSWE (int x, int y) {		
		super(x,y, "pics/fields/Fences/FenceSWE.png");
		accessibleSouth = false;
		accessibleWest = false;
		accessibleEast = false;
	}
}