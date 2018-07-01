package localServer.map.mapFields;

import localServer.map.MapField;

public class FenceNW extends MapField {
	public FenceNW () {		
		super();
		accessibleNorth = false;
		accessibleWest = false;
	}
}
