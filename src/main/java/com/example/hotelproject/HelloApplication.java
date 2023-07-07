package com.example.hotelproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
//        stage.setFullScreen(true);
//        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.show();
        Conect.getInstance();
    }

    public static void main(String[] args) {
        launch();
    }
}