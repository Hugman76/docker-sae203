package juegos.client.space;

import juegos.common.SharedConstants;
import juegos.server.space.ServerSpaceType;

import java.util.ArrayList;
import java.util.function.Supplier;

public record ClientSpaceType(String id, Supplier<ClientSpace> spaceSupplier)
{
	public static final ArrayList<ClientSpaceType> TYPES = new ArrayList<>();

	public static final ClientSpaceType LOBBY = register(SharedConstants.LOBBY, LobbyClientSpace::new);
	public static final ClientSpaceType TEST = register(SharedConstants.TEST, TestClientSpace::new);

	public static ClientSpaceType register(String id, Supplier<ClientSpace> spaceSupplier) {
		ClientSpaceType type = new ClientSpaceType(id, spaceSupplier);
		TYPES.add(type);
		return type;
	}

	public static ClientSpaceType getById(String id) {
		for (ClientSpaceType type : TYPES) {
			if (type.id.equals(id)) {
				return type;
			}
		}
		return null;
	}
}
