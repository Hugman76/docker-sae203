package juegos.client.space;

import javax.swing.*;
import java.awt.*;

public class TTTClientSpace extends ClientSpace
{
	private JButton[][] tabBtnCase;

	public TTTClientSpace() {
		super(ClientSpaceType.TIC_TAC_TOE);
	}

	@Override
	public void handleCommand(String[] args) {

	}

	@Override
	public JPanel getUI() {
		JPanel panel = new JPanel();

		//Création des composants
		this.tabBtnCase = new JButton[3][3];

		for(int lig = 0; lig < this.tabBtnCase.length; lig++)
			for(int col = 0; col < this.tabBtnCase.length; col++)
				this.tabBtnCase[lig][col] = new JButton(new ImageIcon());

		//Positionnement des composants
		for(int lig = 0; lig < this.tabBtnCase.length; lig++)
			for(int col = 0; col < this.tabBtnCase.length; col++)
				panel.add(this.tabBtnCase[lig][col]);

		panel.setLayout(new GridLayout(3, 3));

		return panel;
	}
}
