package juegos.client.space;

import juegos.common.SharedConstants;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ClientSpaceType
{
	public static final ArrayList<ClientSpaceType> TYPES = new ArrayList<>();

	public static final ClientSpaceType LOBBY = register(SharedConstants.LOBBY, LobbyClientSpace::new);
	public static final ClientSpaceType TEST = register(SharedConstants.TEST, TestClientSpace::new);

	private final String id;
	private final Supplier<ClientSpace> spaceSupplier;

	public ClientSpaceType(String id, Supplier<ClientSpace> spaceSupplier) {
		this.id = id;
		this.spaceSupplier = spaceSupplier;
	}

	public static ClientSpaceType register(String id, Supplier<ClientSpace> spaceSupplier) {
		ClientSpaceType type = new ClientSpaceType(id, spaceSupplier);
		TYPES.add(type);
		return type;
	}

	public static ClientSpaceType getById(String id) {
		for(ClientSpaceType type : TYPES) {
			if(type.id.equals(id)) {
				return type;
			}
		}
		return null;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return id.replace('_', ' ').toUpperCase();
	}

	public ClientSpace create() {
		return spaceSupplier.get();
	}
}
