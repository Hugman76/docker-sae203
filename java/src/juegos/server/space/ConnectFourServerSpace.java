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
	private final char[][] tileSet;
	private ServerPlayer player1;
	private ServerPlayer player2;
	private boolean player1Turn;

	public ConnectFourServerSpace() {
		super(ServerSpaceType.CONNECT_FOUR);
		this.tileSet = new char[SharedConstants.CONNECT_FOUR_WIDTH][SharedConstants.CONNECT_FOUR_HEIGHT];
		for(char[] charLine : this.tileSet) {
			Arrays.fill(charLine, '0');
		}
	}

	@Override
	public boolean canAccept(ServerPlayer player) {
		return this.getPlayers().size() <= 2;
	}

	@Override
	public void handleJoin(ServerPlayer player) {
		super.handleJoin(player);
		if(this.player1 == null) {
			this.player1 = player;
		}
		else if(this.player2 == null) {
			this.player2 = player;
		}
		else {
			return;
		}
		this.sendCells(player);
	}

	@Override
	public void handleLeave(ServerPlayer player) {
		super.handleLeave(player);
		if(this.player1 == player) {
			this.player1 = null;
		}
		else if(this.player2 == player) {
			this.player2 = null;
		}
	}

	@Override
	public void handleCommand(ServerPlayer player, String[] args) {
		if(args[0].equals(SharedConstants.CONNECT_FOUR_CMD_CELL)) {
			if(args[1].equals(SharedConstants.CONNECT_FOUR_CMD_CELL_PUT)) {
				dropTile(player, Integer.parseInt(args[2]));
			}
		}
	}

	/**
	 * Fait tomber une pièce dans la colonne donnée.
	 * @param player le joueur qui joue
	 * @param x la colonne
	 */
	public void dropTile(ServerPlayer player, int x) {
		for(int y = 0; y < this.tileSet[x].length; y++) {
			if(this.tileSet[x][y] == '0') {
				this.tileSet[x][y] = this.player1 == player ? '1' : '2';
				this.player1Turn = !this.player1Turn;
				break;
			}
		}
		this.checkWin();
		this.sendCellsToEveryone();
	}

	public void sendLocks(ServerPlayer player) {
		boolean[] locked = new boolean[this.tileSet.length];
		Arrays.fill(locked, true);
		if((player1Turn && player == player1) || (!player1Turn && player == player2)) {
			for(int x = 0; x < this.tileSet.length; x++) {
				for(int y = 0; y < this.tileSet[x].length; y++) {
					if(this.tileSet[x][y] == '0') {
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
		if(!lockedInts.isEmpty())   this.sendCommand(SharedConstants.CONNECT_FOUR_CMD_COLUMN, SharedConstants.CONNECT_FOUR_CMD_COLUMN_LOCK, lockedInts.toString(),   String.valueOf(true));
		if(!unlockedInts.isEmpty()) this.sendCommand(SharedConstants.CONNECT_FOUR_CMD_COLUMN, SharedConstants.CONNECT_FOUR_CMD_COLUMN_LOCK, unlockedInts.toString(), String.valueOf(false));
	}

	public void checkWin() {
		for(char[] line : this.tileSet) {
			for(char tile : line) {
				if(tile == '0') return;
			}
		}
		for(ServerPlayer player : this.getPlayers()) {
			player.leave();
		}
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
