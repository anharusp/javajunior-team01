package com.acme.edu.client;

import java.util.Random;

public class ClientEntity {
    private String userId = createRandomUserId();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String newId) {
        this.userId = newId;
    }

    private String createRandomUserId() {
        Random rand = new Random();
        return "id" + rand.nextInt(10000000);
    }
}
