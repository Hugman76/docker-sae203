package juegos.server;

import juegos.common.Command;
import juegos.common.CommandType;
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
	private final PrintWriter writer;
	private final BufferedReader reader;

	public ServerPlayer(Socket socket) throws IOException {
		writer = new PrintWriter(socket.getOutputStream(), true);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * Attend et lit la prochaine commande envoyée par le client du joueur.
	 */
	public void read() {
		String s = null;
		try {
			s = this.reader.readLine();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		SharedConstants.debug("> " + s);
		Command command = Command.parse(s);
		if(CommandType.SPACE.equals(command.getType())) {
			this.getSpace().handleCommand(this, command.getArgs());
		}
		else if(CommandType.ASK_MOVE.equals(command.getType())) {
			String typeId = command.getArg(0);
			ServerSpaceType type = ServerSpaceType.getById(command.getArg(0));
			if(type == null) {
				SharedConstants.error("Le type d'espace demandé (" + typeId + ") n'existe pas sur le serveur.");
			}
			else {
				this.join(type);
			}
		}
		else if(CommandType.USERNAME.equals(command.getType())) {
			this.setName(command.getArg(0));
		}
		else if(Command.QUIT.equals(command)) {
			this.getSpace().handleDisconnection(this);
		}
	}

	/**
	 * Envoie une commande au client du joueur.
	 */
	public void write(Command command) {
		SharedConstants.debug(this + " < " + command);
		writer.println(command);
	}

	/**
	 * Place un joueur dans le premier espace du type demandé qui est disponible, et qu'il peut rejoindre.
	 * Si aucun espace est disponible, alors un nouvel espace est créé.
	 *
	 * @param spaceType le type d'espace dans lequel le joueur doit être placé
	 */
	public void join(ServerSpaceType spaceType) {
		if(spaceType == null) {
			throw new IllegalArgumentException("spaceType ne peut pas être null");
		}
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
			this.write(CommandType.MOVE.create(space.getType().toString()));
			return true;
		}
		else {
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
