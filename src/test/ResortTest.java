package test;

public class ResortTest {
	public static void main (String[] args) {
		String[] pre = new String[10];
		String[] post = new String[10];
		
		pre[0] = "0";
		pre[1] = "";
		pre[2] = "2";
		pre[3] = "3";
		pre[4] = "";
		pre[5] = "5";
		pre[6] = "6";
		pre[7] = "7";
		pre[8] = "";
		pre[9] = "9";
		
		
		short i = 0;
		for (int j=0;j<10;j++) {
			post[j] = "";
			if (!pre[j].trim().equals("")) {
				post[i] = pre[j];
				i++;
			}	
		}
		
		System.out.println("Before sorting :");
		for (short k=0;k<10;k++) {
			System.out.print(pre[k] + " ; ");
		}
		System.out.println("\nAfter sorting :");
		for (short k=0;k<10;k++) {
			System.out.print(post[k] + " ; ");
		}
	}
}
