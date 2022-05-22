package juegos.server.space;

import juegos.server.ServerPlayer;

public class TTTServerSpace extends ServerSpace
{
	public TTTServerSpace() {
		super(ServerSpaceType.TIC_TAC_TOE);
	}

	@Override
	public boolean canAccept(ServerPlayer player) {
		return true;
	}

	@Override
	public void handleCommand(ServerPlayer player, String[] args) {
	}
}
