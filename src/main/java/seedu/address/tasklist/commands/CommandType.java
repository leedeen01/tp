package seedu.address.tasklist.commands;

/**
 * Represents the different types of commands that can be executed.
 */
public enum CommandType {
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    DELETE("delete"),
    DAYPLAN("agenda"),
    HELP("help"),
    FIND("find"),
    EXIT("exit"),
    UNKNOWN("unknown");

    private final String keyword;

    CommandType(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    /**
     * Converts a user input string into a corresponding CommandType.
     *
     * @param input The user input command as a string.
     * @return The corresponding CommandType.
     */
    public static CommandType fromString(String input) {
        for (CommandType type : CommandType.values()) {
            if (type.keyword.equalsIgnoreCase(input)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
