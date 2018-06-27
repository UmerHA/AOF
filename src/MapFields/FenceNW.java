package MapFields;

public class FenceNW extends MapField {
	public FenceNW (int x, int y) {		
		super(x,y, "pics/fields/Fences/FenceNW.png");
		accessibleNorth = false;
		accessibleWest = false;
	}
}
