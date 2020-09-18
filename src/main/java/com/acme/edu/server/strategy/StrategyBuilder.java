package com.acme.edu.server.strategy;

import com.acme.edu.message.ChatMessage;

public class StrategyBuilder {

    public static Strategy create(ChatMessage message) throws UnrecognizedStrategyException {
        if ("/exit".equals(message.getMessageType()))
            return new ExitStrategy(message);
        if ("/hist".equals(message.getMessageType()))
            return new HistoryStrategy(message);
        if ("/sndp".equals(message.getMessageType()))
            return new PrivateSendStrategy(message); 
        if ("/snd".equals(message.getMessageType()))
            return new SendStrategy(message);
        if ("/chid".equals(message.getMessageType()))
            return new NameStrategy(message);
        throw new UnrecognizedStrategyException("Request not recognized");
    }
}
