package client;

import java.util.Arrays;
import java.util.List;

/*	This class returns a level depending on how much xp is given */

public abstract class XPLVconverter {
	private final static List<Integer> XP_LIMIT = Arrays.asList(0, 0, 83, 174, 276, 388, 512, 650, 801, 969, 
			1154, 1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523, 3973, 4470, 5018, 5624, 6291, 7028, 7842,
			8740, 9730, 10824, 12031, 13363, 14883, 16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648,
			37224, 41171, 45529, 50339, 55649, 61512, 67983, 75127, 83014, 91721, 101333, 111945, 123660, 
			136594, 150872, 166636, 184040, 203545, 224466, 247886, 273742, 302288, 333804, 368599, 407015, 
			449428, 496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895, 1096278, 1210421, 1336443,
			1475581, 1629200, 1789808, 1986068);

	public static int getLV(long xp) {		
		for (int i = XP_LIMIT.size() - 1; i > 1; i--) {
			if (xp >= XP_LIMIT.get(i))
				return i - 2; //Bsp.: Ab 83 XP gibt's Level 2 
		}
		return 1; // if xp isn't high enought for any lv
	}

	public static int getLV(short xp) {
		return getLV((long) xp);
	}

	public static int getLV(int xp) {
		return getLV((long) xp);
	}
}
