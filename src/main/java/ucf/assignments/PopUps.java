package ucf.assignments;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import ucf.assignments.model.ToDo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class PopUps {

    public static Dialog<String> getNewListPopUp(Stage owner) {
        TextInputDialog newListPopUp = new TextInputDialog();

        newListPopUp.setTitle("New List");
        newListPopUp.setHeaderText(null);

        Label label = new Label("List Name");
        label.setPrefHeight(newListPopUp.getEditor().getHeight() + 25);
        label.setAlignment(Pos.CENTER);

        newListPopUp.setGraphic(label);
        newListPopUp.initOwner(owner);
        return newListPopUp;
    }

    public static Dialog<ButtonType> getListAlreadyExistsPopUp(Stage owner) {
        Alert listAlreadyExistsPopUp = new Alert(Alert.AlertType.WARNING);

        listAlreadyExistsPopUp.setTitle("List Creation Failed");
        listAlreadyExistsPopUp.setHeaderText(null);

        listAlreadyExistsPopUp.getDialogPane().setContent(new Label("List already exists!"));
        ((Label) listAlreadyExistsPopUp.getDialogPane().getContent())
                .setTextAlignment(TextAlignment.CENTER);

        listAlreadyExistsPopUp.initOwner(owner);
        return listAlreadyExistsPopUp;
    }

    public static Dialog<ButtonType> getListCreationFailedPopUp(Stage owner) {
        Alert listCreationFailedPopUp = new Alert(Alert.AlertType.ERROR);

        listCreationFailedPopUp.setTitle("List Creation Failed");
        listCreationFailedPopUp.setHeaderText(null);

        listCreationFailedPopUp.getDialogPane().setContent(
                new Label("Error: Failed to Create List (List names must be a valid file name)")
        );
        ((Label) listCreationFailedPopUp.getDialogPane().getContent())
                .setTextAlignment(TextAlignment.CENTER);

        listCreationFailedPopUp.initOwner(owner);
        return listCreationFailedPopUp;
    }

    public static Dialog<ButtonType> getSaveConfirmationPopUp(Stage owner) {
        Alert saveConfirmationPopUp = new Alert(Alert.AlertType.CONFIRMATION);

        saveConfirmationPopUp.setTitle("Unsaved Changes");
        saveConfirmationPopUp.setHeaderText(null);
        saveConfirmationPopUp.setContentText(
                "You have unsaved changes. Are you sure you want to continue?");

        saveConfirmationPopUp.initOwner(owner);
        return saveConfirmationPopUp;
    }

    public static Dialog<ButtonType> getInvalidJsonFilePopUp(Stage owner) {
        Alert invalidJsonFilePopUp = new Alert(Alert.AlertType.ERROR);

        invalidJsonFilePopUp.setTitle("Invalid JSON File");
        invalidJsonFilePopUp.setHeaderText(null);

        invalidJsonFilePopUp.getDialogPane().setContent(
                new Label("Invalid JSON file!"));
        ((Label) invalidJsonFilePopUp.getDialogPane().getContent())
                .setTextAlignment(TextAlignment.CENTER);

        invalidJsonFilePopUp.initOwner(owner);
        return invalidJsonFilePopUp;
    }

    public static Dialog<ButtonType> getSaveFailedPopUp(Stage owner) {
        Alert saveFailedPopUp = new Alert(Alert.AlertType.ERROR);

        saveFailedPopUp.setTitle("Save Failed");
        saveFailedPopUp.setHeaderText(null);

        saveFailedPopUp.getDialogPane().setContent(
                new Label("Unknown Error: Failed to Save List"));
        ((Label) saveFailedPopUp.getDialogPane().getContent())
                .setTextAlignment(TextAlignment.CENTER);

        saveFailedPopUp.initOwner(owner);
        return saveFailedPopUp;
    }

    public static Dialog<ButtonType> getDeleteConfirmationPopUp(Stage owner) {
        Alert deleteConfirmationPopUp = new Alert(Alert.AlertType.CONFIRMATION);

        deleteConfirmationPopUp.setTitle("Delete List");
        deleteConfirmationPopUp.setHeaderText(null);

        deleteConfirmationPopUp.getDialogPane().setContent(
                new Label("Are you sure you want to delete this list?\n" +
                "(This action cannot be undone)"));
        ((Label) deleteConfirmationPopUp.getDialogPane().getContent()).setTextAlignment(TextAlignment.CENTER);

        deleteConfirmationPopUp.initOwner(owner);
        return deleteConfirmationPopUp;
    }

    public static Dialog<ButtonType> getNoListToDeletePopUp(Stage owner) {
        Alert noListToDeletePopUp = new Alert(Alert.AlertType.WARNING);

        noListToDeletePopUp.setTitle("Delete Failed");
        noListToDeletePopUp.setHeaderText(null);

        noListToDeletePopUp.getDialogPane().setContent(
                new Label("No List to Delete!"));
        ((Label) noListToDeletePopUp.getDialogPane().getContent())
                .setTextAlignment(TextAlignment.CENTER);

        noListToDeletePopUp.initOwner(owner);
        return noListToDeletePopUp;
    }

    public static Dialog<ButtonType> getDeleteFailedPopUp(Stage owner) {
        Alert deleteFailedPopUp = new Alert(Alert.AlertType.ERROR);

        deleteFailedPopUp.setTitle("Delete Failed");
        deleteFailedPopUp.setHeaderText(null);

        deleteFailedPopUp.getDialogPane().setContent(
                new Label("Unknown Error: Failed to Delete List"));
        ((Label) deleteFailedPopUp.getDialogPane().getContent())
                .setTextAlignment(TextAlignment.CENTER);

        deleteFailedPopUp.initOwner(owner);
        return deleteFailedPopUp;
    }

    public static Dialog<ButtonType> getWrongFileTypePopUp(Stage owner) {
        Alert wrongFileTypePopUp = new Alert(Alert.AlertType.ERROR);

        wrongFileTypePopUp.setTitle("Save Failed");
        wrongFileTypePopUp.setHeaderText(null);

        wrongFileTypePopUp.getDialogPane().setContent(
                new Label("Wrong file type!"));
        ((Label) wrongFileTypePopUp.getDialogPane().getContent())
                .setTextAlignment(TextAlignment.CENTER);

        wrongFileTypePopUp.initOwner(owner);
        return wrongFileTypePopUp;
    }

    public static Dialog<ToDo> getAddToDoPopUp(Stage owner) {
        Dialog<ToDo> addToDoPopUp = new Dialog<>();

        addToDoPopUp.setTitle("Add To-Do");
        addToDoPopUp.getDialogPane()
                .getScene()
                .getWindow()
                .setOnCloseRequest(event -> addToDoPopUp.close());
        addToDoPopUp.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);

        GridPane grid = new GridPane();
        Label descriptionLabel = new Label("Description: ");
        Label dueDateLabel = new Label("Due Date: ");
        TextArea descriptionField = new TextArea();
        DatePicker dueDatePicker = new DatePicker();

        descriptionField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if(!event.isShiftDown() && event.getCode() == KeyCode.ENTER) {
                event.consume();
                grid.requestFocus();
            }
        });

        dueDatePicker.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if(!event.isShiftDown() && event.getCode() == KeyCode.ENTER) {
                event.consume();
                grid.requestFocus();
            }
        });

        dueDatePicker.setConverter(new StringConverter<>() {
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

        grid.add(descriptionLabel, 0, 0);
        grid.add(descriptionField, 1, 0);
        grid.add(dueDateLabel, 0, 1);
        grid.add(dueDatePicker, 1, 1);

        addToDoPopUp.setGraphic(grid);

        addToDoPopUp.setResultConverter(param -> {
            if(param == ButtonType.OK && dueDatePicker.getValue() != null && descriptionField.getText().length() != 0) {
                if(descriptionField.getText().length() > 256) {
                    return new ToDo(descriptionField.getText().substring(0, 256), dueDatePicker.getValue());
                } else {
                    return new ToDo(descriptionField.getText(), dueDatePicker.getValue());
                }
            }
            return null;
        });

        addToDoPopUp.initOwner(owner);
        return addToDoPopUp;
    }

    public static Dialog<Void> getHelpPopUp(Stage owner) {
        Dialog<Void> helpPopUp = new Dialog<>();

        helpPopUp.setTitle("Getting Started");
        helpPopUp.getDialogPane()
                .getScene()
                .getWindow()
                .setOnCloseRequest(event -> helpPopUp.close());

        Label helpLabel = new Label();
        helpLabel.setText("""
                To get started, simply add a to-do by clicking the "Add To-Do" Button
                
                To attach the List to a file, Go to File->New List or Ctrl+N
                This can be done later if preferred
                
                To-Dos can be deleted with the "Delete To-Do" Button or pressing the Delete Key
                All To-Dos can be deleted with the "Delete All To-Dos" Button or Ctrl+Delete
                
                To-Dos can be edited on the right side of the window
                To-Dos can be marked as complete or incomplete with the checkbox to the right of the description
                
                A To-Do list can be saved using File->Save or Ctrl+S.
                If the list is not already attached to a file,
                you will be prompted to choose a file to save to. This file must be of type .json
                
                A To-Do list can be saved using File->Save As. You will always be prompted for a file
                
                A To-Do List can be opened with File->Open or Ctrl+O
                A To-Do List can be closed with File->Close List
                A To-Do List can be deleted with File->Delete List
                
                The To-Do List can be Filtered and Sorted using the selectors on the right side of the window or the View menu
                
                The Application can be exited with File->Exit or Alt+F4
                
                A sample list is provided in the /lists/ directory.
                This list can be opened with File->Open
                
                This help window is dedicated to Rey
                """);

        helpPopUp.setGraphic(helpLabel);

        helpPopUp.initOwner(owner);
        return helpPopUp;
    }
}
