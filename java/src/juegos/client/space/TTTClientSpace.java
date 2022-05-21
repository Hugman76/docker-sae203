package juegos.client.space;

import javax.swing.*;

public class TTTClientSpace extends ClientSpace
{
	private JButton[][] tabBtnCase;

	@Override
	public void createUI(JPanel panel) {
		//Création des composants
		this.tabBtnCase = new JButton[3][3];

		for (int lig = 0; lig<this.tabBtnCase.length; lig++)
			for (int col = 0; col<this.tabBtnCase.length; col++)
				this.tabBtnCase[lig][col] = new JButton(new ImageIcon());

		//Positionnement des composants
		for (int lig = 0; lig<this.tabBtnCase.length; lig++)
			for (int col = 0; col<this.tabBtnCase.length; col++)
				panel.add(this.tabBtnCase[lig][col]);
	}
}
