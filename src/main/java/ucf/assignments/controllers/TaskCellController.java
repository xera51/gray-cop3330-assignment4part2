package ucf.assignments.controllers;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import ucf.assignments.model.ToDo;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;



public class TaskCellController {

    @FXML
    private BorderPane root;

    @FXML
    private CheckBox completeCheckBox;

    @FXML
    private Label dueDateLabel;

    @FXML
    private Label descriptionLabel;

    ToDo toDo;

    public void setToDo(ToDo newToDo) {

        toDo = newToDo;

        StringBinding dueDateBinding = new StringBinding() {
            {
                super.bind(toDo.dueDateProperty());
            }
            @Override
            protected String computeValue() {

                if(toDo.getDueDate().equals(LocalDate.now())) {
                    return "Today";
                } else if (toDo.getDueDate().minusDays(1).equals(LocalDate.now())) {
                    return "Tomorrow";
                } else {
                    return toDo.getDueDate().toString();
                }
            }
        };

        dueDateLabel.textProperty().bind(dueDateBinding);
        descriptionLabel.textProperty().bind(toDo.descriptionProperty());
        completeCheckBox.setSelected(toDo.isComplete());
        completeCheckBox.selectedProperty().addListener(
                (observable, oldValue, newValue) -> toDo.setComplete(newValue));
    }

    public Node getView() {
        return root;
    }
}
