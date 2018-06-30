package map;

public class MapBase extends MapField {
	public MapBase(int x, int y, String picPath) {
		super(x, y, picPath);
	}

	public String getName () {
		String temp = this.getClass().toString();
		return temp.substring(15, temp.length());
	}
}
