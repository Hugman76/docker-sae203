package juegos.client;

import juegos.client.space.ClientSpace;
import juegos.client.space.ClientSpaceType;
import juegos.common.SharedConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class JuegosClient
{
	public static final JuegosClient INSTANCE = new JuegosClient();

	public PrintWriter writer;
	public BufferedReader reader;
	public ClientSpace space;

	public static void main(String[] args) {
		INSTANCE.start();
	}

	public void start() {
		// Créer le serveur
		SharedConstants.info("Connexion au serveur...");
		try(Socket toServer = new Socket("localhost", SharedConstants.DEFAULT_PORT)) {
			this.writer = new PrintWriter(toServer.getOutputStream(), true);
			this.reader = new BufferedReader(new InputStreamReader(toServer.getInputStream()));
			if(SharedConstants.OK.equals(read())) {
				SharedConstants.info("Connexion établie !");
				this.space = ClientSpaceType.LOBBY.create();

				while(true) {
					this.space.tick();
				}
			}
		} catch(IOException e) {
			SharedConstants.error("Impossible de créer le serveur : " + e);
			System.exit(1);
		}
	}

	public static ClientSpace getSpace() {
		return INSTANCE.space;
	}

	public static String read() {
		try {
			SharedConstants.debug("Lecture...");
			return INSTANCE.reader.readLine();
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void send(String msg) {
		SharedConstants.debug("Envoi de : " + msg);
		INSTANCE.writer.println(msg);
	}

	public static boolean moveTo(ClientSpaceType spaceType) {
		send(spaceType.getId());
		String reply = read();
		if(SharedConstants.OK.equals(reply)) {
			INSTANCE.space = spaceType.create();
			return true;
		}
		return false;
	}
}