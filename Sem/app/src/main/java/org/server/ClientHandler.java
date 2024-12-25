package org.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

import lombok.Getter;

final class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final PrintWriter out;
    private final BufferedReader in;
    @Getter
    private User user;
    private final Gson gson = new Gson(); 

    public ClientHandler(final Socket clientSocket) {
        this.clientSocket = clientSocket;
        user = new User("admin");
        PrintWriter out;
        BufferedReader in;
        try {
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (IOException e) {
            e.printStackTrace();
            out = null;
            in = null;
            stop();
        }
        this.out = out;
        this.in = in;
    }

    @Override
    public void run() {
        // Get messages from user and send them to others.
        Message userMessage;
        try {
            while((userMessage = getMessage()) != null) {
                Server.broadcast(userMessage, this);
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        finally {
            Server.removeClient(this);
            this.stop();
        }
    }

    public void stop() {
        Server.broadcast(new Message(new User("SERVER"), user.getUsername() + " disconnected."), this);
        try {
            out.close();
            in.close();
            clientSocket.close();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public Message getMessage() throws IOException {
        final String input = in.readLine();
        return (input != null) ? gson.fromJson(input, Message.class) : null;
    }

    public void sendMessage(final String message) {
        sendMessage(new Message(user, message));
    }

    public void sendMessage(final Message message) {
        out.println(gson.toJson(message));
        out.flush();
    }
}
