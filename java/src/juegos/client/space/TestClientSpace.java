package juegos.client.space;

import juegos.client.JuegosClient;

import javax.swing.*;
import java.awt.*;

public class TestClientSpace extends ClientSpace
{
	private final JLabel lblTest;

	public TestClientSpace() {
		super(ClientSpaceType.TEST);
		this.lblTest = new JLabel("TEST !");
	}

	@Override
	public JPanel getUI() {
		JPanel panel = new JPanel();
		JButton btnTest = new JButton("Envoie un truc");

		btnTest.addActionListener(e -> {
			JuegosClient.write("get?");
			lblTest.setText(JuegosClient.read());
		});

		panel.setLayout(new GridLayout(2, 2));
		panel.add(btnTest);
		panel.add(lblTest);

		return panel;
	}
}
