package com.acme.edu.clientEntity;

import com.acme.edu.client.ClientEntity;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

public class ClientEntityTest {

    private ClientEntity clientEntity = new ClientEntity();

    @Test
    public void shouldGetNameWhenSmbAsk() {
        ClientEntity mock = mock(ClientEntity.class);
        mock.getUserId();
        verify(mock).getUserId();
    }

    @Test
    public void shouldSetIDWhenUserChanged() {
        clientEntity.setUserId("newName");
        assertThat("newName".equals(clientEntity.getUserId()));
    }

    @Test
    public void shouldGetRoomId(){
        assertThat(clientEntity.getRoomId()).isEqualTo("default");
    }

    @Test
    public void shouldSetRoomId(){
        clientEntity.setRoomId("NewRoomId");
        AssertionsForClassTypes.assertThat(clientEntity.getRoomId()).isEqualTo("NewRoomId");
    }
}
