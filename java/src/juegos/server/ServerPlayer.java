package juegos.server;

import juegos.common.SharedConstants;
import juegos.server.space.ServerSpace;
import juegos.server.space.ServerSpaceType;

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

	public String read() {
		try {
			String msg = in.readLine();
			SharedConstants.debug("Lecture de " + this + " : " + msg);
			return msg;
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void send(String msg) {
		SharedConstants.debug("Envoi à " + this + " : " + msg);
		out.println(msg);
	}

	/**
	 * Place un joueur dans le premier espace du type demandé qui est disponible, et qu'il peut rejoindre.
	 * Si aucun espace est disponible, alors un nouvel espace est créé.
	 *
	 * @param spaceType le type d'espace dans lequel le joueur doit être placé
	 */
	public void join(ServerSpaceType spaceType) {
		if(spaceType == null) throw new IllegalArgumentException("spaceType ne peut pas être null");
		ServerSpace space = null;
		for(ServerSpace s : JuegosServer.getSpaces()) {
			if(s.getType().equals(spaceType) && s.canAccept(this)) space = s;
		}
		if(space == null) space = spaceType.create();
		if(!this.join(space)) {
			throw new RuntimeException("Impossible de rejoindre cet espace. Cela ne devrait pas arriver. Bizarre... Veuillez vérifier que les espaces de type " + spaceType + " peuvent accepter de nouveaux joueurs juste après avoir été créés.");
		}
	}

	private boolean join(ServerSpace space) {
		SharedConstants.debug(this + " essaye de rejoindre " + space.toString() + " depuis " + getSpace());
		if(space.canAccept(this)) {
			if(this.getSpace() != null) this.getSpace().getPlayers().remove(this);
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
}
