package com.acme.edu.server.command;

public class CommandParser {

    private final String commandString;

    public CommandParser(String commandString) {
        this.commandString = commandString;
    }

    public static boolean isKnownCommand(String commandString) {
        String command = commandString.split(" ", 1)[0];
        return command == "/snd" || command == "/hist" || command == "/exit";
    }

    public Command parse(String commandString) throws CommandParserException {
        Command command = new EmptyCommand();
        String[] splitString = this.commandString.split(" ", 1);
        if (splitString[0].charAt(0) == '/') {
            switch (splitString[0]) {
                case "/snd":
                    command = new SendCommand(splitString[1]);
                    break;
                case "/hist":
                    command = new HistoryCommand();
                    break;
                case "/exit":
                    command = new ExitCommand();
                    break;
                default:
                    throw new CommandParserException("Unknown command: " + splitString[0]);
            }
            return command;
        } else {
            throw new CommandParserException("Not a command"+ this.commandString);
        }
    }
}
