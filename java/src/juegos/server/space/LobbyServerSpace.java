package juegos.server.space;

import juegos.common.SharedConstants;
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
		String typeId = player.read();
		ServerSpaceType type = ServerSpaceType.getById(typeId);
		if(type == null) {
			player.write(SharedConstants.NO);
			SharedConstants.error("Le type d'espace demandé (" + typeId + ") n'existe pas sur le serveur.");
		}
		else {
			player.join(type);
		}
	}
}
