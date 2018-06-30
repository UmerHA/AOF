package map.mapBases;

import java.awt.Image;

import map.MapBase;

public class Grass extends MapBase {
	
	private static Image pic;

	public Grass (int x, int y) {
		super (x,y,"pics/fields/grass.jpg");
		isNotBasic = false;
		pic = this.getImage();
	}
	
	public void entered () {
		// System.out.println ("I've been entered : " + this.x + " " + this.y);
	}
	
	public static Image getStaticImage () {
		return pic;
	}
}