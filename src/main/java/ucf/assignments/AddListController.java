/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddListController {


    @FXML private TextField title;

    @FXML private Button confirmButton;

    @FXML private Button cancelButton;

    private ToDoList list;


    @FXML
    void confirm(ActionEvent event) {
        // get title
        // validate
        // if not valid, alert user and abort method
        // create new list
        // close window
    }

    @FXML
    void cancel(ActionEvent event) {
        // close window
    }
}
