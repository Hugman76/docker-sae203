package juegos.client.ui;

import juegos.client.JuegosClient;

import javax.swing.*;
import java.awt.*;

public class TestFrame extends JFrame
{
	private final JLabel lblTest;

	public TestFrame() {
		// Éléments
		JButton btnTest = new JButton("Envoie un truc");
		lblTest = new JLabel("TEST !");
		JPanel pnlMain = new JPanel();

		// Paramétrages fonctionnels
		btnTest.addActionListener(e -> {
			JuegosClient.send("get?");
			lblTest.setText(JuegosClient.read());
		});

		// Paramétrages visuels
		pnlMain.setLayout(new GridLayout(2, 2));
		pnlMain.add(btnTest);
		pnlMain.add(lblTest);

		// Ajout des éléments
		this.add(pnlMain);

		// Paramétrage de la fenêtre
		this.setTitle("Test");
		this.setSize(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Affichage
		this.setVisible(true);
	}

	public void setText(String text) {
		lblTest.setText(text);
	}
}
