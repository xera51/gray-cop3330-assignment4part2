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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ucf.assignments.model.ToDo;
import ucf.assignments.model.ToDoDAO;
import ucf.assignments.model.ToDoListManagerModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// TODO make it so when checkbox clicked, the cell is selected
// TODO move alerts somewhere else
// TODO not saved alerts
// TODO sorts called after edits (and add and remove)
// TODO include a sample list
// TODO load last list opened when last closed? settings?
public class ToDoListManagerController {

    ToDoDAO dao = new ToDoDAO();

    ToDoListManagerModel model = new ToDoListManagerModel();

    private File defaultDir = null;
    private final FileChooser fileChooser = new FileChooser();
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private IndexRange descriptionPreviousSelection = new IndexRange(0, 0);
    Stage stage;


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
    @FXML
    private Button deleteToDoButton;

    @FXML
    private Button deleteAllToDosButton;
    @FXML
    private MenuItem showAllItem;

    @FXML
    private MenuItem showCompleteItem;

    @FXML
    private MenuItem showIncompleteItem;

    @FXML
    private MenuItem sortAlphabeticallyItem;

    @FXML
    private MenuItem sortDueDateItem;


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

        // Set up directoryChooser
        directoryChooser.setInitialDirectory(defaultDir);

        // Set up itemListView
        itemListView.setItems(model.getFilteredToDoList());
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
        directoryChooser.setTitle("Set Location");
        setChooserDir();
        directoryChooser.setInitialDirectory(fileChooser.getInitialDirectory());

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

        dueDateField.setConverter(new StringConverter<>() {
            final String pattern = "yyyy-MM-dd";
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

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

        dueDateField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if(!event.isShiftDown() && event.getCode() == KeyCode.ENTER) {
                event.consume();
                root.requestFocus();
            }
        });
    }

    @FXML
    void newList(ActionEvent event) {
        PopUps.getNewListPopUp(stage).showAndWait().ifPresent(fileName -> {
            setChooserDir();
            File selectedDirectory = directoryChooser.showDialog(stage);
            if(selectedDirectory != null) {
                try {
                    defaultDir = selectedDirectory;
                    model.createList(selectedDirectory, fileName);
                } catch (FileAlreadyExistsException e) {
                    PopUps.getListAlreadyExistsPopUp(stage).show();
                } catch (IOException | InvalidPathException e) {
                    PopUps.getListCreationFailedPopUp(stage).show();
                }
            }
        });
    }

    @FXML
    void openList(ActionEvent event) {
        // TODO warn unsaved
        setChooserDir();
        fileChooser.setTitle("Open List");
        File selectedFile = fileChooser.showOpenDialog(stage);

        if(selectedFile != null) {
            try {
                defaultDir = selectedFile.getParentFile();
                model.openList(selectedFile);
                model.loadList();
            } catch (JsonSyntaxException e) {
                PopUps.getInvalidJsonFilePopUp(stage).show();
            }
        }
    }

    @FXML
    void closeList(ActionEvent event) {
        if(model.getDao().getListFile() != null) {
            // TODO warn unsaved
            root.requestFocus();
            model.clearList();
        }
    }

    @FXML
    void save(ActionEvent event) {
        // TODO set saved to true
        if(model.getDao().getListFile() == null) {
            saveAs(event);
        } else if(!model.saveList()) {
            PopUps.getSaveFailedPopUp(stage).show();
        }
    }

    @FXML
    void saveAs(ActionEvent event) {
        // TODO set saved to true
        setChooserDir();
        fileChooser.setTitle("Save As");
        File selectedFile = fileChooser.showSaveDialog(stage);

        if(model.openList(selectedFile)) {
            if (model.saveList()) {
                defaultDir = selectedFile.getParentFile();
            } else {
                PopUps.getSaveFailedPopUp(stage).show();
            }
        } else {
            PopUps.getWrongFileTypePopUp(stage).show();
        }
    }

    @FXML
    void deleteList(ActionEvent event) {
        if (model.getDao().getListFile() != null) {
            PopUps.getDeleteConfirmationPopUp(stage).showAndWait().ifPresent(response -> {
                if(response == ButtonType.OK) {
                    if (!model.deleteList()) {
                        PopUps.getDeleteFailedPopUp(stage).show();
                    }
                }
            });
        } else {
            PopUps.getNoListToDeletePopUp(stage).show();
        }
    }


    @FXML
    void openHelpWindow(ActionEvent event) {
        PopUps.getHelpPopUp(stage).show();
    }

    @FXML
    void exit(ActionEvent event) {
        // TODO warn unsaved
        Platform.exit();
    }


    @FXML
    void addItem(ActionEvent event) {
        PopUps.getAddToDoPopUp(stage).showAndWait().ifPresent(toDo -> model.addToDo(toDo));
    }

    @FXML
    void deleteItem(ActionEvent event) {
       model.deleteToDo(itemListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void deleteAllItems(ActionEvent event) {
        model.deleteAllToDos();
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
    void editDesc(ActionEvent event) {
        descriptionField.requestFocus();
        descriptionField.selectAll();
    }

    @FXML
    void showAllToDos(ActionEvent event) {
        model.filterAll();
    }

    @FXML
    void showCompleteToDos(ActionEvent event) {
        model.filterComplete();
    }

    @FXML
    void showIncompleteToDos(ActionEvent event) {
        model.filterIncomplete();
    }

    @FXML
    void sortAlphabetically(ActionEvent event) {
        model.sortLexicographic();
    }

    @FXML
    void sortByDueDate(ActionEvent event) {
        model.sortDue();
    }

    // If the directory for the file/directory chooser was deleted, sets directory to ./lists
    private void setChooserDir() {
        if(!fileChooser.getInitialDirectory().exists()) {
            fileChooser.setInitialDirectory(makeListsDir());
            directoryChooser.setInitialDirectory(fileChooser.getInitialDirectory());
        }
    }

    // Creates the lists directory if it does not exist
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private File makeListsDir() {
        File listDir = new File(System.getProperty("user.dir") +
                File.separator + "lists" + File.separator);
        if(!listDir.exists()) listDir.mkdir();
        return listDir;
    }
}
