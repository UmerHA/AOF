package localServer.map.mapFields;

import localServer.map.MapField;

public class FenceSWE extends MapField {
	public FenceSWE () {		
		super();
		accessibleSouth = false;
		accessibleWest = false;
		accessibleEast = false;
	}
}