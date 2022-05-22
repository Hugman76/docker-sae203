package juegos.server.space;

import juegos.server.JuegosServer;
import juegos.server.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente un espace sur le serveur.<br>
 * Par exemple - et par défaut - un espace est alloué au lobby, accueillant des joueurs à l'infini.
 * Un autre espace peut-être dédié à un jeu, qui peut accueillir maximum 2 joueurs, etc.<br>
 * Un espace contient son type ({@link ServerSpaceType}) et une liste de joueurs.
 * Il est nécessaire également de posséder un équivalent de cet espace du côté client, et de gérer la communication entre les deux.
 *
 * @see ServerSpaceType
 * @see juegos.client.space.ClientSpace
 */
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
	 * Retourne le type de cet espace.
	 */
	public ServerSpaceType getType() {
		return type;
	}

	/**
	 * @return la liste des joueurs de cet espace.
	 */
	public List<ServerPlayer> getPlayers() {
		return players;
	}

	/**
	 * Retourne si le joueur peut rejoindre cet espace.
	 */
	abstract public boolean canAccept(ServerPlayer player);

	abstract public void handleCommand(ServerPlayer player, String[] args);

	/**
	 * Execute un algorithme qui sera exécuté lorsqu'un joueur se déconnecte. Après l'exécution de cet algorithme, le joueur sera retiré complètement du serveur.
	 *
	 * @param player le joueur qui vient de se déconnecter
	 */
	public void handleDisconnection(ServerPlayer player) {
		this.destroy(player);
	}

	/**
	 * Déplace les joueurs au lobby, puis détruit cet espace.
	 *
	 * @param player le joueur qui a causé la destruction de cet espace. On le retire de la liste des joueurs à rediriger, car il se trouve sûrement déjà autre part.
	 */
	public void destroy(ServerPlayer player) {
		this.destroy(player, ServerSpaceType.LOBBY);
	}

	/**
	 * Déplace les joueurs à un autre espace, puis détruit cet espace.
	 *
	 * @param player       le joueur qui a causé la destruction de cet espace. On le retire de la liste des joueurs à rediriger, car il se trouve sûrement déjà autre part.
	 * @param fallbackType le type d'espace vers lequel rediriger les joueurs
	 */
	public void destroy(ServerPlayer player, ServerSpaceType fallbackType) {
		// TODO : fixer ça, y'as toujours une ConcurrentModificationException qui nous empêche de retirer les joueurs de la liste

		this.getPlayers().stream().filter(serverPlayer -> serverPlayer != player).forEach(player2 -> player2.join(fallbackType));
		JuegosServer.getSpaces().remove(this);
	}

	@Override
	public String toString() {
		return "ServerSpace{" +
				"type=" + type +
				", players=" + players +
				'}';
	}
}
