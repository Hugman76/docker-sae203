package juegos.server.space;

import juegos.common.SharedConstants;
import juegos.server.ServerPlayer;

import java.util.Arrays;

/**
 * Espace serveur du Puissance 4.
 * @author Hugman_76
 */
public class ConnectFourServerSpace extends ServerSpace
{
	private static final int EMPTY_CELL = -1;
	private static final int WIN_CELL_LENGTH = 4;
	private final int[][] tileSet;
	private final ServerPlayer[] players;
	private int turn;

	public ConnectFourServerSpace(int maxPlayers) {
		super(ServerSpaceType.CONNECT_FOUR);
		this.tileSet = new int[SharedConstants.CONNECT_FOUR_WIDTH][SharedConstants.CONNECT_FOUR_HEIGHT];
		for(int[] charLine : this.tileSet) {
			Arrays.fill(charLine, EMPTY_CELL);
		}
		this.turn = 0;
		this.players = new ServerPlayer[maxPlayers];
	}

	public ConnectFourServerSpace() {
		this(2);
	}

	@Override
	public boolean canAccept(ServerPlayer player) {
		return this.getPlayers().size() < this.players.length;
	}

	@Override
	public void handleJoin(ServerPlayer player) {
		super.handleJoin(player);
		for(int i = 0; i < this.players.length; i++) {
			if(this.players[i] == null) {
				this.players[i] = player;
				this.sendCells(player);
				break;
			}
		}
	}

	@Override
	public void handleLeave(ServerPlayer player) {
		super.handleLeave(player);
		this.players[this.getPlayerIndex(player)] = null;
	}

	@Override
	public void handleCommand(ServerPlayer player, String[] args) {
		if(args[0].equals(SharedConstants.CONNECT_FOUR_CMD_CELL)) {
			if(args[1].equals(SharedConstants.CONNECT_FOUR_CMD_CELL_PUT)) {
				dropTile(getPlayerIndex(player), Integer.parseInt(args[2]));
			}
		}
	}

	public int getPlayerIndex(ServerPlayer player) {
		for(int i = 0; i < this.players.length; i++) {
			if(this.players[i] == player) {
				return i;
			}
		}
		return -1;
	}

	public void nextTurn() {
		this.turn = (this.turn + 1) % this.players.length;
	}

	/**
	 * Fait tomber une pièce dans la colonne donnée.
	 * @param i le numéro du joueur qui joue
	 * @param x la colonne
	 */
	public void dropTile(int i, int x) {
		for(int y = 0; y < this.tileSet[x].length; y++) {
			if(this.tileSet[x][y] == EMPTY_CELL) {
				this.tileSet[x][y] = i;
				this.nextTurn();
				break;
			}
		}
		int winner = this.checkWin();
		if(winner != -1) {
			ServerPlayer player = this.players[winner];
			this.sendCommand(player, SharedConstants.CONNECT_FOUR_CMD_WIN);
			Arrays.stream(this.players)
					.filter(player1 -> player1 != player)
					.forEach(player1 -> this.sendCommand(player1, SharedConstants.CONNECT_FOUR_CMD_LOSE, player.getName()));
		}
		this.sendCellsToEveryone();
	}

	public void sendLocks(ServerPlayer player) {
		boolean[] locked = new boolean[this.tileSet.length];
		Arrays.fill(locked, true);
		if(this.turn == this.getPlayerIndex(player)) {
			for(int x = 0; x < this.tileSet.length; x++) {
				for(int y = 0; y < this.tileSet[x].length; y++) {
					if(this.tileSet[x][y] == EMPTY_CELL) {
						locked[x] = false;
						break;
					}
				}
			}
		}

		StringBuilder lockedInts   = new StringBuilder();
		StringBuilder unlockedInts = new StringBuilder();
		for(int i = 0; i < locked.length; i++) {
			if(locked[i]) lockedInts.append(i).append(SharedConstants.ARGUMENT_DELIMITER);
			else unlockedInts.append(i).append(SharedConstants.ARGUMENT_DELIMITER);
		}
		if(!lockedInts.isEmpty())   this.sendCommand(player, SharedConstants.CONNECT_FOUR_CMD_COLUMN, SharedConstants.CONNECT_FOUR_CMD_COLUMN_LOCK, lockedInts.toString(),   String.valueOf(true));
		if(!unlockedInts.isEmpty()) this.sendCommand(player, SharedConstants.CONNECT_FOUR_CMD_COLUMN, SharedConstants.CONNECT_FOUR_CMD_COLUMN_LOCK, unlockedInts.toString(), String.valueOf(false));
	}

	public int checkHorizontalWin(int y) {
		int consecutive = 0;
		int player = EMPTY_CELL;
		for(int x = 0; x < SharedConstants.CONNECT_FOUR_WIDTH; x++) {
			int cellValue = this.tileSet[x][y];
			if(player != cellValue || cellValue == EMPTY_CELL) {
				consecutive = 0;
				player = cellValue;
			} else {
				consecutive++;
			}
			if(consecutive == WIN_CELL_LENGTH) {
				return player;
			}
		}
		return -1;
	}

	public int checkWin() {
		for(int x = 0; x < this.tileSet.length; x++) {
			for(int y = 0; y < this.tileSet[x].length; y++) {
				if(x == 0) {
					int winner = this.checkHorizontalWin(y);
					if(winner != EMPTY_CELL) return winner;
				}
			}
		}
		return EMPTY_CELL;
	}

	public void sendCellsToEveryone() {
		this.getPlayers().forEach(this::sendCells);
	}

	public void sendCells(ServerPlayer player) {
		StringBuilder s = new StringBuilder();
		for(int x = 0; x < this.tileSet.length; x++) {
			for(int y = 0; y < this.tileSet[x].length; y++) {
				s.append(this.tileSet[x][y]);
			}
			if(x < this.tileSet.length - 1) {
				s.append(SharedConstants.ARGUMENT_DELIMITER);
			}
		}
		this.sendCommand(player,
				SharedConstants.CONNECT_FOUR_CMD_CELL,
				SharedConstants.CONNECT_FOUR_CMD_CELL_ALL,
				s.toString());
		this.sendLocks(player);
	}
}
