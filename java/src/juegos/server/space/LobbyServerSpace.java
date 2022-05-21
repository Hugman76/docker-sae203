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
	public void handleCommunication(ServerPlayer player) {
		ServerSpaceType type = ServerSpaceType.getById(player.read());
		player.join(type);
	}
}
