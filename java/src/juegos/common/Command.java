package juegos.common;


import java.util.Arrays;
import java.util.Objects;

public class Command
{
	public static final Command QUIT = CommandType.INFO.create("quit");
	public static final Command INFO_NO = CommandType.INFO.create("no");

	private final CommandType type;
	private final String[] args;

	public Command(CommandType type, String[] args) {
		this.type = type;
		this.args = args;
	}

	public CommandType getType() {
		return type;
	}

	public String[] getArgs() {
		return args;
	}

	public String getArg(int index) {
		return this.args[index];
	}

	public static Command fromString(String s) {
		String[] parts = s.split(":");
		CommandType type = CommandType.fromString(parts[0]);
		String[] args = Arrays.copyOfRange(parts, 1, parts.length);
		return new Command(type, args);
	}

	public static Command parse(Object obj) {
		Command command = null;
		if(obj instanceof Command) {
			command = (Command) obj;
		}
		else if(obj instanceof String s) {
			command = Command.fromString(s);
		}
		return Objects.requireNonNullElse(command, QUIT);
	}

	@Override
	public String toString() {
		return this.type + ":" + String.join(":", this.args);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Command other)
			return Objects.equals(this.type, other.type) && Arrays.equals(this.args, other.args);
		else
			return false;
	}
}
