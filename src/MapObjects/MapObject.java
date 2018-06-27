package MapObjects;

import MainPackage.MainApplet;
import MapFields.MapField;

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

	public String getName () {
		String temp = this.getClass().toString();
		return temp.substring(17, temp.length());
	}
	
	//override
	public void use () {}
}
