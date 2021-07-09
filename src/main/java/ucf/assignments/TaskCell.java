package ucf.assignments;

import javafx.scene.control.ListCell;
import ucf.assignments.model.ToDo;

// TODO customize cell
public class TaskCell extends ListCell<ToDo> {

    @Override
    protected void updateItem(ToDo todo, boolean empty) {
        super.updateItem(todo, empty);
        if(empty || todo == null) {
            setText(null);
            setGraphic(null);
        }
        else {
            setText(todo.getDesc());
        }
    }
}
