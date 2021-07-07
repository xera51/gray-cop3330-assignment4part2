/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ToDoListManagerController implements Initializable {

    ToDoListManagerModel model = new ToDoListManagerModel();

    // TODO: update wildcards in ListViews

    @FXML private Button deleteItemButton;
    @FXML private CheckBox completeCheckbox;
    @FXML private Label dueLabel;
    @FXML private TextField dueDateField;
    @FXML private Button editDueButton;
    @FXML private Label descriptionLabel;
    @FXML private Button editDescButton;
    @FXML private TextField listTitleField;
    @FXML private Button editListButton;
    @FXML private Button deleteListButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



}
