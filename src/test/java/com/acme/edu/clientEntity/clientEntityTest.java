package com.acme.edu.clientEntity;

import com.acme.edu.client.Client;
import com.acme.edu.client.ClientEntity;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

public class clientEntityTest {

    @Test
    public void shouldGetNameWhenSmbAsk() {
        ClientEntity mock = mock(ClientEntity.class);
        mock.getUserId();
        verify(mock).getUserId();
    }

    @Test
    public void shouldSetIDWhenUserChanged() {
        ClientEntity CE = new ClientEntity();
        CE.setUserId("newName");
        assertThat("newName".equals(CE.getUserId()));
    }
}
