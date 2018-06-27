package MapFields;

public class FenceSW extends MapField {
	public FenceSW (int x, int y) {		
		super(x,y, "pics/fields/Fences/FenceSW.png");
		accessibleSouth = false;
		accessibleWest = false;
	}
}
