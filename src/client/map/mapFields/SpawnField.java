package client.map.mapFields;

import client.map.MapField;

public class SpawnField extends MapField { 
	public SpawnField (int x, int y) {
		super (x,y,"fields/grass.jpg");
		this.openForNPC = false; 
	} 
}