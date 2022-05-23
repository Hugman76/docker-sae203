package juegos.server.space;

import juegos.common.SharedConstants;
import juegos.server.JuegosServer;
import juegos.server.space.uno.UnoServerSpace;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ServerSpaceType
{
	public static final ArrayList<ServerSpaceType> TYPES = new ArrayList<>();

	public static final ServerSpaceType LOBBY = register(SharedConstants.LOBBY, LobbyServerSpace::new);
	public static final ServerSpaceType TEST = register(SharedConstants.TEST, TestServerSpace::new);
	public static final ServerSpaceType CONNECT_FOUR = register(SharedConstants.CONNECT_FOUR, ConnectFourServerSpace::new);
	public static final ServerSpaceType TIC_TAC_TOE = register(SharedConstants.TIC_TAC_TOE, TTTServerSpace::new);
	public static final ServerSpaceType UNO = register(SharedConstants.UNO, UnoServerSpace::new);

	private final String id;
	private final Supplier<ServerSpace> supplier;

	public ServerSpaceType(String id, Supplier<ServerSpace> supplier) {
		this.id = id;
		this.supplier = supplier;
	}

	public static ServerSpaceType register(String id, Supplier<ServerSpace> supplier) {
		ServerSpaceType type = new ServerSpaceType(id, supplier);
		TYPES.add(type);
		return type;
	}

	public static ServerSpaceType getById(String id) {
		for(ServerSpaceType type : TYPES) {
			if(type.id.equals(id)) {
				return type;
			}
		}
		return null;
	}

	public ServerSpace create() {
		ServerSpace space = this.supplier.get();
		JuegosServer.addSpace(space);
		return space;
	}

	@Override
	public String toString() {
		return id;
	}
}
