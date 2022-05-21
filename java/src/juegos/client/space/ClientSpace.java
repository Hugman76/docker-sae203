package juegos.client.space;

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
	/**
	 * Méthode exécutée en boucle, tandis que le client fait partie de cet espace.
	 */
	public void tick() {}

	abstract public void createUI(JPanel panel);
}
