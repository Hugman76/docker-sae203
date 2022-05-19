package juegos.server.space;

import juegos.server.ServerPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class LobbyServerSpace extends ServerSpace
{
	@Override
	public boolean canJoin(ServerPlayer player) {
		return true;
	}

	@Override
	public void discuss(ServerPlayer player, PrintWriter out, BufferedReader in) throws IOException {
		ServerSpaceType type = ServerSpaceType.getById(in.readLine());
		ServerSpace newSpace = null;
		if(type != null) {
			newSpace = type.spaceSupplier().get();
		}
		if(newSpace == null || !player.moveTo(newSpace)) {
			out.println("Impossible de vous déplacer à ce jeu.");
		}
	}
}
