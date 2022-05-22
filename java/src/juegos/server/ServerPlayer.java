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
	private String name;
	private final PrintWriter out;
	private final BufferedReader in;

	public ServerPlayer(Socket socket) throws IOException {
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * Attend et lit le prochain message envoyé par le client du joueur.
	 */
	public String read() {
		try {
			String msg = in.readLine();
			SharedConstants.debug(this + " > " + msg);
			return msg;
		} catch(IOException e) {
			this.getSpace().handleDisconnection(this);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Envoie un message au client du joueur.
	 */
	public void write(String msg) {
		SharedConstants.debug(this + " < " + msg);
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

	/**
	 * Méthode privée qui fait rejoindre un joueur à un espace dont on est sûr existe déjà.
	 *
	 * @return vrai si le joueur a pu rejoindre l'espace.
	 * @see #join(ServerSpaceType)
	 */
	private boolean join(ServerSpace space) {
		SharedConstants.debug(this + " essaye de rejoindre " + space.toString() + " depuis " + getSpace());
		if(space.canAccept(this)) {
			if(this.getSpace() != null) this.getSpace().getPlayers().remove(this);
			space.getPlayers().add(this);
			this.write(SharedConstants.OK);
			return true;
		}
		else {
			this.write(SharedConstants.NO);
			return false;
		}
	}

	/**
	 * @return l'espace auquel le joueur est rattaché.
	 */
	public ServerSpace getSpace() {
		for(ServerSpace space : JuegosServer.getSpaces()) {
			if(space.getPlayers().contains(this)) return space;
		}
		return null;
	}

	/**
	 * @return vrai si le joueur est dans un espace qui est un jeu.
	 */
	public boolean isInGame() {
		return getSpace().getType() != ServerSpaceType.LOBBY;
	}

	@Override
	public String toString() {
		return this.name == null ? super.toString() : this.name;
	}
}
