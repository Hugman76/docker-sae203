package juegos.client.space;

import juegos.client.JuegosClient;
import juegos.common.CommandType;

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
	public void handleCommand(String[] args) {
		if(args[0].equals("text") && args[1].equals("set"))
			lblTest.setText(args[2]);
	}

	@Override
	public JPanel getUI() {
		JPanel panel = new JPanel();
		JButton btnTest = new JButton("Envoyer une commande au serveur");

		btnTest.addActionListener(e -> {
			JuegosClient.write(CommandType.SPACE.create("text", "get"));
		});

		panel.setLayout(new GridLayout(2, 2));
		panel.add(btnTest);
		panel.add(lblTest);

		return panel;
	}
}
