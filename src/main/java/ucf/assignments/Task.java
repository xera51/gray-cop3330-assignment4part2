/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.util.Callback;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable, Comparable<Task> {

    private final StringProperty description;
    private final ObjectProperty<LocalDate> dueDate;
    private final BooleanProperty complete;

    public Task() {
        this("", LocalDate.now(), false);
    }

    public Task(String description, LocalDate dueDate) {
        this(description, dueDate, false);
    }

    public Task(String description, LocalDate dueDate, boolean complete) {
        this.description = new SimpleStringProperty(description);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.complete = new SimpleBooleanProperty(false);
    }

    public static Callback<Task, Observable[]> extractor() {
        return (Task task) -> new Observable[]{
                task.descriptionProperty(),
                task.dueDateProperty(),
                task.completeProperty()
        };
    }

    public final String getDesc() {
        return description.get();
    }

    public void setDesc(String description) {
        this.description.set(description);
    }

    public final LocalDate getDueDate() {
        return dueDate.get();
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate.set(dueDate);
    }

    public final boolean isComplete() {
        return complete.get();
    }

    public void setComplete(boolean complete) {
        this.complete.set(complete);
    }

    public final StringProperty descriptionProperty() {
        return description;
    }

    public final ObjectProperty<LocalDate> dueDateProperty() {
        return dueDate;
    }

    public final BooleanProperty completeProperty() {
        return complete;
    }

    @Override
    public String toString() {
        return "Task{" +
                "dueDate=" + this.getDueDate() +
                ", description=" + this.getDesc() +
                ", complete=" + this.isComplete() + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Task i)) return false;
        return this.getDesc().equals(i.getDesc())
                && this.getDueDate().equals(i.getDueDate())
                && (this.isComplete() == i.isComplete());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (description == null ? 0 : description.hashCode());
        hash = 31 * hash + (dueDate == null ? 0 : dueDate.hashCode());
        hash = 31 * hash + complete.hashCode();
        return hash;
    }

    @Override
    public int compareTo(Task task) {
        return this.description.get().compareTo(task.description.get());
    }
}
