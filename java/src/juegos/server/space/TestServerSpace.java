package juegos.server.space;

import juegos.server.ServerPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class TestServerSpace extends ServerSpace
{
	@Override
	public boolean canJoin(ServerPlayer player) {
		return true;
	}

	@Override
	public void discuss(ServerPlayer player, PrintWriter out, BufferedReader in) throws IOException {
		String msg = player.read();
		if("get?".equals(msg))
			player.send("TEST:" + this.randomString(4));
		else {
			ServerSpaceType type = ServerSpaceType.getById(msg);
			if(type != null) {
				player.moveTo(type.spaceSupplier().get());
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
