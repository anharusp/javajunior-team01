package com.acme.edu.server.command;

public class SendCommand implements Command {
    private final String text;

    public SendCommand(String text) {
        this.text = text;
    }

    @Override
    public void execute() {

    }
}
