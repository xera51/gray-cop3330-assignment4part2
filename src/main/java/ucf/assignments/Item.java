/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Item {

    private final StringProperty description = new SimpleStringProperty("");
    private final ObjectProperty<LocalDate> dueDate = new SimpleObjectProperty<>(LocalDate.now());
    private final BooleanProperty complete = new SimpleBooleanProperty(false);

    public Item() {}

    public Item(String description, LocalDate dueDate) {
        this.setDesc(description);
        this.setDueDate(dueDate);
    }

    public Item(String description, LocalDate dueDate, boolean complete) {
        this.setDesc(description);
        this.setDueDate(dueDate);
        this.setComplete(complete);
    }

    public final String getDesc() { return this.description.get(); }
    public final LocalDate getDueDate() { return this.dueDate.get(); }
    public final boolean isComplete() { return this.complete.get(); }

    public final void setDesc(String description) { this.description.set(description); }
    public final void setDueDate(LocalDate dueDate) { this.dueDate.set(dueDate); }
    public final void setComplete(boolean complete) { this.complete.set(complete); }

    public StringProperty descriptionProperty() { return this.description; }
    public ObjectProperty<LocalDate> dueDateProperty() { return this.dueDate; }
    public BooleanProperty completeProperty() { return this.complete; }

    @Override
    public String toString() {
        return String.format("Due: %s%nDescription: %s%nComplete: %b%n",
                this.getDueDate(), this.getDesc(), this.isComplete());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof Item i)) return false;
        return this.getDesc().equals(i.getDesc())
                && this.getDueDate().equals(i.getDueDate())
                && (this.isComplete() == i.isComplete());
    }
}
