/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments;

import com.google.gson.JsonSyntaxException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


// TODO open operations currently silently fail if user picks wrong filetype, instead of notifying
public class ToDoListManagerController implements Initializable {

    ToDoListManagerModel model = new ToDoListManagerModel();
    
    // TODO update to what dir user last left off in when using new or open
    private static File defaultDir = null;
    private static final FileChooser fileChooser = new FileChooser();

    // TODO: update wildcards in ListViews

    @FXML private MenuBar menuBar;
    @FXML private MenuItem newListMenuItem;

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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Set up default directory for list files
        defaultDir = new File(System.getProperty("user.dir") +
                File.separator + "lists" + File.separator);
        if(!defaultDir.exists()) defaultDir.mkdir();

        // Set up FileChooser
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON file", "*.json"));

    }

    @FXML
    void newList(ActionEvent event) {
        // if unsaved list open, warn
        // create new file pop up window
            // asks for file name and file dir
        // if not null, set as current file in model
    }

    @FXML
    void openList(ActionEvent event) {
        // if unsaved list open, warn
        fileChooser.setTitle("Open List");
        File selectedFile = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if(this.validateFile(selectedFile)) {
            try {
                model.open(selectedFile);
            } catch (JsonSyntaxException e) {
                // TODO error window
                System.out.println("Throw error window (is json, but not proper format)");
            }
            defaultDir = selectedFile.getParentFile();
        }
    }

    @FXML
    void closeList(ActionEvent event) {
        // if not saved, warn
        // if not cancelled, clear model
        model.close();
    }

    @FXML
    void save(ActionEvent event) {
        // if list not associated with file, call newList
        model.save();
    }

    @FXML
    void saveAs(ActionEvent event) {
        // TODO need to be able to choose an already existing file or be able to create new
        fileChooser.setTitle("Save As");
        File selectedFile = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if(this.validateFile(selectedFile)) {
            model.setListFile(selectedFile);
            model.save();
        }
    }

    // TODO confirm delete window (can't be undone)
    @FXML
    void deleteList(ActionEvent event) {
        model.delete();
    }

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }


    @FXML
    void addItem(ActionEvent event) {

    }

    @FXML
    void deleteItem(ActionEvent event) {

    }

    @FXML
    void deleteAllItems(ActionEvent event) {

    }

    @FXML
    void descEdited(InputMethodEvent event) {

    }

    @FXML
    void dueEdited(InputMethodEvent event) {

    }

    @FXML
    void editDesc(ActionEvent event) {

    }

    @FXML
    void editDue(ActionEvent event) {

    }



    @FXML
    void showAllItems(ActionEvent event) {

    }

    @FXML
    void showCompleteItems(ActionEvent event) {

    }

    @FXML
    void showIncompleteItems(ActionEvent event) {

    }

    @FXML
    void toggleComplete(ActionEvent event) {

    }

    private boolean validateFile(File file) {
        return file != null && file.toString().endsWith(".json");
    }
}
