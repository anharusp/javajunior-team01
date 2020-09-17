package com.acme.edu.server.strategy;

public class UnrecognizedStrategyException extends Exception {
    public UnrecognizedStrategyException(String request_not_recognized) {
        super(request_not_recognized);
    }
}
