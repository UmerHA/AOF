package MapFields;

public class FenceWE extends MapField {
	public FenceWE (int x, int y) {		
		super(x,y, "pics/fields/Fences/FenceWE.png");
		accessibleEast = false;
		accessibleWest = false;
	}
}
