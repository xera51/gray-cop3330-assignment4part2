package ucf.assignments;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import ucf.assignments.controllers.TaskCellController;
import ucf.assignments.model.ToDo;

import java.io.IOException;
import java.net.URL;

// TODO customize cell
public class TaskCell extends ListCell<ToDo> {

    private TaskCellController controller;

    public TaskCell() {
        try {
            FXMLLoader loader = new FXMLLoader(ToDoListManager.TaskCellFXML);
            loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(ToDo todo, boolean empty) {
        super.updateItem(todo, empty);
        if(empty || todo == null) {
            setGraphic(null);
        } else {
            controller.setToDo(todo);
            Node root = controller.getView();
            setGraphic(root);
        }
    }
}
