package juegos.server.space;

import juegos.server.ServerPlayer;

public class ConnectFourServerSpace extends ServerSpace
{
	private final char[][] tileSet;

	public ConnectFourServerSpace() {
		super(ServerSpaceType.TIC_TAC_TOE);
		this.tileSet = new char[7][6];
	}

	@Override
	public boolean canAccept(ServerPlayer player) {
		return this.getPlayers().size() <= 2;
	}

	@Override
	public void handleCommand(ServerPlayer player, String[] args) {
		if(args[0].equals("tile") && args[1].equals("drop")) {
			int x = Integer.parseInt(args[2]);
			for(int y = 0; y < this.tileSet[x].length; y++) {
				while(this.tileSet[x][y] != ' ') {
					y++;
				}
			}
		}
	}

	public String tileSetToString() {
		StringBuilder s = new StringBuilder();
		for(int x = 0; x < this.tileSet.length; x++) {
			for(int y = 0; y < this.tileSet[x].length; y++) {
				s.append(this.tileSet[x][y]);
			}
			if(x < this.tileSet.length - 1) {
				s.append(";");
			}
		}
		return s.toString();
	}
}
