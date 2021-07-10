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

import java.net.URL;

// TODO make icon a variable so other windows can access
// TODO load icon without getClassLoader
public class ToDoListManager extends Application {

    public static URL TaskCellFXML;

    @Override
    public void start(Stage primaryStage) throws Exception {
        TaskCellFXML = getClass().getResource("/ucf/assignments/controllers/TaskCell.fxml");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ToDoListManager.fxml"));
        loader.setControllerFactory(param -> new ToDoListManagerController(primaryStage));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("To-Do List Manager");
        primaryStage.getIcons().add(new Image(this.getClass().getClassLoader().getResourceAsStream("Icon.png")));
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
