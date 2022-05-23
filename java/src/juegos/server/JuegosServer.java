package juegos.server;

import juegos.common.SharedConstants;
import juegos.server.space.LobbyServerSpace;
import juegos.server.space.ServerSpace;
import juegos.server.space.ServerSpaceType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class JuegosServer
{
	public static final JuegosServer INSTANCE = new JuegosServer();

	private final List<ServerSpace> spaces = new ArrayList<>();
	private final List<ServerPlayer> players = new ArrayList<>();

	public static void main(String[] args) {
		INSTANCE.start(SharedConstants.DEFAULT_PORT);
	}

	public static List<ServerSpace> getSpaces() {
		return INSTANCE.spaces;
	}

	/**
	 * @return la liste des joueurs connectés au serveur.
	 */
	public static List<ServerPlayer> getPlayers() {
		return INSTANCE.players;
	}

	private void start(int port) {
		// Créer le serveur
		this.spaces.add(new LobbyServerSpace());
		try(ServerSocket serverSocket = new ServerSocket(port)) {
			SharedConstants.info("Serveur démarré sur le port " + serverSocket.getLocalPort() + " !\nEn attente de connexions...");
			// Accueillir les clients
			while(true) {
				this.startNewThread(serverSocket.accept());
			}
		} catch(IOException e) {
			throw new RuntimeException("Impossible de créer le serveur : ", e);
		}
	}

	private void startNewThread(Socket socket) {
		SharedConstants.info("Nouveau client connecté !");
		try {
			ServerPlayer player = new ServerPlayer(socket);

			player.join(ServerSpaceType.LOBBY);
			SharedConstants.info(player + " a rejoint le lobby.");
		} catch(IOException e) {
			throw new RuntimeException("Impossible de lire/écrire sur le socket : ", e);
		}
	}
}