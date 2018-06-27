package MapFields;

public class FenceNS extends MapField {
	public FenceNS (int x, int y) {		
		super(x,y, "pics/fields/Fences/FenceNS.png");
		accessibleNorth = false;
		accessibleSouth = false;
	}
}
