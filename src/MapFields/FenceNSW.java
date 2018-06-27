package MapFields;

public class FenceNSW extends MapField {
	public FenceNSW (int x, int y) {		
		super(x,y, "pics/fields/Fences/FenceNSW.png");
		accessibleNorth = false;
		accessibleSouth = false;
		accessibleWest = false;
	}
}