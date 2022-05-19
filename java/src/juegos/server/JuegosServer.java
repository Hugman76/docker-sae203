package juegos.server;

import juegos.common.SharedConstants;
import juegos.server.space.LobbyServerSpace;
import juegos.server.space.ServerSpace;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class JuegosServer
{
	public static final JuegosServer INSTANCE = new JuegosServer();

	private static final boolean DEBUG = false;

	private List<ServerSpace> spaces;
	private ServerSpace lobby;

	public static void main(String[] args) {
		INSTANCE.start(SharedConstants.DEFAULT_PORT);
	}

	public static ServerSpace getLobby() {
		return INSTANCE.lobby;
	}

	public static List<ServerSpace> getSpaces() {
		return INSTANCE.spaces;
	}

	public static List<ServerPlayer> getPlayers() {
		List<ServerPlayer> players = new ArrayList<>();
		for(ServerSpace space : INSTANCE.spaces) {
			players.addAll(space.getPlayers());
		}
		return players;
	}

	public void start(int port) {
		// Créer le serveur
		this.spaces = new ArrayList<>();
		this.lobby = new LobbyServerSpace();
		try(ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Serveur démarré !\nEn attente de connexions...");
			// Accueillir les clients
			while(true) {
				this.startNewThread(serverSocket.accept());
			}
		} catch(IOException e) {
			System.err.println("Impossible de créer le serveur : " + e);
			System.exit(1);
		}
	}

	public void startNewThread(Socket socket) {
		new Thread(() -> {
			try {
				ServerPlayer player = new ServerPlayer(socket);
				JuegosServer.getLobby().getPlayers().add(player);
				System.out.println("Nouveau client connecté !");

				while(true) {
					player.discuss();
				}
			} catch(IOException e) {
				System.err.println("Impossible de lire/écrire sur le socket : " + e);
				throw new RuntimeException(e);
			}
		}).start();
	}
}