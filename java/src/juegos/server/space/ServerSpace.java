package juegos.server.space;

import juegos.server.JuegosServer;
import juegos.server.ServerPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class ServerSpace
{
	private final List<ServerPlayer> players;

	public ServerSpace() {
		this.players = new ArrayList<>();
		JuegosServer.getSpaces().add(this);
	}

	/**
	 * Retourne si le joueur peut rejoindre cet espace.
	 */
	abstract public boolean canJoin(ServerPlayer player);

	/**
	 * Retourne la liste des joueurs de cet espace.
	 */
	public List<ServerPlayer> getPlayers() {
		return players;
	}

	/**
	 * Renvoie les joueurs au lobby, puis détruit cet espace.
	 */
	public void destroy() {
		this.getPlayers().forEach(player -> player.moveTo(JuegosServer.getLobby()));
		JuegosServer.getSpaces().remove(this);
	}

	abstract public void discuss(ServerPlayer player, PrintWriter out, BufferedReader in) throws IOException;
}
