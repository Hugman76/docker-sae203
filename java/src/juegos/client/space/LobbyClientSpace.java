package juegos.client.space;

import juegos.client.JuegosClient;

import javax.swing.*;
import java.awt.*;

public class LobbyClientSpace extends ClientSpace
{
	@Override
	public void createUI(JPanel panel) {
		for(ClientSpaceType spaceType : ClientSpaceType.TYPES) {
			if(spaceType != ClientSpaceType.LOBBY) {
				JButton button = new JButton(spaceType.getName());
				button.addActionListener(e -> {
					JuegosClient.moveTo(spaceType);
				});
				panel.add(button);
			}
		}

		int size = (int) Math.floor(Math.sqrt(ClientSpaceType.TYPES.size()));
		panel.setLayout(new GridLayout(size, size));
	}
}
