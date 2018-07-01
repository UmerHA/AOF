package localServer.map.mapFields;

import localServer.map.MapField;

public class FenceNSW extends MapField {
	public FenceNSW () {		
		super();
		accessibleNorth = false;
		accessibleSouth = false;
		accessibleWest = false;
	}
}