package juegos.client.space;

import juegos.client.ui.LobbyFrame;

import java.io.IOException;

public class LobbyClientSpace extends ClientSpace
{
	private final LobbyFrame frame;

	public LobbyClientSpace() {
		this.frame = new LobbyFrame();
	}

	@Override
	public void discuss() throws IOException {
	}
}
