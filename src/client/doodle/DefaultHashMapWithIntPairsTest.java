package client.doodle;

import client.DefaultHashMap;
import client.IntPair;

public class DefaultHashMapWithIntPairsTest {

	public static void main(String[] args) {
		
		DefaultHashMap<IntPair, String> map = new DefaultHashMap<IntPair, String>("Default String");
		
		map.put(new IntPair(1,1), "1 1");
		map.put(new IntPair(2,2), "2 2");
		map.put(new IntPair(3,3), "3 3");
		
		for (int i=1; i<=3; i++) {
			for (int j=1; j<=3; j++) {
				System.out.print(map.get(new IntPair(i,j)) + "#");
			}
			System.out.print("\n");
		}
		
		if (new IntPair(1,1).equals(new IntPair(1,1)))
			System.out.println("They're equal");
		else
			System.out.println("They're not equals :(");
	}
}
