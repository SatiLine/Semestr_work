package org.example;

import org.client.Message;
import org.client.User;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public final class ChatController {
    @FXML
    private Button enterButton;
    @FXML
    private Button exitButton;
    @FXML
    private TextField messageField;
    @FXML
    private ListView<Message> chatArea;
    @FXML
    private ListView<User> usersList;

    private final ChatModel model = new ChatModel();

    @FXML
    public void initialize() {
        chatArea.setCellFactory(new MessageCellFactory());
        chatArea.setItems(model.getMessages().getValue());
        messageField.textProperty().bindBidirectional(model.getInputMessage());
        enterButton.setOnAction(e -> model.sendMessage());
        exitButton.setOnAction(e -> {
            Platform.exit();
        });
        model.start(6666);
    }

}


