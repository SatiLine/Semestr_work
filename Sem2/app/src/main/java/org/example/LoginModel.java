package org.example;

import org.client.Client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

public final class LoginModel {
    @Getter
    private final StringProperty login = new SimpleStringProperty("");

    private Client client;

    public void submitLogin() {
        //client.login()
    }
}
