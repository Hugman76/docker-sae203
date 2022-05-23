package juegos.client.space;

import juegos.client.JuegosClient;
import juegos.common.CommandType;

import javax.swing.*;

/**
 * Cette classe représente un espace sur le client.<br>
 * Par exemple - et par défaut - un espace est alloué au lobby et fait apparaître dans la fenêtre principale la liste des jeux disponibles.
 * Un autre espace peut-être dédié à un jeu, qui affichera le plateau du jeu ainsi que le nom de l'adversaire, etc.<br>
 * Contrairement à l'équivalent serveur, un espace client ne contient qu'une méthode qui est execute en boucle, mais il est possible qu'elle ne soit même pas nécessaire.
 * Il est nécessaire également de posséder un équivalent de cet espace du côté serveur, et de gérer la communication entre les deux.
 *
 * @see ClientSpaceType
 * @see juegos.server.space.ServerSpace
 */
public abstract class ClientSpace
{
	private final ClientSpaceType type;

	public ClientSpace(ClientSpaceType type) {
		this.type = type;
	}

	public ClientSpaceType getType() {
		return type;
	}

	/**
	 * Méthode de raccourci pour envoyer une commande d'espace au serveur.
	 *
	 * @param args les arguments de la commande
	 */
	public void sendCommand(String... args) {
		JuegosClient.sendCommand(CommandType.SPACE.create(args));
	}

	/**
	 * Méthode déclenchée lorsque le client reçoit une commande du type {@link juegos.common.CommandType#SPACE} du serveur, tandis que le client est dans cet espace.
	 * Les arguments peuvent être n'importe quoi, tant qu'ils correspondent bien à ce que le serveur attend.
	 *
	 * @param args arguments de la commande
	 */
	abstract public void handleCommand(String[] args);

	/**
	 * Méthode qui construit l'interface de l'espace sous forme d'un Panel.
	 */
	abstract public JPanel getUI();
}
