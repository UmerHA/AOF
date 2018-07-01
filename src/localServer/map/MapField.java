package localServer.map;

public class MapField {
	String name;

	public boolean accessibleNorth = true;
	public boolean accessibleSouth = true;
	public boolean accessibleEast = true;
	public boolean accessibleWest = true;
	public boolean openForNPC = true;

	public MapField() {
	}

	public String getName() {
		String[] tmp = this.getClass().toString().split("\\.");
		return tmp[tmp.length - 1];
	}
}
