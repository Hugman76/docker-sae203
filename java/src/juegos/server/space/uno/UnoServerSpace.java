package juegos.server.space.uno;

import juegos.common.SharedConstants;
import juegos.server.ServerPlayer;
import juegos.server.space.ServerSpace;
import juegos.server.space.ServerSpaceType;
import juegos.common.UnoCard;

import java.util.ArrayList;
import java.util.Arrays;

public class UnoServerSpace extends ServerSpace
{
	private final ArrayList<UnoCard> drawCards; // la pioche
	private UnoCard topCard; // la carte du sommet de la pile
	private final UnoPlayer[] unoPlayers; // les joueurs

	private int turn; // le tour actuel

	public UnoServerSpace() {
		this(2);
	}

	public UnoServerSpace(int nbJoueur) {
		super(ServerSpaceType.UNO);

		this.unoPlayers = new UnoPlayer[nbJoueur];
		this.drawCards = UnoCard.createInitialDrawCards();
		this.topCard = this.draw();
		// la première carte ne doit pas être spéciale
		while(this.topCard.isSpecial()) {
			this.putCardInDraw(this.topCard);
			this.topCard = this.draw();
		}
		this.turn = -1;
	}


	@Override
	public boolean canAccept(ServerPlayer player) {
		return this.getPlayers().size() < this.unoPlayers.length;
	}

	@Override
	public void handleCommand(ServerPlayer player, String[] args) {
		if(args[0].equals(SharedConstants.UNO_CMD_CARD)) {
			if(args[1].equals(SharedConstants.UNO_CMD_CARD_PLAY)) {
				UnoCard card = UnoCard.fromString(args[2]);
				this.play(this.getPlayerIndex(player), card);
			}

			if(args[1].equals(SharedConstants.UNO_CMD_CARD_DRAW)) {
				this.draw(this.getUnoPlayer(player));
			}
		}
	}

	@Override
	public void handleJoin(ServerPlayer player) {
		super.handleJoin(player);

		for(int i = 0; i < this.unoPlayers.length; i++) {
			if(this.unoPlayers[i] == null) { // si la place est libre
				this.unoPlayers[i] = new UnoPlayer(player);
				for(int j = 0; j < 7; j++) {
					this.draw(this.unoPlayers[i]);
				}
				break;
			}
		}

		boolean allReady = true;
		for(UnoPlayer unoPlayer : this.unoPlayers) {
			if(unoPlayer == null) {
				allReady = false;
				break;
			}
		}
		if(allReady) {
			this.turn = 0;
			for(UnoPlayer p : this.unoPlayers) {
				this.sendCards(p);
			}
		}
	}

	@Override
	public void handleLeave(ServerPlayer player) {
		super.handleLeave(player);
		this.unoPlayers[this.getPlayerIndex(player)] = null;
	}

	public UnoPlayer getUnoPlayer(ServerPlayer player) {
		return this.unoPlayers[this.getPlayerIndex(player)];
	}


	public int getPlayerIndex(ServerPlayer player) {
		for(int i = 0; i < this.unoPlayers.length; i++) {
			if(this.unoPlayers[i] != null && this.unoPlayers[i].getServerPlayer() == player) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Pioche une carte au hasard
	 */
	public void draw(UnoPlayer player) {
		player.getDeck().add(draw());
	}

	/**
	 * Pioche une carte au hasard
	 */
	public UnoCard draw() {
		int index = (int) (Math.random() * this.drawCards.size());
		UnoCard card = this.drawCards.get(index);
		this.drawCards.remove(index);
		return card;
	}

	/**
	 * Remets une carte dans la pioche
	 */
	public void putCardInDraw(UnoCard card) {
		this.drawCards.add(card);
	}

	public void checkWin() {
		for(UnoPlayer player : unoPlayers) {
			if(player.getDeck().size() == 0) {
				this.win(player);
			}
		}
	}

	public void win(UnoPlayer unoPlayer) {
		ServerPlayer player = unoPlayer.getServerPlayer();
		Arrays.stream(this.unoPlayers)
				.filter(p -> p != null && p.getServerPlayer() != player)
				.forEach(p -> this.sendCommand(p.getServerPlayer(), SharedConstants.LOSE, player.getName()));
		this.sendCommand(player, SharedConstants.WIN);
		player.join(ServerSpaceType.LOBBY);
		this.destroy(player);
	}

	/**
	 * Joue une carte
	 * @param playerIndex le numéro du joueur qui joue
	 */
	public void play(int playerIndex, UnoCard card) {
		if(turn == playerIndex) {
			this.topCard = card;
			this.nextTurn();
			for(UnoPlayer player : this.unoPlayers) {
				this.sendCards(player);
			}
			this.checkWin();
		}
	}

	public void nextTurn() {
		this.turn = (this.turn + 1) % this.unoPlayers.length;
	}

	public void sendCards(UnoPlayer player) {
		this.sendCommand(player.getServerPlayer(),
				SharedConstants.UNO_CMD_CARD,
				SharedConstants.UNO_CMD_CARD_ALL,
				this.topCard.toString(),
				UnoCard.listToString(player.getDeck()),
				String.valueOf(this.getPlayerIndex(player.getServerPlayer()) == this.turn));
	}
}