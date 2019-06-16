package client.map.mapFields;

import client.map.MapField;

public class FenceNW extends MapField {
	public FenceNW () {		
		super("fields/Fences/FenceNW.png");
		accessibleNorth = false;
		accessibleWest = false;
	}
}
