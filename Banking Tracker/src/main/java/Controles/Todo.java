package Controles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Todo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
/*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTodo.fxml"));
     //   FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/AjouterList.fxml"));
        try {
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Parent root = loader2.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




 */ Image image = new Image("images/black.png");
 primaryStage.getIcons().add(image);
        primaryStage.setTitle("PennyWise");


        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/views/MainTodo.fxml"));

        try {
            Parent root = loader3.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }




}
