package juegos.server.space;

import juegos.server.ServerPlayer;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class TestServerSpace extends ServerSpace
{
	public TestServerSpace() {
		super(ServerSpaceType.TEST);
	}

	@Override
	public boolean canAccept(ServerPlayer player) {
		return true;
	}

	@Override
	public void handleCommunication(ServerPlayer player) {
		this.talkTo(player);
	}

	public void talkTo(ServerPlayer player) {
		String msg = player.read();
		if("get?".equals(msg)) {
			player.send("TEST:" + this.randomString(4));
			this.talkTo(player);
		}
		else {
			ServerSpaceType type = ServerSpaceType.getById(msg);
			if(type != null) {
				player.join(type);
				this.destroy();
			}
		}
	}

	public String randomString(int length) {
		byte[] array = new byte[length];
		new Random().nextBytes(array);
		return new String(array, StandardCharsets.UTF_8);
	}
}
