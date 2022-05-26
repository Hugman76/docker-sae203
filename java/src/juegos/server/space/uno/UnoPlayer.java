package juegos.server.space.uno;

import juegos.server.ServerPlayer;
import juegos.common.UnoCard;

import java.util.ArrayList;

public class UnoPlayer
{
	private final ServerPlayer player;
	private final ArrayList<UnoCard> deck;

	public UnoPlayer(ServerPlayer player) {
		this.player = player;
		this.deck = new ArrayList<>();
	}

	public ServerPlayer getServerPlayer() {
		return player;
	}

	public ArrayList<UnoCard> getDeck() {
		return deck;
	}
}
