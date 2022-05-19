package juegos.client.ui;

import juegos.client.JuegosClient;
import juegos.client.space.ClientSpaceType;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LobbyFrame extends JFrame
{
	public LobbyFrame()
	{
		// Éléments
		JButton btnTest = new JButton("Test");
		JPanel pnlMain = new JPanel();

		// Paramétrages fonctionnels
		btnTest.addActionListener(e -> {
			JuegosClient.moveTo(ClientSpaceType.TEST);
		});

		// Paramétrages visuels
		pnlMain.setLayout(new GridLayout(2, 2));
		pnlMain.add(btnTest);

		// Ajout des éléments
		this.add(pnlMain);

		// Paramétrage de la fenêtre
		this.setTitle("Lobby");
		this.setSize(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Affichage
		this.setVisible(true);
	}
}
