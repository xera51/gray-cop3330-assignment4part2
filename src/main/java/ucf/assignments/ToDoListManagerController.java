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

    @FXML private Label toDoListsLabel;
    @FXML private ListView<?> toDoListsListView;
    @FXML private Button addListButton;
    @FXML private Button saveAllListsButton;
    @FXML private RadioButton allRadioButton;
    @FXML private ToggleGroup filterToggleGroup;
    @FXML private RadioButton completeRadioButton;
    @FXML private RadioButton incompleteRadioButton;
    @FXML private Button addItemButton;
    @FXML private Button saveListButton;
    @FXML private ListView<?> toDoListListView;
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
    @FXML private TextField desciptionField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // load data
        // bind ToDoListsListView to model data
        // bind ToDoListListView to ToDoListsListView selection
        // add listener to update ToDoListListView when ToDoListsListView selection updates
        // add listener to listTitleField to update when ToDoListsListView selection updates
        // add listener to dueDateField to update when ToDoListListView selection updates
        // add listener to descriptionField to update when ToDoListListView selection updates
        // add listener to completeCheckBox to update when ToDoListView selection updates
    }

    @FXML
    void addItem(ActionEvent event) {
        // open add item popup
        // when closed, get item
        // if not null, add item to currently selected list
    }

    @FXML
    void addList(ActionEvent event) {
        // open add list popup
        // when closed, get list
        // if not null, add list to model
    }

    @FXML
    void deleteItem(ActionEvent event) {
        // deletes currently selected item from model
    }

    @FXML
    void deleteList(ActionEvent event) {
        // deletes currently selected list from model
    }

    @FXML
    void editDesc(ActionEvent event) {
        // focus on the description TextArea
        // highlight the text
    }

    @FXML
    void editDue(ActionEvent event) {
        // focus on the dueDate TextField
        // highlight the text
    }

    @FXML
    void editList(ActionEvent event) {
        // focus on the list name TextField
        // highlight the text
    }

    @FXML
    void saveAllLists(ActionEvent event) {
        // call model saveAllToDoLists method
    }

    @FXML
    void saveList(ActionEvent event) {
        // get the current active lists name
        // pass to model saveToDoList method
    }

    @FXML
    void toggleComplete(ActionEvent event) {
        // toggle current selected items complete bool
    }

    @FXML
    void descEdited(InputMethodEvent event) {
        // validate
        // if valid, save to current selected item description
        // if invalid, revert text and alert
    }

    @FXML
    void dueEdited(InputMethodEvent event) {
        // validate
        // if valid, save to current selected item dueDate
        // if invalid, revert text and alert
    }

    @FXML
    void listTitleEdited(InputMethodEvent event) {
        // validate
        // if valid, save to current selected list
        // if invalid, revert text and alert
    }
}
