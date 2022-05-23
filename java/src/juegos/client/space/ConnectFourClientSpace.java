package juegos.client.space;

import juegos.common.SharedConstants;

import javax.swing.*;
import java.awt.*;

public class ConnectFourClientSpace extends ClientSpace
{
	private final JButton[] buttons;
	private final JLabel[][] cells;

	public ConnectFourClientSpace() {
		super(ClientSpaceType.CONNECT_FOUR);
		this.cells = new JLabel[SharedConstants.CONNECT_FOUR_WIDTH][SharedConstants.CONNECT_FOUR_HEIGHT];
		this.buttons = new JButton[SharedConstants.CONNECT_FOUR_WIDTH];
	}

	@Override
	public void handleCommand(String[] args) {
		if(args[0].equals(SharedConstants.CONNECT_FOUR_CMD_CELL)) {
			if(args[1].equals(SharedConstants.CONNECT_FOUR_CMD_CELL_ALL)) {
				this.updateCellsFromString(args[2]);
			}
		}
	}

	public void toggleColumns(int[] columns) {
		for(int x : columns) {
			for(int y = 0; y < this.cells[x].length; y++) {
				this.cells[x][y].setBackground(Color.RED);
			}
		}
	}

	public void updateCellsFromString(String cells) {
		String[] tileSetArray = cells.split(SharedConstants.CONNECT_FOUR_CELLS_DELIMITER);
		for(int x = 0; x < tileSetArray.length; x++) {
			String tileSetLine = tileSetArray[x];
			for(int y = 0; y < tileSetLine.length(); y++) {
				this.cells[x][y].setBackground(switch(tileSetLine.charAt(y)) {
					case '1' -> Color.RED;
					case '2' -> Color.YELLOW;
					default -> Color.WHITE;
				});
			}
		}
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

		// Ajout des cellules
		for(int y = SharedConstants.CONNECT_FOUR_HEIGHT - 1; y >= 0; y--) {
			for(int x = 0; x < SharedConstants.CONNECT_FOUR_WIDTH; x++) {
				this.cells[x][y] = new JLabel(new ImageIcon("data/connect_four/cell.png"));
				this.cells[x][y].setOpaque(true);
				this.cells[x][y].setBackground(Color.WHITE);
				this.cells[x][y].setHorizontalAlignment(SwingConstants.CENTER);
				this.cells[x][y].setVerticalAlignment(SwingConstants.CENTER);
				grillePanel.add(this.cells[x][y]);
			}
		}

		mainPanel.add(grillePanel, BorderLayout.CENTER);

		return mainPanel;
	}
}
