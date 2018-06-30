package mainPackage;


public class myObject {
	protected String name;
	protected String examineText;
	
	public String getExamineText () {
		return examineText;
	}
	public String getName () {
		return name;
	}
	public void examine () {
		MainApplet.addInfo(examineText);
	}
}
