package juegos.client.space;

import juegos.common.SharedConstants;
import juegos.common.UnoCarte;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UnoClientSpace extends ClientSpace
{
	private final JButton[] buttons;
	private final JLabel[][] cells;
    private ArrayList<UnoCarte> tabPaquet = new ArrayList<>();

	public UnoClientSpace() {
		super(ClientSpaceType.UNO);
		this.cells = new JLabel[SharedConstants.CONNECT_FOUR_WIDTH][SharedConstants.CONNECT_FOUR_HEIGHT];
		this.buttons = new JButton[SharedConstants.CONNECT_FOUR_WIDTH];
	}

	@Override
	public void handleCommand(String[] args) 
	{
		this.updateCard(args[2]);
		
	}

	public void updateCard(String cells) 
	{
		
	}

	@Override
	public JPanel getUI() {
		JPanel mainPanel = new JPanel();
		JPanel grillePanel = new JPanel();

		mainPanel.setLayout(new BorderLayout());
		grillePanel.setLayout(new GridLayout(SharedConstants.CONNECT_FOUR_HEIGHT + 1, SharedConstants.CONNECT_FOUR_WIDTH));

		// Ajout des boutons de contrôles
		for(int x = 0; x < SharedConstants.CONNECT_FOUR_WIDTH; x++) {
			Image newimg =
					new ImageIcon("data/images/connect_four/button.png").getImage()
							.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);
			JButton button = new JButton(new ImageIcon(newimg));
			button.setHorizontalTextPosition(JButton.CENTER);
			button.setVerticalTextPosition(JButton.CENTER);

			int finalX = x;
			button.addActionListener(e -> this.sendCommand(
					SharedConstants.CONNECT_FOUR_CMD_CELL,
					SharedConstants.CONNECT_FOUR_CMD_CELL_PUT,
					String.valueOf(finalX)));
			grillePanel.add(button);
		}

		

		mainPanel.add(grillePanel, BorderLayout.CENTER);

		return mainPanel;
	}
}