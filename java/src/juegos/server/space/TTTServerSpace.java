package juegos.server.space;

import juegos.common.SharedConstants;
import juegos.server.ServerPlayer;

import java.util.Arrays;

public class TTTServerSpace extends ServerSpace
{
	private static final char EMPTY_CELl = ' ';
	private static final char PLAYER_1_CELl = 'X';
	private static final char PLAYER_2_CELl = 'O';
	private final char[][] tabChar;
	private final ServerPlayer[] players;
	private int turn;

	public TTTServerSpace() {
		super(ServerSpaceType.TIC_TAC_TOE);

		this.tabChar = new char[3][3];
		for(char[] line : tabChar)
			Arrays.fill(line, ' ');

		this.turn = 0;
		this.players = new ServerPlayer[2];
	}

	@Override
	public boolean canAccept(ServerPlayer player) {
		return this.getPlayers().size() < this.players.length;
	}

	@Override
	public void handleCommand(ServerPlayer player, String[] args) {
		if(args[0].equals(SharedConstants.TIC_TAC_TOE_CMD_CELL)) {
			if(args[1].equals(SharedConstants.TIC_TAC_TOE_CMD_CELL_PUT)) {
				int lig = Integer.parseInt(args[2]);
				int col = Integer.parseInt(args[3]);
				this.placeCell(lig, col, getPlayerChar(getPlayerIndex(player)));
			}
		}
	}

	@Override
	public void handleJoin(ServerPlayer player) {
		super.handleJoin(player);
		for(int i = 0; i < this.players.length; i++) {
			if(this.players[i] == null) {
				this.players[i] = player;
				this.sendInfos(player);
				break;
			}
		}
	}

	@Override
	public void handleLeave(ServerPlayer player) {
		super.handleLeave(player);
		this.players[this.getPlayerIndex(player)] = null;
	}

	public int getPlayerIndex(ServerPlayer player) {
		for(int i = 0; i < this.players.length; i++)
			if(this.players[i] == player)
				return i;
		return -1;
	}

	public char getPlayerChar(int playerIndex) {
		if(playerIndex == 0)
			return PLAYER_1_CELl;

		return PLAYER_2_CELl;
	}

	public void placeCell(int lig, int col, char c) {
		if(this.tabChar[lig][col] == EMPTY_CELl) {
			this.tabChar[lig][col] = c;
			this.nextTurn();
			for(ServerPlayer player : this.players) {
				if(player != null) {
					this.sendInfos(player);
				}
			}
			this.checkWin();
		}
	}

	public void nextTurn() {
		this.turn = (this.turn + 1) % this.players.length;
	}

	public void win(int playerIndex) {
		ServerPlayer player = this.players[playerIndex];
		Arrays.stream(this.players)
				.filter(p -> p != player && p != null)
				.forEach(p -> this.sendCommand(p, SharedConstants.LOSE, player.getName()));
		this.sendCommand(player, SharedConstants.WIN);
		player.join(ServerSpaceType.LOBBY);
		this.destroy(player);
	}

	public void tie() {
		this.sendCommand(SharedConstants.LOSE);
		this.destroy(null);
	}

	public void checkWin() {
		for(int playerIndex = 0; playerIndex < this.players.length; playerIndex++) {
			this.checkVerticalWins(playerIndex);
			this.checkHorizontalWins(playerIndex);
			this.checkDiagonalWins(playerIndex);
		}
		if(this.isFull()) {
			this.tie();
		}
	}

	public void checkVerticalWins(int playerIndex) {
		for(int col = 0; col < this.tabChar[0].length; col++) {
			boolean win = true;
			for(int lig = 0; lig < 3; lig++) {
				if(tabChar[lig][col] != this.getPlayerChar(playerIndex))
					win = false;
			}
			if(win) {
				this.win(playerIndex);
			}
		}
	}

	public void checkHorizontalWins(int playerIndex) {
		for(int lig = 0; lig < this.tabChar.length; lig++) {
			boolean win = true;
			for(int col = 0; col < 3; col++) {
				if(tabChar[lig][col] != this.getPlayerChar(playerIndex))
					win = false;
			}
			if(win) {
				this.win(playerIndex);
			}
		}
	}

	public void checkDiagonalWins(int playerIndex) {
		if(tabChar[0][0] == tabChar[1][1] && tabChar[0][0] == tabChar[2][2]
				&& tabChar[0][0] == this.getPlayerChar(playerIndex))
			this.win(playerIndex);
		if(tabChar[2][0] == tabChar[1][1] && tabChar[2][0] == tabChar[0][2]
				&& tabChar[2][0] == this.getPlayerChar(playerIndex))
			this.win(playerIndex);
	}

	public boolean isFull() {
		for(int col = 0; col < this.tabChar[0].length; col++) {
			for(int lig = 0; lig < 3; lig++) {
				if(tabChar[lig][col] == ' ')
					return false;
			}
		}
		return true;
	}

	public String cellsToString() {
		StringBuilder s = new StringBuilder();
		for(int x = 0; x < this.tabChar.length; x++) {
			for(int y = 0; y < this.tabChar.length; y++) {
				s.append(this.tabChar[x][y]);
			}
			if(x < this.tabChar.length - 1) {
				s.append(SharedConstants.ARGUMENT_DELIMITER);
			}
		}
		return s.toString();
	}

	public void sendInfos(ServerPlayer player) {
		// Envoi des cellules
		this.sendCommand(player,
				SharedConstants.TIC_TAC_TOE_CMD_CELL,
				SharedConstants.TIC_TAC_TOE_CMD_CELL_ALL,
				this.cellsToString());
	}
}
