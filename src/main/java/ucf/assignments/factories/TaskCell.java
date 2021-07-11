/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments.factories;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import ucf.assignments.controllers.TaskCellController;
import ucf.assignments.model.ToDo;

import java.io.IOException;
import java.net.URL;

public class TaskCell extends ListCell<ToDo> {

    private TaskCellController controller;

    public TaskCell() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ucf/assignments/fxml/TaskCell.fxml"));
            loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(ToDo todo, boolean empty) {
        super.updateItem(todo, empty);
        if(empty || todo == null) {
            setGraphic(null);
        } else {
            controller.setToDo(todo);
            Node root = controller.getView();
            setGraphic(root);
        }
    }
}
