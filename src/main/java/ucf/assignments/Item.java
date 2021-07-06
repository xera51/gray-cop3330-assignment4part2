/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Christopher Gray
 */

package ucf.assignments;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Item {


    private StringProperty description = new SimpleStringProperty();
    private ObjectProperty<LocalDate> dueDate = new ObjectPropertyBase<LocalDate>() {
        @Override
        public Object getBean() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }
    };
    private BooleanProperty complete = new SimpleBooleanProperty();

    public Item() {
        //constructs Item, complete set to false
    }

    public Item(String description, LocalDate dueDate) {
        //constructs Item, complete set to false
    }


    public String getDesc() {
        //return Item description string
        return null;
    }

    public LocalDate getDueDate() {
        //return Item dueDate
        return null;
    }

    public boolean isComplete() {
        // returns Item complete status
        return false;
    }

    public void setDesc(String description) {
        //set Item description
    }

    public void setDueDate(LocalDate dueDate) {
        //set Item dueDate
    }

    public void setComplete(boolean complete) {
        // sets Item compelete
    }

    public StringProperty descriptionProperty() {
        // returns description
        return null;
    }

    public ObjectProperty<LocalDate> dueDateProperty() {
        // returns local date
        return null;
    }

    public BooleanProperty completeProperty() {
        // returns complete
        return null;
    }
}
