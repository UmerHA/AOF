package Tests;

public class SplitStringTest {
	public static void main (String[] args) {
		String str = "LOGIN~umer~adil";
		String split = "~";
		String[] splitString = str.split(split);
		for (int i=0;i<splitString.length;i++)
			System.out.println(splitString[i]);
	}
}
