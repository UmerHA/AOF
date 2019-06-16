package client.map.mapFields;

import client.map.MapField;

public class FenceSW extends MapField {
	public FenceSW () {		
		super("fields/Fences/FenceSW.png");
		accessibleSouth = false;
		accessibleWest = false;
	}
}
