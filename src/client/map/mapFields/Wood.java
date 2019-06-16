package client.map.mapFields;

import client.map.MapField;

public class Wood extends MapField {
	public Wood() {
		super("fields/Wood.gif");

		this.accessibleNorth = false;
		this.accessibleSouth = false;
	}

	public void entered() {
		//System.out.println("You're entering the WOOD");
	}

	public void exited() {
		//System.out.println("WOOD says : Don't leave me");
	}
}