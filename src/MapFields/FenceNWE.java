package MapFields;

public class FenceNWE extends MapField {
	public FenceNWE (int x, int y) {		
		super(x,y, "pics/fields/Fences/FenceNWE.png");
		accessibleNorth = false;
		accessibleWest = false;
		accessibleEast = false;
	}
}
