package org.example;

import java.io.IOException;

import org.client.Client;
import org.client.Message;
import org.client.User;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import lombok.Getter;

public final class ChatModel {
    @Getter
    private final ListProperty<User> users = new SimpleListProperty<>(FXCollections.observableArrayList());
    @Getter
    private final StringProperty inputMessage = new SimpleStringProperty("");
    @Getter
    private final ListProperty<Message> messages = new SimpleListProperty<>(FXCollections.observableArrayList());

    private Client client;

    public void start(final int port) {
        client = new Client(6666);
        Thread thread = new Thread(() -> {
            Message serverResponse;
            try {
                while ((serverResponse = client.getMessage()) != null) {
                    final Message response = serverResponse;
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            messages.add(response);
                        }
                    });
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }


    public void sendMessage() {
        final String message = inputMessage.getValue();
        messages.add(new Message(client.getUser(), message));
        client.sendMessage(inputMessage.getValue());
        inputMessage.set("");
    }
}
