package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public final class LoginController {
    @FXML
    private Button enterButton;
    @FXML
    private TextField loginTextField;

    private final LoginModel model = new LoginModel();

    @FXML
    public void initialize() {
        loginTextField.textProperty().bind(model.getLogin());
        enterButton.setOnAction(e -> model.submitLogin());
    }

}