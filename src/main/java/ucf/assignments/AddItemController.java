/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import ucf.assignments.model.ToDo;

public class AddItemController {

    @FXML private Label header;
    @FXML private TextArea description;
    @FXML private DatePicker dueDate;
    @FXML private Button submitButton;
    @FXML private Button cancelButton;

    private ToDo todo;

    @FXML
    private void submit(ActionEvent event) {
        // get text from description
        // get date from date
        // validate
        // if not valid, alert user and abort method
        // create new todo
        // close window
    }

    @FXML
    private void cancel(ActionEvent event) {
        // close window
    }
}
