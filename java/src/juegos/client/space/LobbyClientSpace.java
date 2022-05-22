package juegos.client.space;

import juegos.client.JuegosClient;
import juegos.common.CommandType;

import javax.swing.*;
import java.awt.*;

public class LobbyClientSpace extends ClientSpace
{
	public LobbyClientSpace() {
		super(ClientSpaceType.LOBBY);
	}

	@Override
	public void handleCommand(String[] args) {

	}

	@Override
	public JPanel getUI() {
		JPanel panel = new JPanel();

		for(ClientSpaceType spaceType : ClientSpaceType.TYPES) {
			if(spaceType != ClientSpaceType.LOBBY) {
				JButton button = new JButton(spaceType.getName());
				button.addActionListener(e -> {
					JuegosClient.write(CommandType.ASK_MOVE.create(spaceType.toString()));
				});
				panel.add(button);
			}
		}

		int size = (int) Math.floor(Math.sqrt(ClientSpaceType.TYPES.size()));
		panel.setLayout(new GridLayout(size, size));

		return panel;
	}
}
