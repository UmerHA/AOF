package MapFields;

public class FenceEast extends MapField {
	public FenceEast (int x, int y) {		
		super(x,y, "pics/fields/Fences/FenceEast.png");
		accessibleEast = false;
	}
}


