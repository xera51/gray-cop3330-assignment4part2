/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Item {

    private StringProperty description = new SimpleStringProperty("");
    private ObjectProperty<LocalDate> dueDate = new SimpleObjectProperty<>(LocalDate.now());
    private BooleanProperty complete = new SimpleBooleanProperty(false);

    public Item() {}
    public Item(String description, LocalDate dueDate) {
        this.setDesc(description);
        this.setDueDate(dueDate);
    }

    public String getDesc() { return this.description.get(); }
    public LocalDate getDueDate() { return this.dueDate.get(); }
    public boolean isComplete() { return this.complete.get(); }

    public void setDesc(String description) { this.description.set(description); }
    public void setDueDate(LocalDate dueDate) { this.dueDate.set(dueDate); }
    public void setComplete(boolean complete) { this.complete.set(complete); }

    public StringProperty descriptionProperty() { return this.description; }
    public ObjectProperty<LocalDate> dueDateProperty() { return this.dueDate; }
    public BooleanProperty completeProperty() { return this.complete; }
}
