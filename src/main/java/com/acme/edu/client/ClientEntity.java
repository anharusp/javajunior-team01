package com.acme.edu.client;

import java.util.Random;

public class ClientEntity {
    private String userId = createRandomUserId();

    /**
     * Method for getting user id
     * @return {@code String} userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Method for setting user id
     * @param newId {@code String} the desired user id
     */
    public void setUserId(String newId) {
        this.userId = newId;
    }

    private String createRandomUserId() {
        Random rand = new Random();
        return "id" + rand.nextInt(10000000);
    }
}
