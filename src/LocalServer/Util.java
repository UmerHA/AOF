package localServer;

class Util {
	public static void log(String string) {
		System.out.println(string);
	}

	public static String rand(int min, int max) {
		return String.valueOf((int) Math.floor(Math.random() * (max - min)) + min);
	}
	public static int randInt(int min, int max) {
		return (int) Math.floor(Math.random() * (max - min)) + min;
	}
}
