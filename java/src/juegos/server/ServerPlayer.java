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
	private final Thread thread;
	private final PrintWriter writer;
	private final BufferedReader reader;
	private String name;
	private ServerSpace space;

	public ServerPlayer(Socket socket) throws IOException {
		this.writer = new PrintWriter(socket.getOutputStream(), true);
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		this.thread = new Thread(() -> {
			while(true) {
				this.read();
			}
		});
		this.thread.start();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ServerSpace getSpace() {
		return space;
	}

	/**
	 * Envoie une commande au client du joueur.
	 */
	public void sendCommand(Command command) {
		SharedConstants.debug(this + " < " + command);
		writer.println(command);
	}

	public void disconnect() {
		this.space.handleLeave(this);
		JuegosServer.removePlayer(this);
		this.space = null;
		SharedConstants.info(this + " s'est déconnecté du serveur.");
		this.thread.stop();
	}

	/**
	 * Attend et lit le prochain message envoyé par le client du joueur.
	 */
	protected void read() {
		String s;
		try {
			s = this.reader.readLine();
		} catch(IOException e) {
			this.disconnect();
			return;
		}
		SharedConstants.debug("> " + s);
		Command command = Command.parse(s);
		if(CommandType.SPACE.equals(command.getType())) {
			this.space.handleCommand(this, command.getArgs());
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
			this.disconnect();
		}
	}

	/**
	 * Méthode de raccourci pour renvoyer quelqu'un au lobby.
	 */
	public void leave() {
		this.join(ServerSpaceType.LOBBY);
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
		if(space == null) {
			space = spaceType.create();
		}

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
		SharedConstants.debug(this + " essaye de rejoindre " + space + " depuis " + this.space);
		if(space.canAccept(this)) {
			ServerSpace previousSpace = this.space;
			this.space = space;
			if(previousSpace != null) previousSpace.handleLeave(this);
			this.space.handleJoin(this);
			this.sendCommand(CommandType.MOVE.create(space.getType().toString()));
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * @return vrai si le joueur est dans un espace qui est un jeu.
	 */
	public boolean isInGame() {
		return this.space.getType() != ServerSpaceType.LOBBY;
	}

	@Override
	public String toString() {
		return this.name == null ? super.toString() : this.name;
	}
}
