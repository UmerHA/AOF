package mainPackage;

public class myObject {
	protected String name;
	protected String examineText;

	public myObject() {
		// The default name of an object is its class name
		String[] tmp = this.getClass().toString().split("\\.");
		name = tmp[tmp.length - 1];
	}

	public String getExamineText() {
		return examineText;
	}

	public String getName() {
		return name;
	}

	public void examine() {
		MainApplet.addInfo(examineText);
	}
}
