package map;

import mainPackage.MainApplet;

public class MapObject extends MapField {
	protected String option1 = "";
	protected String examineTXT = "";
	
	public MapObject(int x, int y, String picPath) {
		super(x, y, picPath);
		this.isObject = true;
		accessibleNorth = false;
		accessibleSouth = false;
		accessibleEast = false;
		accessibleWest = false;
		openForNPC = false;
	}

	public String getOption1 () {
		return this.option1;
	}
	public String getExamineTXT () {
		return this.examineTXT;
	}
	public void examine  () {
		MainApplet.addInfo(examineTXT);
	}
	
	//override
	public void use () {}
}
