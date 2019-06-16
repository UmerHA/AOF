package client.map;

import client.App;

public class MapObject extends MapField {
	protected String option1 = "";
	protected String examineTXT = "";
	
	public MapObject(String picPath) {
		super(picPath);
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
		App.addInfo(examineTXT);
	}
	
	//override
	public void use () {}
}
