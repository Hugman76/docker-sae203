package juegos.client.space;

import juegos.common.SharedConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TTTClientSpace extends ClientSpace implements ActionListener
{
	public  JButton[][] tabBtnCase;
	private final String forme;

	public TTTClientSpace() {
		super(ClientSpaceType.TIC_TAC_TOE);
		this.tabBtnCase = new JButton[3][3];
		this.forme = " ";
	}

	@Override
	public void handleCommand(String[] args) {
		if (args[0].equals("set")) {
			this.setCase(Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[3]);
		}
	}

	public void setCase(int lig, int col, String forme) {

		this.tabBtnCase[lig][col].setText(String.valueOf(forme));
	}

	@Override
	public JPanel getUI()
	{
		//Création des composants
		JPanel mainPanel = new JPanel();
		JPanel panelPlateau = new JPanel();
		JPanel panelJ1 = new JPanel();
		JPanel panelJ2 = new JPanel();

		for(int lig = 0; lig < this.tabBtnCase.length; lig++)
			for(int col = 0; col < this.tabBtnCase.length; col++)
			{
				this.tabBtnCase[lig][col] = new JButton(" ");
				this.tabBtnCase[lig][col].setBackground(Color.WHITE);
			}

		JLabel lblJ1 = new JLabel("quoi");
		JLabel lblJ2 = new JLabel("feur");

		//Positionnement des composants
		for(int lig = 0; lig < this.tabBtnCase.length; lig++)
			for(int col = 0; col < this.tabBtnCase.length; col++)
				panelPlateau.add(this.tabBtnCase[lig][col]);

		panelPlateau.setLayout(new GridLayout(3, 3));

		panelJ1.add(lblJ1);
		panelJ2.add(lblJ2);

		mainPanel.add(panelJ2);
		mainPanel.add(panelPlateau);
		mainPanel.add(panelJ1);

		mainPanel.setLayout(new GridLayout(3,1));

		//Activation des composants
		for(int lig = 0; lig < this.tabBtnCase.length; lig++)
			for(int col = 0; col < this.tabBtnCase.length; col++)
				this.tabBtnCase[lig][col].addActionListener(this);

		return mainPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		for(int lig = 0; lig < this.tabBtnCase.length; lig++)
			for(int col = 0; col < this.tabBtnCase.length; col++)
				if (e.getSource() == tabBtnCase[lig][col])
				{
					this.sendCommand("set", String.valueOf(lig), String.valueOf(col));
				}

	}


}
