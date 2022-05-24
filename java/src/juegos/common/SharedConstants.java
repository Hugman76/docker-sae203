package juegos.common;

/**
 * Classe regroupant les constantes utilisées à la fois sur le serveur et le client.
 * C'est très utile pour avoir des communications correctes entre les deux.
 * Si les codes sont bons, alors ces variables peuvent être modifiées à n'importe quel moment (tant qu'elles sont distinctes) et les communications ne seront pas perturbées.
 */
public class SharedConstants
{
	public static final String DEFAULT_HOST = "localhost";
	public static final int DEFAULT_PORT = 8000;
	/**
	 * Cette variable est utilisée pour envoyer plus d'informations utiles au développement dans les logs.
	 */
	public static final boolean DEBUG = true;

	public static final String COMMAND_DELIMITER = ":";
	public static final String ARGUMENT_DELIMITER = "'";

	public static final String LOBBY = "lobby";

	/**
	 * <a href="https://fr.wikipedia.org/wiki/Puissance_4">Puissance 4</a>
	 */
	public static final String CONNECT_FOUR = "connect_four";
	public static final int CONNECT_FOUR_HEIGHT = 6;
	public static final int CONNECT_FOUR_WIDTH = 7;
	public static final String CONNECT_FOUR_CMD_CELL = "cell";
	public static final String CONNECT_FOUR_CMD_CELL_ALL = "all";
	public static final String CONNECT_FOUR_CMD_CELL_PUT = "put";
	public static final String CONNECT_FOUR_CMD_COLUMN = "column";
	public static final String CONNECT_FOUR_CMD_COLUMN_LOCK = "lock";
	public static final String CONNECT_FOUR_CMD_WIN = "win";
	public static final String CONNECT_FOUR_CMD_LOSE = "win";
	/**
	 * <a href="https://fr.wikipedia.org/wiki/Morpion_(jeu)">Morpion</a>
	 */
	public static final String TIC_TAC_TOE = "tic_tac_toe";
	public static final String TIC_TAC_TOE_DELIMITER = "/";
	/**
	 * <a href="https://fr.wikipedia.org/wiki/Bataille_(jeu)">Bataille</a>
	 */
	public static final String WAR = "war";
	/**
	 * <a href="https://fr.wikipedia.org/wiki/Uno">Uno</a>
	 */
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
