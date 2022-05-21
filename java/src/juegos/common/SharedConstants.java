package juegos.common;

public class SharedConstants
{
	public static final int DEFAULT_PORT = 8000;
	public static final boolean DEBUG = true;

	public static final String OK = "OK";
	public static final String NO = "NO";

	public static final String LOBBY = "L";
	public static final String TEST = "test";
	public static final String CONNECT_FOUR = "cf";
	public static final String MORPION = "M";
	public static final String UNO = "U";

	public static void info(String s) {
		System.out.println(s);
	}

	public static void error(String s) {
		System.err.println(s);
	}

	public static void debug(String s) {
		if(DEBUG) System.out.println(s);
	}
}
