package juegos.server.space;

import juegos.common.SharedConstants;
import juegos.server.ServerPlayer;

import java.util.Arrays;

public class ConnectFourServerSpace extends ServerSpace
{
	private final char[][] tileSet;

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
	public void handleCommand(ServerPlayer player, String[] args) {
		if(args[0].equals("tile") && args[1].equals("drop")) {
			dropTile(player, Integer.parseInt(args[2]));
		}
	}

	public void dropTile(ServerPlayer player, int x) {
		for(int y = 0; y < this.tileSet[x].length; y++) {
			if(this.tileSet[x][y] == '0') {
				if(this.getPlayers().get(0) == player)
					this.tileSet[x][y] = '1';
				else
					this.tileSet[x][y] = '2';
				break;
			}
		}
		this.sendTileSetToClients();
	}

	public void sendTileSetToClients() {
		this.getPlayers().forEach(player1 -> {
			StringBuilder s = new StringBuilder();
			for(int x = 0; x < this.tileSet.length; x++) {
				for(int y = 0; y < this.tileSet[x].length; y++) {
					s.append(this.tileSet[x][y]);
				}
				if(x < this.tileSet.length - 1) {
					s.append(SharedConstants.CONNECT_FOUR_DELIMITER);
				}
			}
			this.sendCommand(player1, "tiles", s.toString());
		});
	}
}
