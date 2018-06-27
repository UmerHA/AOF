package MainPackage;

/*	This class returns a level depending on how much xp is given
 * 	THIS CLASS NEEDS TO BE INITIALISED !!
 */


public abstract class XPLVconverter {
	private final static int[] LIMIT = new int[101]; // available : 0-100 | only 2 - 100 are used (atm only 2-80 are used)
	private final static short MAX_LV = 80;
	
	// XPLVconverter needs to be initialized !!  Use following method to do so :
	public static void init () {
		LIMIT[2] = 83;
		LIMIT[3] = 174;
		LIMIT[4] = 276;
		LIMIT[5] = 388;
		LIMIT[6] = 512;
		LIMIT[7] = 650;
		LIMIT[8] = 801;
		LIMIT[9] = 969;
		LIMIT[10] = 1154;
		LIMIT[11] = 1358;
		LIMIT[12] = 1584;
		LIMIT[13] = 1833;
		LIMIT[14] = 2107;
		LIMIT[15] = 2411;
		LIMIT[16] = 2746;
		LIMIT[17] = 3115;
		LIMIT[18] = 3523;
		LIMIT[19] = 3973;
		LIMIT[20] = 4470;
		LIMIT[21] = 5018;
		LIMIT[22] = 5624;
		LIMIT[23] = 6291;
		LIMIT[24] = 7028;
		LIMIT[25] = 7842;
		LIMIT[26] = 8740;
		LIMIT[27] = 9730;
		LIMIT[28] = 10824;
		LIMIT[29] = 12031;
		LIMIT[30] = 13363;
		LIMIT[31] = 14883;
		LIMIT[32] = 16456;
		LIMIT[33] = 18247;
		LIMIT[34] = 20224;
		LIMIT[35] = 22406;
		LIMIT[36] = 24815;
		LIMIT[37] = 27473;
		LIMIT[38] = 30408;
		LIMIT[39] = 33648;
		LIMIT[40] = 37224;
		LIMIT[41] = 41171;
		LIMIT[42] = 45529;
		LIMIT[43] = 50339;
		LIMIT[44] = 55649;
		LIMIT[45] = 61512;
		LIMIT[46] = 67983;
		LIMIT[47] = 75127;
		LIMIT[48] = 83014;
		LIMIT[49] = 91721;
		LIMIT[50] = 101333;
		LIMIT[51] = 111945;
		LIMIT[52] = 123660;
		LIMIT[53] = 136594;
		LIMIT[54] = 150872;
		LIMIT[55] = 166636;
		LIMIT[56] = 184040;
		LIMIT[57] = 203545;
		LIMIT[58] = 224466;
		LIMIT[59] = 247886;
		LIMIT[60] = 273742;
		LIMIT[61] = 302288;
		LIMIT[62] = 333804;
		LIMIT[63] = 368599;
		LIMIT[64] = 407015;
		LIMIT[65] = 449428;
		LIMIT[66] = 496254;
		LIMIT[67] = 547953;
		LIMIT[68] = 605032;
		LIMIT[69] = 668051;
		LIMIT[70] = 737627;
		LIMIT[71] = 814445;
		LIMIT[72] = 899257;
		LIMIT[73] = 992895;
		LIMIT[74] = 1096278;
		LIMIT[75] = 1210421;
		LIMIT[76] = 1336443;
		LIMIT[77] = 1475581;
		LIMIT[78] = 1629200;
		LIMIT[79] = 1789808;
		LIMIT[80] = 1986068;
	}
	
	public static int getLV (long xp) {
		for (int i=MAX_LV;i>1;i--) {
			if (xp>=LIMIT[i])
				return i;
		}
		return 1; // if xp isn't high enought for any lv 
	}
	public static int getLV (short xp) {
		return getLV ((long) xp);
	}
	public static int getLV (int xp) {
		return getLV ((long) xp);
	}
}
