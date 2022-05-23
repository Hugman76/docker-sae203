package juegos.client.space;

import juegos.common.SharedConstants;

import javax.swing.*;
import java.awt.*;

public class ConnectFourClientSpace extends ClientSpace
{
	private final JLabel[][] tiles;

	public ConnectFourClientSpace() {
		super(ClientSpaceType.CONNECT_FOUR);
		this.tiles = new JLabel[SharedConstants.CONNECT_FOUR_WIDTH][SharedConstants.CONNECT_FOUR_HEIGHT];
		for(int x = 0; x < this.tiles.length; x++) {
			for(int y = 0; y < this.tiles[x].length; y++) {
				this.tiles[x][y] = new JLabel("0");
				this.tiles[x][y].setOpaque(true);
				this.tiles[x][y].setBackground(Color.WHITE);
				this.tiles[x][y].setHorizontalAlignment(SwingConstants.CENTER);
				this.tiles[x][y].setVerticalAlignment(SwingConstants.CENTER);
			}
		}
	}

	@Override
	public void handleCommand(String[] args) {
		if(args[0].equals("tiles")) {
			this.buildTiles(args[1]);
		}
	}

	public void buildTiles(String tiles) {
		String[] tileSetArray = tiles.split(SharedConstants.CONNECT_FOUR_DELIMITER);
		for(int x = 0; x < tileSetArray.length; x++) {
			String tileSetLine = tileSetArray[x];
			for(int y = 0; y < tileSetLine.length(); y++) {
				Color color = switch(tileSetLine.charAt(y)) {
					case '1' -> Color.RED;
					case '2' -> Color.YELLOW;
					default -> Color.WHITE;
				};
				this.tiles[x][y].setBackground(color);
			}
		}
	}

	@Override
	public JPanel getUI() {
		JPanel mainPanel = new JPanel();
		JPanel grillePanel = new JPanel();

		mainPanel.setLayout(new BorderLayout());
		grillePanel.setLayout(new GridLayout(SharedConstants.CONNECT_FOUR_HEIGHT + 1, SharedConstants.CONNECT_FOUR_WIDTH));

		for(int y = SharedConstants.CONNECT_FOUR_HEIGHT; y >= 0; y--) {
			for(int x = 0; x < this.tiles.length; x++) {
				if(y == SharedConstants.CONNECT_FOUR_HEIGHT) {

					JButton button = new JButton("v");
					grillePanel.add(button);

					int finalX = x;
					button.addActionListener(e -> {
						this.sendCommand("tile", "drop", String.valueOf(finalX));
					});
				}
				else {
					grillePanel.add(this.tiles[x][y]);
				}
			}
		}
		mainPanel.add(grillePanel, BorderLayout.CENTER);

		return mainPanel;
	}
}
