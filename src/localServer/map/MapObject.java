package localServer.map;

public class MapObject extends MapField {
	public MapObject() {
		super();
		accessibleNorth = false;
		accessibleSouth = false;
		accessibleEast = false;
		accessibleWest = false;
		openForNPC = false;
	}
}
