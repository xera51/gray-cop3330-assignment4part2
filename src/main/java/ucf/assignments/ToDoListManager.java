/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ucf.assignments.controllers.ToDoListManagerController;

import java.io.InputStream;

public class ToDoListManager extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ucf/assignments/fxml/ToDoListManager.fxml"));
        loader.setControllerFactory(param -> new ToDoListManagerController(primaryStage));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("To-Do List Manager");

        try (InputStream icon = this.getClass().getResourceAsStream("/ucf/assignments/images/Icon.png")) {
            assert icon != null;
            primaryStage.getIcons().add(new Image(icon));
        } catch (AssertionError ignored) {
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
