package juegos.server.space;

import juegos.common.SharedConstants;
import juegos.server.JuegosServer;

import java.util.ArrayList;
import java.util.function.Supplier;

public record ServerSpaceType(String id, Supplier<ServerSpace> spaceSupplier)
{
	public static final ArrayList<ServerSpaceType> TYPES = new ArrayList<>();
	
	public static final ServerSpaceType LOBBY = register(SharedConstants.LOBBY, JuegosServer::getLobby);
	public static final ServerSpaceType TEST = register(SharedConstants.TEST, TestServerSpace::new);

	public static ServerSpaceType register(String id, Supplier<ServerSpace> spaceSupplier) {
		ServerSpaceType type = new ServerSpaceType(id, spaceSupplier);
		TYPES.add(type);
		return type;
	}

	public static ServerSpaceType getById(String id) {
		for (ServerSpaceType type : TYPES) {
			if (type.id.equals(id)) {
				return type;
			}
		}
		return null;
	}
}
