package juegos.client;

import juegos.client.space.ClientSpace;
import juegos.client.space.ClientSpaceType;
import juegos.common.Command;
import juegos.common.CommandType;
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
	public Thread readingThread;
	public JPanel mainPanel;

	public PrintWriter writer;
	public BufferedReader reader;
	public ClientSpace space;

	public JuegosClient() {
		frame = new JFrame("Juegos");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		mainPanel = createConnectionPanel();
	}

	public static void main(String[] args) {
		refreshUI();
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
		SharedConstants.info("Connexion établie !");

		INSTANCE.readingThread = new Thread(() -> {
			while(true) {
				read();
			}
		});
		INSTANCE.readingThread.start();

		JuegosClient.sendCommand(CommandType.USERNAME.create(username));
	}

	/**
	 * @return l'espace actuel du client.
	 */
	public static ClientSpace getSpace() {
		return INSTANCE.space;
	}

	/**
	 * Envoie une commande au serveur.
	 */
	public static void sendCommand(Command msg) {
		SharedConstants.debug("< " + msg);
		INSTANCE.writer.println(msg);
	}

	/**
	 * Attend et lit le prochain message du serveur.
	 */
	private static void read() {
		try {
			String s = INSTANCE.reader.readLine();
			SharedConstants.debug("> " + s);
			Command command = Command.parse(s);
			if(CommandType.SPACE.equals(command.getType())) {
				INSTANCE.space.handleCommand(command.getArgs());
			}
			else if(CommandType.MOVE.equals(command.getType())) {
				ClientSpaceType spaceType = ClientSpaceType.getById(command.getArg(0));
				if(spaceType != null) {
					INSTANCE.space = spaceType.create();
					INSTANCE.mainPanel = getSpace().getUI();
					JuegosClient.getFrame().setTitle(getSpace().getType().getName());
					JuegosClient.refreshUI();
				}
				else {
					SharedConstants.error("Type d'espace inconnu : " + command.getArg(0));
					JuegosClient.sendCommand(CommandType.ASK_MOVE.create(ClientSpaceType.LOBBY.toString()));
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
			INSTANCE.writer.close();
			INSTANCE.mainPanel = createConnectionPanel();
			refreshUI();
			INSTANCE.readingThread.stop();
			INSTANCE.readingThread = null;
		}
	}

	/**
	 * Rafraîchit l'interface graphique.
	 */
	public static void refreshUI() {
		getFrame().getContentPane().removeAll();
		getFrame().invalidate();
		getFrame().add(INSTANCE.mainPanel);
		getFrame().validate();
		getFrame().repaint();

		SharedConstants.debug("UI refreshed");
	}

	public static JPanel createConnectionPanel() {
		JPanel panel = new JPanel();

		String[] names = {"MorpionFan76", "Xx_Puissxnce4_xX", "UnoDosTres", "Elon Musk"};
		JTextField usernameField = new JTextField(names[(int) (Math.random() * names.length)], 20);
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
			} catch(Exception ex) {
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