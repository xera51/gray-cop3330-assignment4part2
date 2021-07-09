/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments;

import com.google.gson.JsonSyntaxException;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ucf.assignments.model.ToDo;
import ucf.assignments.model.ToDoDAO;

import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


// TODO open operations currently silently fail if user picks wrong filetype, instead of notifying
public class ToDoListManagerController implements Initializable {

    ToDoDAO dao = new ToDoDAO();

    // TODO update to what dir user last left off in when using new or open
    private static File defaultDir = null;
    private static final FileChooser fileChooser = new FileChooser();

    // TODO: update wildcards in ListViews

    @FXML private BorderPane root;
    @FXML private MenuBar menuBar;
    @FXML private MenuItem newListMenuItem;
    @FXML private ListView<ToDo> itemListView;
    @FXML private Button addButton;

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
    @FXML private TextArea descriptionField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Set up defaultDir
        defaultDir = makeListsDir();

        // Set up fileChooser
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON file", "*.json"));


        // Set up itemListView
        itemListView.setItems(FXCollections.observableArrayList(ToDo.extractor()));
        itemListView.setCellFactory(lv -> new TaskCell());

        // TODO will need to be updated because of dueDate, perhaps extract
        itemListView.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> descriptionField.setDisable(
                        itemListView.getSelectionModel().getSelectedIndex() < 0));


        // Set up descriptionField
        StringBinding descriptionBinding =
                Bindings.when(itemListView.getSelectionModel().selectedIndexProperty().lessThan(0))
                        .then("")
                        .otherwise(
                Bindings.selectString(
                        itemListView.getSelectionModel().selectedItemProperty(),
                        "description"));

        descriptionField.textProperty().bind(descriptionBinding);

        // Allows user to type in descriptionField,
        // When they leave the field, edits the current item's description
        descriptionField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (itemListView.getSelectionModel().getSelectedIndex() >= 0) {
                if (newValue) {
                    descriptionField.textProperty().unbind();
                } else {
                    if(!itemListView.isFocused())
                        itemListView.getSelectionModel().getSelectedItem().descriptionProperty().set(descriptionField.getText());
                    descriptionField.textProperty().bind(descriptionBinding);
                }
            }
        });

        descriptionField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if(!event.isShiftDown() && event.getCode() == KeyCode.ENTER) {
                event.consume();
                root.requestFocus();
            }
        });
    }

    @FXML
    void newList(ActionEvent event) {
        // if unsaved list open, warn
        // prompt for new file

        // TODO commented code
        /*
        try {
            dao.create();
            itemListView.getItems().clear();
        } catch (FileAlreadyExistsException e) {
            // Alert user file/list already exists
        } catch (IOException e) {
            // Alert user file/list could not be created
        } */
    }

    @FXML
    void openList(ActionEvent event) {
        // if unsaved list open, warn
        setFileChooserDir();
        fileChooser.setTitle("Open List");
        File selectedFile = fileChooser.showOpenDialog(menuBar.getScene().getWindow());

        if(selectedFile != null) {
            try {
                defaultDir = selectedFile.getParentFile();
                dao.open(selectedFile);
                itemListView.getItems().setAll(dao.read());
            } catch (JsonSyntaxException e) {
                // Alert the user the Json file is invalid
            }
        }
    }

    @FXML
    void closeList(ActionEvent event) {
        // if not saved, warn
        dao.setListFileToNull();
        itemListView.getItems().clear();
    }

    @FXML
    void save(ActionEvent event) {
        if(dao.getListFile() == null) {
            // prompt for new file or maybe also choose a file? save vs save as?
            //TODO commented code
            //dao.create();
        }
        if (!dao.save(itemListView.getItems())) {
            // alert user save failed
        }
    }

    @FXML
    void saveAs(ActionEvent event) {
        // TODO need to be able to choose an already existing file or be able to create new
        setFileChooserDir();
        fileChooser.setTitle("Save As");
        File selectedFile = fileChooser.showOpenDialog(menuBar.getScene().getWindow());

    }


    @FXML
    void deleteList(ActionEvent event) {
        // TODO confirm delete window (can't be undone)
        if(!dao.delete()) {
            // alert user could not delete
        } else {
            itemListView.getItems().clear();
        }
    }

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }


    @FXML
    void addItem(ActionEvent event) {
        // TODO currently test code, needs to be deleted
        String[] strings = {"random", "a string", "cool"};
        Random random = new Random();
        itemListView.getSelectionModel().getSelectedItem().setDesc(
                strings[random.nextInt(3)]
        );
    }

    @FXML
    void deleteItem(ActionEvent event) {

    }

    @FXML
    void deleteAllItems(ActionEvent event) {

    }

    @FXML
    void descEdited(InputMethodEvent event) {
        // prevent from going greater than 256 and less than 1
        // wrong function, needs to be key typed i believe
        // display little thing to notify user cant be longer than 256
        // or empty
    }

    @FXML
    void descFieldKeyPressed(KeyEvent event) {
        if(itemListView.getSelectionModel().getSelectedIndex() >= 0) {
            if(event.getCode() == KeyCode.ENTER) {
                if(event.isShiftDown()) {
                    descriptionField.insertText(descriptionField.getCaretPosition(), System.lineSeparator());
                } else {
                    event.consume();
                    root.requestFocus();
                }
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                descriptionField.setText(itemListView.getSelectionModel().getSelectedItem().getDesc());
                root.requestFocus();
            }
        }
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

    // If the directory for the file chooser was deleted, sets directory to ./lists
    private void setFileChooserDir() {
        if(!fileChooser.getInitialDirectory().exists()) {
            fileChooser.setInitialDirectory(makeListsDir());
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private File makeListsDir() {
        File listDir = new File(System.getProperty("user.dir") +
                File.separator + "lists" + File.separator);
        if(!listDir.exists()) defaultDir.mkdir();
        return listDir;
    }
}
