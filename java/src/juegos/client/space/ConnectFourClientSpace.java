package juegos.client.space;

import javax.swing.*;
import java.awt.*;

public class ConnectFourClientSpace extends ClientSpace
{
	private final JLabel[][] tiles;

	public ConnectFourClientSpace() {
		super(ClientSpaceType.TEST);
		this.tiles = new JLabel[7][6];
	}

	@Override
	public void handleCommand(String[] args) {
		if(args[0].equals("tiles")) {
			this.buildTiles(args[1]);
		}
	}

	public void buildTiles(String tiles) {
		String[] tileSetArray = tiles.split(";");
		for(int x = 0; x < tileSetArray.length; x++) {
			String tileSetLine = tileSetArray[x];
			for(int y = 0; y < tileSetLine.length(); y++) {
				buildTile(x, y, tileSetLine.charAt(y));
			}
		}
	}

	public void buildTile(int x, int y, char tile) {
		this.tiles[x][y] = new JLabel(String.valueOf(tile));
	}

	public void dropTile(int x) {
		this.sendCommand("tile", "drop", String.valueOf(x));
	}

	@Override
	public JPanel getUI() {
		JPanel mainPanel = new JPanel();
		JPanel grillePanel = new JPanel();

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(grillePanel, BorderLayout.CENTER);

		return mainPanel;
	}
}
