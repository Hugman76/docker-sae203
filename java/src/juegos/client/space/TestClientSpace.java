package juegos.client.space;

import juegos.client.ui.TestFrame;
import juegos.common.SharedConstants;

import java.io.IOException;

public class TestClientSpace extends ClientSpace
{
	private final TestFrame frame;

	public TestClientSpace() {
		this.frame = new TestFrame();
	}

	@Override
	public void discuss() {
	}
}
