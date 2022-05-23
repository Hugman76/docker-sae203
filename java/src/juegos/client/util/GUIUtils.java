package juegos.client.util;

import javax.swing.*;
import java.awt.*;

public final class GUIUtils
{
	public static void adaptAspectRatio(JPanel innerPanel, JPanel container) {
		int w = container.getWidth();
		int h = container.getHeight();
		int size =  Math.min(w, h);
		innerPanel.setPreferredSize(new Dimension(size, size));
		container.revalidate();
	}
}
