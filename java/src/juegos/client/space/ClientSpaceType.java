package juegos.client.space;

import juegos.common.SharedConstants;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ClientSpaceType
{
	public static final ArrayList<ClientSpaceType> TYPES = new ArrayList<>();

	public static final ClientSpaceType LOBBY = register("Menu principal", SharedConstants.LOBBY, LobbyClientSpace::new);
	public static final ClientSpaceType CONNECT_FOUR = register("Puissance 4", SharedConstants.CONNECT_FOUR, ConnectFourClientSpace::new);
	public static final ClientSpaceType TIC_TAC_TOE = register("Morpion", SharedConstants.TIC_TAC_TOE, TTTClientSpace::new);
	public static final ClientSpaceType UNO = register("UNO", SharedConstants.UNO, UnoClientSpace::new);

	private final String name;
	private final String id;
	private final Supplier<ClientSpace> spaceSupplier;

	public ClientSpaceType(String name, String id, Supplier<ClientSpace> spaceSupplier) {
		this.name = name;
		this.id = id;
		this.spaceSupplier = spaceSupplier;
	}

	public static ClientSpaceType register(String name, String id, Supplier<ClientSpace> spaceSupplier) {
		ClientSpaceType type = new ClientSpaceType(name, id, spaceSupplier);
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

	@Override
	public String toString() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ClientSpace create() {
		return spaceSupplier.get();
	}
}
