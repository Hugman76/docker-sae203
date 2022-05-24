package juegos.client.space;

import juegos.common.SharedConstants;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ClientSpaceType
{
	public static final ArrayList<ClientSpaceType> TYPES = new ArrayList<>();

	public static final ClientSpaceType LOBBY = register("Menu principal", SharedConstants.LOBBY, LobbyClientSpace::new, false);
	public static final ClientSpaceType CONNECT_FOUR = register("Puissance 4", SharedConstants.CONNECT_FOUR, ConnectFourClientSpace::new);
	public static final ClientSpaceType TIC_TAC_TOE = register("Morpion", SharedConstants.TIC_TAC_TOE, TTTClientSpace::new, false);
	public static final ClientSpaceType UNO = register("UNO", SharedConstants.UNO, UnoClientSpace::new, false);

	private final String name;
	private final String id;
	private final Supplier<ClientSpace> spaceSupplier;
	private final boolean selectable;

	public ClientSpaceType(String name, String id, Supplier<ClientSpace> spaceSupplier, boolean selectable) {
		this.name = name;
		this.id = id;
		this.spaceSupplier = spaceSupplier;
		this.selectable = selectable;
	}

	public static ClientSpaceType register(String name, String id, Supplier<ClientSpace> spaceSupplier, boolean selectable) {
		ClientSpaceType type = new ClientSpaceType(name, id, spaceSupplier, selectable);
		TYPES.add(type);
		return type;
	}


	public static ClientSpaceType register(String name, String id, Supplier<ClientSpace> spaceSupplier) {
		return register(name, id, spaceSupplier, true);
	}

	public static ClientSpaceType getById(String id) {
		for(ClientSpaceType type : TYPES) {
			if(type.id.equals(id)) {
				return type;
			}
		}
		return null;
	}

	public boolean isSelectable() {
		return selectable;
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
