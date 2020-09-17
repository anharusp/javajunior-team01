package main.java. com.acme.edu.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MessageGetter {

    public MessageGetter(){

    }

    public void getMessage(DataInputStream inputStream, DataOutputStream outStream) throws IOException {
        String message = inputStream.readUTF();


        outStream.writeUTF("Received Message " + message);
        outStream.flush();
    }
}
