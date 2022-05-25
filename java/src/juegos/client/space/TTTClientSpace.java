package juegos.client.space;

import juegos.client.util.GUIUtils;
import juegos.common.SharedConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TTTClientSpace extends ClientSpace implements ActionListener {
	public JButton[][] tabBtnCase;

	public TTTClientSpace() {
		super(ClientSpaceType.TIC_TAC_TOE);
		this.tabBtnCase = new JButton[3][3];
	}

	@Override
	public void handleCommand(String[] args) {
		if (args[0].equals(SharedConstants.TIC_TAC_TOE_CMD_CELL)) {
			if (args[1].equals(SharedConstants.TIC_TAC_TOE_CMD_CELL_ALL)) {
				this.updateCellsFromString(args[2]);
			}
		}
		if (args[0].equals(SharedConstants.WIN)) {
			GUIUtils.showPopup("Victoire !", "Vous avez gagné !");
		}
		if (args[0].equals(SharedConstants.LOSE)) {
			GUIUtils.showPopup("Défaite...", "Vous avez perdu...\nLe vainqueur est " + args[1] + ".");
		}

	}

	public void updateCellsFromString(String cells) {
		String[] cellsLines = cells.split(SharedConstants.ARGUMENT_DELIMITER);
		int x = 0;
		int y;
		for (String cellsLine : cellsLines) {
			y = 0;
			for (int charNumber = 0; charNumber < cellsLine.length(); charNumber++) {
				this.tabBtnCase[x][y].setIcon(new ImageIcon(
						"data/images/ttt/" + Character.toLowerCase(cellsLine.charAt(charNumber)) + ".png"));
				y++;
			}
			x++;
		}
	}

	@Override
	public JPanel getUI() {
		// Création des composants
		JPanel mainPanel = new JPanel();
		JPanel panelPlateau = new JPanel();
		JPanel panelJ1 = new JPanel();
		JPanel panelJ2 = new JPanel();

		panelJ1.setPreferredSize(new Dimension(600, 50));
		panelJ1.setMaximumSize(panelJ1.getPreferredSize());
		panelJ1.setMinimumSize(panelJ1.getPreferredSize());

		panelJ2.setPreferredSize(new Dimension(600, 50));
		panelJ2.setMaximumSize(panelJ1.getPreferredSize());
		panelJ2.setMinimumSize(panelJ1.getPreferredSize());

		for (int lig = 0; lig < this.tabBtnCase.length; lig++)
			for (int col = 0; col < this.tabBtnCase.length; col++) {
				this.tabBtnCase[lig][col] = new JButton(" ");
				this.tabBtnCase[lig][col].setBackground(Color.WHITE);
				this.tabBtnCase[lig][col].setPreferredSize(new Dimension(120, 120));
			}

		JLabel lblJ1 = new JLabel("quoi");
		JLabel lblJ2 = new JLabel("feur");

		// Positionnement des composants
		for (int lig = 0; lig < this.tabBtnCase.length; lig++)
			for (int col = 0; col < this.tabBtnCase.length; col++)
				panelPlateau.add(this.tabBtnCase[lig][col]);

		panelPlateau.setLayout(new GridLayout(3, 3));

		panelJ1.add(lblJ1);
		panelJ2.add(lblJ2);

		mainPanel.add(panelJ2);
		mainPanel.add(panelPlateau);
		mainPanel.add(panelJ1);

		mainPanel.setLayout(new GridLayout(3, 1));

		// Activation des composants
		for (int lig = 0; lig < this.tabBtnCase.length; lig++)
			for (int col = 0; col < this.tabBtnCase.length; col++)
				this.tabBtnCase[lig][col].addActionListener(this);

		return mainPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int lig = 0; lig < this.tabBtnCase.length; lig++)
			for (int col = 0; col < this.tabBtnCase.length; col++)
				if (e.getSource() == tabBtnCase[lig][col])
					this.sendCommand(SharedConstants.TIC_TAC_TOE_CMD_CELL,
							SharedConstants.TIC_TAC_TOE_CMD_CELL_PUT,
							String.valueOf(lig),
							String.valueOf(col));
	}
}
