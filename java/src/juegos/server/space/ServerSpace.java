package juegos.server.space;

import juegos.server.JuegosServer;
import juegos.server.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class ServerSpace
{
	private final ServerSpaceType type;
	private final List<ServerPlayer> players;

	public ServerSpace(ServerSpaceType type) {
		this.type = type;
		this.players = new ArrayList<>();
		JuegosServer.getSpaces().add(this);
	}

	/**
	 * Retourne si le joueur peut rejoindre cet espace.
	 */
	abstract public boolean canAccept(ServerPlayer player);


	/**
	 * Execute un programme entre le client et le serveur.
	 * ATTENTION ! Cette méthode est appelée par un thread séparé.
	 */
	abstract public void handleCommunication(ServerPlayer player);

	/**
	 * Retourne la liste des joueurs de cet espace.
	 */
	public List<ServerPlayer> getPlayers() {
		return players;
	}

	/**
	 * Renvoie les joueurs au lobby, puis détruit cet espace.
	 */
	public void destroy() {
		this.getPlayers().forEach(player -> player.join(ServerSpaceType.LOBBY));
		JuegosServer.getSpaces().remove(this);
	}

	/**
	 * Retourne le type de cet espace.
	 */
	public ServerSpaceType getType() {
		return type;
	}
}
