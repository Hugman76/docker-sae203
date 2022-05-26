package juegos.client.space;

import juegos.client.util.GUIUtils;
import juegos.common.SharedConstants;
import juegos.common.UnoCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UnoClientSpace extends ClientSpace
{
	private final JLabel lblTopCard = new JLabel();
	private final ArrayList<JButton> btnDecks = new ArrayList<>();

	private JPanel deckPanel;
	private JButton drawButton;

	public UnoClientSpace() {
		super(ClientSpaceType.UNO);
	}

	@Override
	public void handleCommand(String[] args) {
		if(args[0].equals(SharedConstants.UNO_CMD_CARD)) {
			if(args[1].equals(SharedConstants.UNO_CMD_CARD_ALL)) {
				this.setTopCard(UnoCard.fromString(args[2]));
				this.setDeck(UnoCard.listFromString(args[3]));
				this.lock(Boolean.parseBoolean(args[4]));
			}
		}
		if(args[0].equals(SharedConstants.WIN)) {
			GUIUtils.showPopup("Victoire !", "Vous avez gagné !");
		}
		if(args[0].equals(SharedConstants.LOSE)) {
			GUIUtils.showPopup("Défaite...", "Vous avez perdu...\nLe vainqueur est " + args[1] + ".");
		}
	}

	public void setTopCard(UnoCard card) {
		Image img = Toolkit.getDefaultToolkit().getImage("data/images/uno/card/" + card.toString() + ".png");
		this.lblTopCard.setIcon(new ImageIcon(img));
	}

	public void setDeck(ArrayList<UnoCard> deck) {
		this.deckPanel.removeAll();
		for(UnoCard card : deck) {
			Image img = Toolkit.getDefaultToolkit().getImage("data/images/uno/card/" + card.toString() + ".png");
			JButton cardButton = new JButton(new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
			cardButton.addActionListener(e -> this.sendCommand(SharedConstants.UNO_CMD_CARD_PLAY, card.toString()));
			this.deckPanel.add(cardButton);
			this.deckPanel.revalidate();
		}
	}

	public void lock(boolean b) {
		this.drawButton.setEnabled(b);
		for(Component component : this.deckPanel.getComponents()) {
			component.setEnabled(b);
		}
	}

	@Override
	public JPanel getUI() {
		JPanel mainPanel = new JPanel(new GridLayout(3, 1));
		//JPanel plateauPanel = new JPanel();
		deckPanel = new JPanel();
		this.drawButton = new JButton();
		//this.cardActuelle = new UnoCarte("B4");

		this.drawButton.addActionListener(e -> this.sendCommand(SharedConstants.UNO_CMD_CARD, SharedConstants.UNO_CMD_CARD_DRAW));

		mainPanel.add(this.drawButton);
		mainPanel.add(this.lblTopCard);
		mainPanel.add(deckPanel);

		return mainPanel;
	}
}