package localServer.map.mapFields;

import localServer.map.MapField;

public class FenceNWE extends MapField {
	public FenceNWE () {		
		super();
		accessibleNorth = false;
		accessibleWest = false;
		accessibleEast = false;
	}
}
