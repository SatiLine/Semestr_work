package org.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Server {
    private static ServerSocket serverSocket;
    private static CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    private Server() {}

    public static void start(final int port) throws InvalidPortException {
        if (port < 0 || port > 65535) {
            throw new InvalidPortException("Port number must be from 0 to 65535.");
        }
        try {
            serverSocket = new ServerSocket(port);
            // Accept incoming connections, creating new thread for each client.
            while (true) {
                final Socket clientSocket = serverSocket.accept();
                final ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
                broadcast(new Message(new User("SERVER"), clientHandler.getUser().getUsername() + " connected."), null);
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
            stop();
        }
    }

    public static void stop() {
        try {
            clients.clear();
            serverSocket.close();
        }
        catch (final IOException e) {
            e.printStackTrace();
            return;
        }
    }

    // Sends message to all users except the sender.
    public static void broadcast(final Message message, final ClientHandler handler) {
        clients.stream()
            .filter(clientHandler -> clientHandler != handler)
            .forEach(clientHandler -> clientHandler.sendMessage(message));
    }

    public static void removeClient(final ClientHandler handler) {
        clients.remove(handler);
    }
}
