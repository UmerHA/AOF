package client.map.mapFields;

import client.map.MapField;

public class FenceNE extends MapField {
	public FenceNE () {		
		super("fields/Fences/FenceNE.png");
		accessibleNorth = false;
		accessibleEast = false;
	}
}
