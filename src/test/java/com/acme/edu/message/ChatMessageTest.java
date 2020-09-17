package com.acme.edu.message;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatMessageTest {
    @Test
    public void shouldCorrectlyParseSendMessageWhenCorrectMessage () {
        String message = "/snd Hello";

        ChatMessage chatMessage = new ChatMessage(message, "1", System.currentTimeMillis());

        assertThat(chatMessage.getMessageType()).isEqualTo("/snd");
        assertThat(chatMessage.getMessageText()).isEqualTo("Hello");
    }

    @Test
    public void shouldCorrectlyParseChidMessageWhenCorrectMessage () {
        String message = "/chid Name";

        ChatMessage chatMessage = new ChatMessage(message, "1", System.currentTimeMillis());

        assertThat(chatMessage.getMessageType()).isEqualTo("/chid");
        assertThat(chatMessage.getChid()).isEqualTo("Name");
    }

    @Test
    public void shouldBeUnavailableWhenInorrectMessage () {
        String message1 = "/chid Name";
        String message2 = "/snd Name";
        String message3 = "/exit Name";
        String message4 = "habberdashery";


        ChatMessage chatMessage1 = new ChatMessage(message1, "1", System.currentTimeMillis());
        ChatMessage chatMessage2 = new ChatMessage(message2, "1", System.currentTimeMillis());
        ChatMessage chatMessage3 = new ChatMessage(message3, "1", System.currentTimeMillis());
        ChatMessage chatMessage4 = new ChatMessage(message4, "1", System.currentTimeMillis());


        assertThat(chatMessage1.isCommandAvailiable()).isTrue();
        assertThat(chatMessage2.isCommandAvailiable()).isTrue();
        assertThat(chatMessage3.isCommandAvailiable()).isTrue();
        assertThat(chatMessage4.isCommandAvailiable()).isFalse();
    }
}
