package juegos.server;

import juegos.common.SharedConstants;
import juegos.server.space.ServerSpace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerPlayer
{
	private final PrintWriter out;
	private final BufferedReader in;

	public ServerPlayer(Socket socket) throws IOException {
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void discuss() throws IOException {
		this.getSpace().discuss(this, out, in);
	}

	public String read() throws IOException {
		return in.readLine();
	}

	public void send(String msg) {
		System.out.println("[ENVOI] " + msg);
		out.println(msg);
	}

	public boolean moveTo(ServerSpace space) {
		if(space.canJoin(this)) {
			this.getSpace().getPlayers().remove(this);
			space.getPlayers().add(this);
			this.send(SharedConstants.OK);
			return true;
		}
		else {
			this.send(SharedConstants.NO);
			return false;
		}
	}

	public ServerSpace getSpace() {
		for(ServerSpace space : JuegosServer.getSpaces()) {
			if(space.getPlayers().contains(this)) return space;
		}
		return null;
	}

	public boolean isInGame() {
		return getSpace() != JuegosServer.getLobby();
	}

	public void initialize() {
		JuegosServer.getLobby().getPlayers().add(this);
	}
}
