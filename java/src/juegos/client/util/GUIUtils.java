package juegos.client.util;

import juegos.client.JuegosClient;

import javax.swing.*;
import java.awt.*;

public final class GUIUtils
{
	public static void adaptAspectRatio(JPanel innerPanel, JPanel container) {
		int w = container.getWidth();
		int h = container.getHeight();
		int size = Math.min(w, h);
		innerPanel.setPreferredSize(new Dimension(size, size));
		container.revalidate();
	}

	public static void showErrorPopup(String message) {
		JOptionPane.showMessageDialog(JuegosClient.getFrame(), message, "Erreur", JOptionPane.ERROR_MESSAGE);
	}

	public static void showPopup(String name, String message) {
		JOptionPane.showMessageDialog(JuegosClient.getFrame(), message, name, JOptionPane.INFORMATION_MESSAGE);
	}
}
