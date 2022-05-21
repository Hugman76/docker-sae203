package juegos.common;

/**
 * Classe regroupant les constantes utilisées à la fois sur le serveur et le client.
 * C'est très utile pour avoir des communications correctes entre les deux.
 * Si les codes sont bons, alors ces variables peuvent être modifiées à n'importe quel moment (tant qu'elles sont distinctes) et les communications ne seront pas perturbées.
 */
public class SharedConstants
{
	public static final int DEFAULT_PORT = 8000;
	/** Cette variable est utilisée pour envoyer plus d'informations utiles au développement dans les logs. */
	public static final boolean DEBUG = true;

	public static final String OK = "OK";
	public static final String NO = "NO";

	public static final String LOBBY = "lobby";
	public static final String TEST = "test";
	/** Puissance 4 */
	public static final String CONNECT_FOUR = "connect_four";
	/** Morpion */
	public static final String TIC_TAC_TOE = "tic_tac_toe";
	/** Uno */
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
