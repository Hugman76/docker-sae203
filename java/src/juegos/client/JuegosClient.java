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
		getFrame().getContentPane().removeAll();
		getFrame().invalidate();
		getFrame().add(createConnectionPanel());
		getFrame().validate();
		getFrame().repaint();
	}

	/**
	 * @return la fenêtre du client.
	 */
	public static JFrame getFrame() {
		return INSTANCE.frame;
	}

	/**
	 * Connecte le client au serveur.
	 *
	 * @return vrai si la connection a réussi.
	 */
	public static void connect(String username, String host, int port) throws Exception {
		// Créer le serveur
		SharedConstants.info("Connexion au serveur " + host + ":" + port);
		Socket socket = new Socket(host, port);
		INSTANCE.writer = new PrintWriter(socket.getOutputStream(), true);
		INSTANCE.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		write(username);
		if(SharedConstants.OK.equals(read())) {
			SharedConstants.info("Connexion établie !");
			moveTo(ClientSpaceType.LOBBY);

			new Thread(() -> {
				while(true) {
					INSTANCE.space.tick();
				}
			}).start();
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
		SharedConstants.debug("< " + msg);
		INSTANCE.writer.println(msg);
	}

	/**
	 * Attend et lit le prochain message du serveur.
	 */
	public static String read() {
		try {
			String s = INSTANCE.reader.readLine();
			SharedConstants.debug("> " + s);
			return s;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Déplace le joueur vers le type d'espace demandé.
	 * Il est important de noter que le client DEMANDE bien au serveur, et que le serveur doit lui répondre.
	 *
	 * @return si le joueur a pu se déplacer.
	 */
	public static boolean moveTo(ClientSpaceType spaceType) {
		write(spaceType.getId());
		String reply = read();
		if(SharedConstants.OK.equals(reply)) {
			INSTANCE.space = spaceType.create();
			JuegosClient.refreshUI();
			return true;
		}
		return false;
	}

	/**
	 * Rafraîchit l'interface graphique.
	 */
	public static void refreshUI() {
		JuegosClient.getFrame().setTitle(getSpace().getType().getName());

		getFrame().getContentPane().removeAll();
		getFrame().invalidate();
		getFrame().add(getSpace().getUI());
		getFrame().validate();
		getFrame().repaint();

		SharedConstants.debug("* UI refreshed for type " + getSpace().getType().getName());
	}

	public static String randomUsername() {
		String[] names = {"MorpionFan76", "Xx_Puissxnce4_xX", "UnoDosTres", "Elon Musk"};
		return names[(int) (Math.random() * names.length)];
	}

	public static JPanel createConnectionPanel() {
		JPanel panel = new JPanel();

		JTextField usernameField = new JTextField(randomUsername(), 20);
		JTextField hostField = new JTextField(SharedConstants.DEFAULT_HOST, 20);
		JTextField portField = new JTextField(String.valueOf(SharedConstants.DEFAULT_PORT), 5);
		JButton connectButton = new JButton("Se connecter");

		connectButton.addActionListener(e -> {
			String username = usernameField.getText();
			String host = hostField.getText();
			String portText = portField.getText();
			if(username.isEmpty() || host.isEmpty() || portText.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int port;
			try {
				port = Integer.parseInt(portText);
			} catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Le port est invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				JuegosClient.connect(username, host, port);
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "Connexion impossible. Veuillez réessayer ou utiliser une adresse différente.", "Échec de la connexion", JOptionPane.ERROR_MESSAGE);
			}
		});

		panel.add(usernameField);
		panel.add(hostField);
		panel.add(portField);
		panel.add(connectButton);

		return panel;
	}
}