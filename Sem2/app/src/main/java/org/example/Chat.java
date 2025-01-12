package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class Chat extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/Chat.fxml"));
            final Scene scene = new Scene(loader.load());
            primaryStage.setTitle("Chat");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
