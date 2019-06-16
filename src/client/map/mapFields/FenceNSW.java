package client.map.mapFields;

import client.map.MapField;

public class FenceNSW extends MapField {
	public FenceNSW () {		
		super("fields/Fences/FenceNSW.png");
		accessibleNorth = false;
		accessibleSouth = false;
		accessibleWest = false;
	}
}