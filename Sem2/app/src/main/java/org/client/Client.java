package org.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

import lombok.Getter;

public final class Client implements Runnable {
    private Socket clientSocket;
    private int port;
    private PrintWriter out;
    private BufferedReader in;
    private Gson gson = new Gson();
    @Getter
    private User user; 

    public Client(final int port) {
        if (port < 0 || port > 65535) {
            throw new InvalidPortException("Port number must be from 0 to 65535.");
        }
        this.port = port;
        try {
            clientSocket = new Socket("127.0.0.1", port);
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));
            user = new User("admin");
        }
        catch (final Exception e) {
            e.printStackTrace();
            stop();
        }
    }

    public void stop() {
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(final String message) {
        out.println(gson.toJson(new Message(user, message)));
        out.flush();
    }

    public Message getMessage() throws IOException {
        final String input = in.readLine();
        return (input != null) ? gson.fromJson(input, Message.class) : null;
    }

    @Override
    public void run() {

    }
}
