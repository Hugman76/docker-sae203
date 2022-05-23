package juegos.server.space;

import juegos.common.SharedConstants;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ServerSpaceType
{
	public static final ArrayList<ServerSpaceType> TYPES = new ArrayList<>();

	public static final ServerSpaceType LOBBY = register(SharedConstants.LOBBY, LobbyServerSpace::new);
	public static final ServerSpaceType TEST = register(SharedConstants.TEST, TestServerSpace::new);
	public static final ServerSpaceType CONNECT_FOUR = register(SharedConstants.CONNECT_FOUR, ConnectFourServerSpace::new);
	public static final ServerSpaceType TIC_TAC_TOE = register(SharedConstants.TIC_TAC_TOE, TTTServerSpace::new);

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
		return this.supplier.get();
	}

	@Override
	public String toString() {
		return id;
	}
}
