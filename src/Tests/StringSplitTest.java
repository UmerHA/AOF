package Tests;

public class StringSplitTest {
	static String txt = "/w niels hi whats up?";
	
	public static void main (String args[]) {
		@SuppressWarnings("unused")
		int ios = 0;//indexOfSlash
		String[] data = txt.split(" ");
		
		if (data[0].equals("/w")) {
			String name = data[1];
			
			int start = 4+data[1].length();
			String msg = txt.substring(start,txt.length());
			
			System.out.println("whisper to " + name + " : \""+msg+"\"");
		} else {
			System.out.println("no whisper");
		}
	}
}