package juegos.client.space;

import juegos.client.JuegosClient;

import java.io.IOException;

public abstract class ClientSpace
{
	abstract public void discuss() throws IOException;

	public void moveTo(ClientSpaceType space) {
		JuegosClient.moveTo(space);
	}

	public String read() {
		return JuegosClient.read();
	}

	public void send(String msg) {
		JuegosClient.send(msg);
	}
}
