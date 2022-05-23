package juegos.client.space;

import juegos.client.util.GUIUtils;
import juegos.common.SharedConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Espace client du Puissance 4.
 * @author Hugman_76
 */
public class ConnectFourClientSpace extends ClientSpace
{
	private final JButton[] buttons;
	private final JLabel[][] cells;

	public ConnectFourClientSpace() {
		super(ClientSpaceType.CONNECT_FOUR);
		this.buttons = new JButton[SharedConstants.CONNECT_FOUR_WIDTH];
		this.cells = new JLabel[SharedConstants.CONNECT_FOUR_WIDTH][SharedConstants.CONNECT_FOUR_HEIGHT];
	}

	@Override
	public void handleCommand(String[] args) {
		if(args[0].equals(SharedConstants.CONNECT_FOUR_CMD_CELL)) {
			if(args[1].equals(SharedConstants.CONNECT_FOUR_CMD_CELL_ALL)) {
				this.updateCellsFromString(args[2]);
			}
		}
		if(args[0].equals(SharedConstants.CONNECT_FOUR_CMD_COLUMN)) {
			if(args[1].equals(SharedConstants.CONNECT_FOUR_CMD_COLUMN_LOCK)) {
				for(String s : args[2].split(SharedConstants.ARGUMENT_DELIMITER)) {
					this.buttons[Integer.parseInt(s)].setEnabled(!Boolean.parseBoolean(args[3]));
				}
			}
		}
	}

	public void updateCellsFromString(String cells) {
		String[] cellsLines = cells.split(SharedConstants.ARGUMENT_DELIMITER);
		int x = 0;
		int y = 0;
		for(int setNumber = 0; setNumber < cellsLines.length; setNumber++) {
			y = 0;
			for(int charNumber = 0; charNumber < cellsLines[setNumber].length(); charNumber++) {
				int i;
				if(cellsLines[setNumber].charAt(charNumber) == '-') i = -Integer.parseInt(String.valueOf(cellsLines[setNumber].charAt(++charNumber)));
				else i = Integer.parseInt(String.valueOf(cellsLines[setNumber].charAt(charNumber)));
				this.cells[x][y].setBackground(switch(i) {
					case 0 -> Color.RED;
					case 1 -> Color.YELLOW;
					default -> Color.WHITE;
				});
				y++;
			}
			x++;
		}
	}

	@Override
	public JPanel getUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel gamePanel = new JPanel(new GridLayout(SharedConstants.CONNECT_FOUR_HEIGHT + 1, SharedConstants.CONNECT_FOUR_WIDTH));
		JPanel containerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

		// Paramétrage des tailles
		containerPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				GUIUtils.adaptAspectRatio(gamePanel, containerPanel);
			}
		});

		// Ajout des boutons de contrôles
		for(int x = 0; x < SharedConstants.CONNECT_FOUR_WIDTH; x++) {
			final Image img = Toolkit.getDefaultToolkit().getImage("data/images/connect_four/button.png");
			this.buttons[x] = new JButton(new ImageIcon(img.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH)));
			this.buttons[x].setHorizontalTextPosition(JButton.CENTER);
			this.buttons[x].setVerticalTextPosition(JButton.CENTER);

			int finalX = x;
			this.buttons[x].addActionListener(e -> this.sendCommand(
					SharedConstants.CONNECT_FOUR_CMD_CELL,
					SharedConstants.CONNECT_FOUR_CMD_CELL_PUT,
					String.valueOf(finalX)));
			gamePanel.add(this.buttons[x]);
		}

		// Ajout des cellules
		for(int y = SharedConstants.CONNECT_FOUR_HEIGHT - 1; y >= 0; y--) {
			for(int x = 0; x < SharedConstants.CONNECT_FOUR_WIDTH; x++) {
				this.cells[x][y] = new JLabel(new ImageIcon("data/images/connect_four/cell_outline.png"));
				this.cells[x][y].setOpaque(true);
				this.cells[x][y].setBackground(Color.WHITE);
				this.cells[x][y].setHorizontalAlignment(SwingConstants.CENTER);
				this.cells[x][y].setVerticalAlignment(SwingConstants.CENTER);
				gamePanel.add(this.cells[x][y]);
			}
		}

		containerPanel.add(gamePanel);
		mainPanel.add(containerPanel, BorderLayout.CENTER);

		return mainPanel;
	}
}
