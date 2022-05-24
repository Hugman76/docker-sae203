package juegos.server.space;

import juegos.common.CommandType;
import juegos.server.JuegosServer;
import juegos.server.ServerPlayer;

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

	public ServerSpace(ServerSpaceType type) {
		this.type = type;
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
		return JuegosServer.getPlayers().stream().filter(player -> player.getSpace() == this).toList();
	}

	/**
	 * Retourne si le joueur peut rejoindre cet espace.
	 */
	abstract public boolean canAccept(ServerPlayer player);

	/**
	 * Méthode de raccourci pour envoyer une commande d'espace à TOUS les clients.
	 *
	 * @param args les arguments de la commande
	 */
	public void sendCommand(String... args) {
		getPlayers().forEach(p -> this.sendCommand(p, args));
	}
	
	/**
	 * Méthode de raccourci pour envoyer une commande d'espace à un client.
	 *
	 * @param player le joueur à qui envoyer la commande
	 * @param args   les arguments de la commande
	 */
	public void sendCommand(ServerPlayer player, String... args) {
		player.sendCommand(CommandType.SPACE.create(args));
	}

	/**
	 * Méthode déclenchée lorsque le client reçoit une commande du type {@link juegos.common.CommandType#SPACE} du client, tandis que le client est dans cet espace.
	 * Les arguments peuvent être n'importe quoi, tant qu'ils correspondent bien à ce que le client attend.
	 *
	 * @param player le joueur qui a envoyé la commande
	 * @param args   les arguments de la commande
	 */
	abstract public void handleCommand(ServerPlayer player, String[] args);

	/**
	 * Méthode déclenchée lorsqu'un joueur se connecte.
	 *
	 * @param player le joueur qui vient de se connecter
	 */
	public void handleJoin(ServerPlayer player) {}

	/**
	 * Méthode déclenchée lorsqu'un joueur se retire de cet espace.
	 *
	 * @param player le joueur qui vient de se déconnecter
	 */
	public void handleLeave(ServerPlayer player) {
		if(this.getPlayers().isEmpty()) {
			this.destroy(player);
		}
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
		if(JuegosServer.getSpaces().contains(this)) {
			JuegosServer.deleteSpace(this);
			for(ServerPlayer p : this.getPlayers()) {
				if(p != player) {
					p.join(fallbackType);
				}
			}
		}
	}

	@Override
	public String toString() {
		return "ServerSpace{" +
				"type=" + type + ", " +
				"players=" + getPlayers() +
				'}';
	}
}
