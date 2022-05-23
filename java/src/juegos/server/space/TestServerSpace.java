package juegos.server.space;

import juegos.server.ServerPlayer;

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
	public void handleCommand(ServerPlayer player, String[] args) {
		if(args[0].equals("text") && args[1].equals("get")) {
			this.sendCommand(player, "text", "set", this.randomString(4));
		}
	}

	public String randomString(int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder(length);
		for(int i = 0; i < length; i++) {
			sb.append((char) (random.nextInt(26) + 'a'));
		}
		return sb.toString();
	}
}
