package juegos.common;

import java.util.ArrayList;
import java.util.List;

public class CommandType
{
	public static final List<CommandType> TYPES = new ArrayList<>();
	public static final CommandType INFO = new CommandType("info");
	public static final CommandType USERNAME = new CommandType("username");
	public static final CommandType ASK_MOVE = new CommandType("ask_move");
	public static final CommandType MOVE = new CommandType("move");
	public static final CommandType SPACE = new CommandType("space");

	private final String s;

	public CommandType(String s) {
		this.s = s;
		TYPES.add(this);
	}

	@Override
	public String toString() {
		return s;
	}

	public static CommandType fromString(String s) {
		for(CommandType t : TYPES) {
			if(t.s.equals(s)) {
				return t;
			}
		}
		return null;
	}

	public Command create(String... args) {
		return new Command(this, args);
	}
}
