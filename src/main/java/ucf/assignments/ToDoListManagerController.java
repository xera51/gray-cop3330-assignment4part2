/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments;

import com.google.gson.JsonSyntaxException;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ucf.assignments.model.ToDo;
import ucf.assignments.model.ToDoDAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

// TODO make it so when checkbox clicked, the cell is selected
// TODO move alerts somewhere else
// TODO not saved alerts
public class ToDoListManagerController {

    ToDoDAO dao = new ToDoDAO();

    // TODO update to what dir user last left off in when using new or open
    private File defaultDir = null;
    private final FileChooser fileChooser = new FileChooser();
    private IndexRange descriptionPreviousSelection = new IndexRange(0, 0);
    Stage stage;

    // TODO: update wildcards in ListViews

    @FXML private BorderPane root;
    @FXML private MenuBar menuBar;
    @FXML private MenuItem newListMenuItem;
    @FXML private ListView<ToDo> itemListView;
    @FXML private Button addButton;

    @FXML private Button deleteItemButton;
    @FXML private CheckBox completeCheckbox;
    @FXML private Label dueLabel;
    @FXML private DatePicker dueDateField;
    @FXML private Label descriptionLabel;
    @FXML private Button editDescButton;
    @FXML private TextField listTitleField;
    @FXML private Button editListButton;
    @FXML private Button deleteListButton;
    @FXML private TextArea descriptionField;


    ToDoListManagerController(Stage stage) {
        this.stage = stage;
    }

    // TODO if alt-tabbing, remove the highlight for the
    // menu bar so when you go back in it isnt on the menu bar
    // (listener to stage/window showing, if not showing, remove menubar highlight
    public void initialize() {

        // Set up defaultDir
        defaultDir = makeListsDir();

        // Set up fileChooser
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.getExtensionFilters().add(new ExtensionFilter("JSON file", "*.json"));

        // Set up itemListView
        itemListView.setItems(FXCollections.observableArrayList(ToDo.extractor()));
        itemListView.setCellFactory(lv -> new TaskCell());

        // Disables description and dueDate fields when no item selected
        itemListView.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(itemListView.getSelectionModel().getSelectedIndex() < 0) {
                        descriptionField.setDisable(true);
                        dueDateField.setDisable(true);
                    } else {
                        descriptionField.setDisable(false);
                        dueDateField.setDisable(false);
                    }
                });


        // Set up descriptionField

        // Binds to the current selection's description. If no selection, binds to ""
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
        // unless they clicked on the listview
        // TODO don't save if window is closed (when user clicks off) (this might lose the text)
        descriptionField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (itemListView.getSelectionModel().getSelectedIndex() >= 0) {
                if (newValue) {
                    descriptionField.textProperty().unbind();
                } else {
                    // TODO maybe alert user cant be empty
                    if((root.isFocused() || dueDateField.isFocused()) && descriptionField.getText().length() > 0)
                        itemListView.getSelectionModel().getSelectedItem().setDesc(descriptionField.getText());
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

        descriptionField.setTextFormatter(new TextFormatter<>(change -> {
            String newTextNoCarriageReturn = change.getControlNewText().replaceAll("\\r", "");
            int newLength = newTextNoCarriageReturn.length();
            if (newLength > 256) {
                // TODO alert user they tried to go above 256 chars maybe
                String tail = change.getControlText().substring(change.getControlCaretPosition());
                if(descriptionPreviousSelection.getLength() != 0 && descriptionPreviousSelection.getStart() == change.getControlCaretPosition()) {
                    tail = tail.substring(descriptionPreviousSelection.getLength());
                }
                String head = newTextNoCarriageReturn.substring(0, 256 - tail.length());
                change.setText(head + tail);
                int oldLength = change.getControlText().length();
                change.setRange(0, oldLength);
            }
            descriptionPreviousSelection = change.getSelection();
            return change;
        }));

        // Set up dueDateField
        ObjectBinding<LocalDate> dueDateBinding =
                Bindings.when(itemListView.getSelectionModel().selectedIndexProperty().lessThan(0))
                        .then((LocalDate) null)
                        .otherwise(
                Bindings.select(
                        itemListView.getSelectionModel().selectedItemProperty(),
                        "dueDate"));

        dueDateField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (itemListView.getSelectionModel().getSelectedIndex() >= 0) {
                if (newValue) {
                    dueDateField.valueProperty().unbind();
                } else {
                    if(!itemListView.isFocused())
                        itemListView.getSelectionModel().getSelectedItem().setDueDate(dueDateField.getValue());
                    dueDateField.valueProperty().bind(dueDateBinding);
                }
            }
        });


        dueDateField.setValue(null);
        dueDateField.valueProperty().bind(dueDateBinding);


        //TODO make better
        dueDateField.setConverter(new StringConverter<>() {
            final String pattern = "yyyy-MM-dd";
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {

            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        // TODO on close request
    }

    @FXML
    void newList(ActionEvent event) {
        TextInputDialog listNameDialog = new TextInputDialog();
        listNameDialog.getEditor().setPromptText("List Name");
        listNameDialog.setTitle("New List");
        listNameDialog.setHeaderText(null);
        Label label = new Label("List Name");
        label.setPrefHeight(listNameDialog.getEditor().getHeight() + 25);
        label.setAlignment(Pos.CENTER);
        listNameDialog.setGraphic(label);
        listNameDialog.initOwner(stage);
        listNameDialog.showAndWait().ifPresent(fileName -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Set Location");
            setFileChooserDir();
            directoryChooser.setInitialDirectory(fileChooser.getInitialDirectory());
            File selectedDirectory = directoryChooser.showDialog(stage);
            try {
                defaultDir = selectedDirectory;
                dao.create(selectedDirectory, fileName);
                itemListView.getItems().setAll(dao.read());
            } catch (FileAlreadyExistsException e) {
                Alert noListAlert = new Alert(Alert.AlertType.WARNING);
                noListAlert.setTitle("List Creation Failed");
                noListAlert.setHeaderText(null);
                noListAlert.getDialogPane().setContent(new Label("List already exists!"));
                ((Label) noListAlert.getDialogPane().getContent()).setTextAlignment(TextAlignment.CENTER);
                noListAlert.initOwner(stage);
                noListAlert.show();
            } catch (IOException | InvalidPathException e) {
                Alert deleteFailedAlert = new Alert(Alert.AlertType.ERROR);
                deleteFailedAlert.setTitle("List Creation Failed");
                deleteFailedAlert.setHeaderText(null);
                deleteFailedAlert.getDialogPane().setContent(new Label("Error: Failed to Create List (List names must be a valid file name)"));
                ((Label) deleteFailedAlert.getDialogPane().getContent()).setTextAlignment(TextAlignment.CENTER);
                deleteFailedAlert.initOwner(stage);
                deleteFailedAlert.show();
                System.out.println("failed to create");
            }
        });
    }

    @FXML
    void openList(ActionEvent event) {
        // if unsaved list open, warn
        setFileChooserDir();
        fileChooser.setTitle("Open List");
        File selectedFile = fileChooser.showOpenDialog(stage);

        if(selectedFile != null) {
            try {
                defaultDir = selectedFile.getParentFile();
                dao.open(selectedFile);
                itemListView.getItems().setAll(dao.read());
            } catch (JsonSyntaxException e) {
                Alert deleteFailedAlert = new Alert(Alert.AlertType.ERROR);
                deleteFailedAlert.setTitle("Invalid File");
                deleteFailedAlert.setHeaderText(null);
                deleteFailedAlert.getDialogPane().setContent(new Label("Invalid JSON file!"));
                ((Label) deleteFailedAlert.getDialogPane().getContent()).setTextAlignment(TextAlignment.CENTER);
                deleteFailedAlert.initOwner(stage);
                deleteFailedAlert.show();
            }
        }
    }

    @FXML
    void closeList(ActionEvent event) {
        // TODO warn unsaved
        root.requestFocus();
        dao.setListFileToNull();
        itemListView.getItems().clear();
    }

    @FXML
    void save(ActionEvent event) {
        if(dao.getListFile() == null) {
            saveAs(event);
        } else if(!dao.save(itemListView.getItems())) {
            Alert deleteFailedAlert = new Alert(Alert.AlertType.ERROR);
            deleteFailedAlert.setTitle("Save Failed");
            deleteFailedAlert.setHeaderText(null);
            deleteFailedAlert.getDialogPane().setContent(new Label("Unknown Error: Failed to Save List"));
            ((Label) deleteFailedAlert.getDialogPane().getContent()).setTextAlignment(TextAlignment.CENTER);
            deleteFailedAlert.initOwner(stage);
            deleteFailedAlert.show();
        }
    }

    @FXML
    void saveAs(ActionEvent event) {
        setFileChooserDir();
        fileChooser.setTitle("Save As");
        File selectedFile = fileChooser.showSaveDialog(stage);
        dao.open(selectedFile);
        if (dao.save(itemListView.getItems())) {
            defaultDir = selectedFile.getParentFile();
        } else {
            Alert deleteFailedAlert = new Alert(Alert.AlertType.ERROR);
            deleteFailedAlert.setTitle("Save Failed");
            deleteFailedAlert.setHeaderText(null);
            deleteFailedAlert.getDialogPane().setContent(new Label("Unknown Error: Failed to Save List"));
            ((Label) deleteFailedAlert.getDialogPane().getContent()).setTextAlignment(TextAlignment.CENTER);
            deleteFailedAlert.initOwner(stage);
            deleteFailedAlert.show();
        }
    }

    @FXML
    void deleteList(ActionEvent event) {
        if (dao.getListFile() != null) {
            Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDeleteAlert.setTitle("Delete List");
            confirmDeleteAlert.setHeaderText(null);
            confirmDeleteAlert.getDialogPane().setContent(new Label("Are you sure you want to delete this list?\n" +
                    "(This action cannot be undone)"));
            ((Label) confirmDeleteAlert.getDialogPane().getContent()).setTextAlignment(TextAlignment.CENTER);
            confirmDeleteAlert.initOwner(stage);
            confirmDeleteAlert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.OK) {
                    if (!dao.delete()) {
                        Alert deleteFailedAlert = new Alert(Alert.AlertType.ERROR);
                        deleteFailedAlert.setTitle("Delete Failed");
                        deleteFailedAlert.setHeaderText(null);
                        deleteFailedAlert.getDialogPane().setContent(new Label("Unknown Error: Failed to Delete List"));
                        ((Label) deleteFailedAlert.getDialogPane().getContent()).setTextAlignment(TextAlignment.CENTER);
                        deleteFailedAlert.initOwner(stage);
                        deleteFailedAlert.show();
                    } else {
                        itemListView.getItems().clear();
                    }
                }
            });
        } else {
            Alert noListAlert = new Alert(Alert.AlertType.WARNING);
            noListAlert.setTitle("Delete Failed");
            noListAlert.setHeaderText(null);
            noListAlert.getDialogPane().setContent(new Label("No List to Delete!"));
            ((Label) noListAlert.getDialogPane().getContent()).setTextAlignment(TextAlignment.CENTER);
            noListAlert.initOwner(stage);
            noListAlert.show();
        }
    }

    @FXML
    void exit(ActionEvent event) {
        // TODO warn unsaved
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
                    descriptionField.insertText(descriptionField.getCaretPosition(), "\n");
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
        if(!listDir.exists()) listDir.mkdir();
        return listDir;
    }
}
