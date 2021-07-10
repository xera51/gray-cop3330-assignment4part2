package ucf.assignments;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

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
}
