package juegos.server.space;

import juegos.server.ServerPlayer;

public class LobbyServerSpace extends ServerSpace
{
	public LobbyServerSpace() {
		super(ServerSpaceType.LOBBY);
	}

	@Override
	public boolean canAccept(ServerPlayer player) {
		return true;
	}

	@Override
	public void handleCommand(ServerPlayer player, String[] args) {}

	@Override
	public void handleDisconnection(ServerPlayer player) {}
}
