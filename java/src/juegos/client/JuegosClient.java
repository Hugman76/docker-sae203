package juegos.client;

import juegos.client.space.ClientSpace;
import juegos.client.space.ClientSpaceType;
import juegos.common.SharedConstants;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class JuegosClient
{
	public static final JuegosClient INSTANCE = new JuegosClient();

	public final JFrame frame;

	public PrintWriter writer;
	public BufferedReader reader;
	public ClientSpace space;

	public JuegosClient() {
		frame = new JFrame("Juegos");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		INSTANCE.start();
	}

	/**
	 * @return la fenêtre du client.
	 */
	public static JFrame getFrame() {
		return INSTANCE.frame;
	}

	/**
	 * Démarre le client.
	 */
	public void start() {
		// Créer le serveur
		SharedConstants.info("Connexion au serveur...");
		try(Socket toServer = new Socket("localhost", SharedConstants.DEFAULT_PORT)) {
			this.writer = new PrintWriter(toServer.getOutputStream(), true);
			this.reader = new BufferedReader(new InputStreamReader(toServer.getInputStream()));
			if(SharedConstants.OK.equals(read())) {
				SharedConstants.info("Connexion établie !");
				moveTo(ClientSpaceType.LOBBY);

				while(true) {
					this.space.tick();
				}
			}
		} catch(IOException e) {
			SharedConstants.error("Impossible de créer le serveur : " + e);
			System.exit(1);
		}
	}

	/**
	 * @return l'espace actuel du client.
	 */
	public static ClientSpace getSpace() {
		return INSTANCE.space;
	}

	/**
	 * Envoie un message au serveur.
	 */
	public static void write(String msg) {
		SharedConstants.debug("Envoi de : " + msg);
		INSTANCE.writer.println(msg);
	}

	/**
	 * Attend et lit le prochain message du serveur.
	 */
	public static String read() {
		try {
			SharedConstants.debug("Lecture...");
			return INSTANCE.reader.readLine();
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Déplace le joueur vers le type d'espace demandé.
	 * Il est important de noter que le client DEMANDE bien au serveur, et que le serveur doit lui répondre.
	 * @return si le joueur a pu se déplacer.
	 */
	public static boolean moveTo(ClientSpaceType spaceType) {
		write(spaceType.getId());
		String reply = read();
		if(SharedConstants.OK.equals(reply)) {
			INSTANCE.space = spaceType.create();
			JuegosClient.getFrame().setTitle(spaceType.getName());
			getFrame().getContentPane().removeAll();
			JPanel panel = new JPanel();
			getSpace().createUI(panel);

			getFrame().add(panel);
			getFrame().validate();
			getFrame().repaint();
			return true;
		}
		return false;
	}
}